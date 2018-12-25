import re

def binary_search(f, lo=0, hi=None):
    """
    Returns a value x such that f(x) is true.
    Based on the values of f at lo and hi.
    Assert that f(lo) != f(hi).
    """
    lo_bool = f(lo)
    if hi is None:
        offset = 1
        while f(lo+offset) == lo_bool:
            offset *= 2
        hi = lo + offset
    else:
        assert f(hi) != lo_bool
    best_so_far = lo if lo_bool else hi
    while lo <= hi:
        mid = (hi + lo) // 2
        result = f(mid)
        if result:
            best_so_far = mid
        if result == lo_bool:
            lo = mid + 1
        else:
            hi = mid - 1
    return best_so_far


inp = """
Immune System:
4082 units each with 2910 hit points with an attack that does 5 cold damage at initiative 15
2820 units each with 9661 hit points (immune to slashing) with an attack that does 27 cold damage at initiative 8
4004 units each with 4885 hit points (weak to slashing) with an attack that does 10 bludgeoning damage at initiative 13
480 units each with 7219 hit points (weak to bludgeoning) with an attack that does 134 radiation damage at initiative 18
8734 units each with 4421 hit points (immune to bludgeoning) with an attack that does 5 slashing damage at initiative 14
516 units each with 2410 hit points (weak to slashing) with an attack that does 46 bludgeoning damage at initiative 5
2437 units each with 11267 hit points (weak to slashing) with an attack that does 38 fire damage at initiative 17
1815 units each with 7239 hit points (weak to cold) with an attack that does 33 slashing damage at initiative 10
4941 units each with 10117 hit points (immune to bludgeoning) with an attack that does 20 fire damage at initiative 9
617 units each with 7816 hit points (weak to bludgeoning, slashing) with an attack that does 120 bludgeoning damage at initiative 4

Infection:
2877 units each with 20620 hit points (weak to radiation, bludgeoning) with an attack that does 13 cold damage at initiative 11
1164 units each with 51797 hit points (immune to fire) with an attack that does 63 fire damage at initiative 7
160 units each with 31039 hit points (weak to radiation; immune to bludgeoning) with an attack that does 317 bludgeoning damage at initiative 2
779 units each with 24870 hit points (immune to radiation, bludgeoning; weak to slashing) with an attack that does 59 slashing damage at initiative 12
1461 units each with 28000 hit points (immune to radiation; weak to bludgeoning) with an attack that does 37 slashing damage at initiative 16
1060 units each with 48827 hit points with an attack that does 73 slashing damage at initiative 3
4422 units each with 38291 hit points with an attack that does 14 slashing damage at initiative 1
4111 units each with 14339 hit points (immune to fire, bludgeoning, cold) with an attack that does 6 radiation damage at initiative 20
4040 units each with 49799 hit points (immune to bludgeoning, cold; weak to slashing, fire) with an attack that does 24 fire damage at initiative 19
2198 units each with 41195 hit points (weak to radiation) with an attack that does 36 slashing damage at initiative 6
""".strip()

def doit(boost=0, part1=False):
    lines = inp.splitlines()
    immune, infection = inp.split("\n\n")

    teams = []

    REGEX = re.compile(r"(\d+) units each with (\d+) hit points (\([^)]*\) )?with an attack that does (\d+) (\w+) damage at initiative (\d+)")

    # namedtuple? who needs namedtuple with hacks like these?
    UNITS, HP, DAMAGE, DTYPE, FAST, IMMUNE, WEAK = range(7)

    blah = boost
    for inps in [immune, infection]:
        lines = inps.splitlines()[1:]
        team = []
        for line in lines:
            s = REGEX.match(line)
            units, hp, extra, damage, dtype, fast = s.groups()
            immune = []
            weak = []
            if extra:
                extra = extra.rstrip(" )").lstrip("(")
                for s in extra.split("; "):
                    if s.startswith("weak to "):
                       weak = s[len("weak to "):].split(", ")
                    elif s.startswith("immune to "):
                       immune = s[len("immune to "):].split(", ")
                    else:
                       assert False
            u = [int(units), int(hp), int(damage) + blah, dtype, int(fast), set(immune), set(weak)]
            team.append(u)
        teams.append(team)
        blah = 0

    def power(t):
        return t[UNITS] * t[DAMAGE]

    def damage(attacking, defending):
        mod = 1
        if attacking[DTYPE] in defending[IMMUNE]:
            mod = 0
        elif attacking[DTYPE] in defending[WEAK]:
            mod = 2
        return power(attacking) * mod

    def sort_key(attacking, defending):
        return (damage(attacking, defending), power(defending), defending[FAST])

    while all(not all(u[UNITS] <= 0 for u in team) for team in teams):
        teams[0].sort(key=power, reverse=True)
        teams[1].sort(key=power, reverse=True)

        targets = []

        # target selection
        for team_i in range(2):
            other_team_i = 1 - team_i
            team = teams[team_i]
            other_team = teams[other_team_i]

            remaining_targets = set(i for i in range(len(other_team)) if other_team[i][UNITS] > 0)
            my_targets = [None] * len(team)

            for i, t in enumerate(team):
                if not remaining_targets:
                    break
                best_target = max(remaining_targets, key= lambda i: sort_key(t, other_team[i]))
                enemy = other_team[best_target]
                if damage(t, enemy) == 0:
                    continue
                print("target: ", t[FAST], "=>", enemy[FAST], t[UNITS]*t[DAMAGE], " | ", damage(t, enemy));
                my_targets[i] = best_target
                remaining_targets.remove(best_target)
            targets.append(my_targets)

        # attacking
        attack_sequence = [(0, i) for i in range(len(teams[0]))] + [(1, i) for i in range(len(teams[1]))]
        attack_sequence.sort(key=lambda x: teams[x[0]][x[1]][FAST], reverse=True)
        did_damage = False
        for team_i, index in attack_sequence:
            to_attack = targets[team_i][index]
            if to_attack is None:
                continue
            me = teams[team_i][index]
            other = teams[1-team_i][to_attack]

            d = damage(me, other)
            d //= other[HP]
            print(me[FAST], "=>", other[FAST]);


            if teams[1-team_i][to_attack][UNITS] > 0 and d > 0:
                did_damage = True

            teams[1-team_i][to_attack][UNITS] -= d
            teams[1-team_i][to_attack][UNITS] = max(teams[1-team_i][to_attack][UNITS], 0)
        if not did_damage:
            return None

    if part1:
        return sum(u[UNITS] for u in teams[0]) or sum(u[UNITS] for u in teams[1])
    asd = sum(u[UNITS] for u in teams[0])
    if asd == 0:
        return None
    else:
        return asd
print(doit(part1=True))