package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.LongStream;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D20_ParticleSwarm {

    public static final double EPSILON = 0.000001;

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D20_ParticleSwarm.class);
        assertThat(run(fileInputs.get(0)), is("0 - 0"));
        assertThat(run(fileInputs.get(1)), is("2 - 1"));
        assertThat(run(fileInputs.get(2)), is("1 - 0"));
        assertThat(run(fileInputs.get(3)), is("170 - 571"));
    }

    private String run(String input) {
        String[] lines = input.split("\\n");
        List<Particle> ps = new ArrayList<>();
        for (String line : lines) {
            ps.add(new Particle(ps.size(), line));
        }
        Particle closest = findClosest(ps, asList(p -> p.acc.norm1(), p -> p.vel.norm1(), p -> p.pos.norm1()));
        List<Collision> collisions = new ArrayList<>();
        for (int i = 0; i < ps.size() - 1; i++) {
            for (int j = i + 1; j < ps.size(); j++) {
                Collision c = getCollision(ps.get(i), ps.get(j));
                if (c != null) collisions.add(c);
            }
        }
        Collections.sort(collisions);
        Set<Particle> collided = new HashSet<>();
        double lastCollisionTime = 0.0;
        for (Collision c : collisions) {
            if (isLt(lastCollisionTime, c.t) && (collided.contains(c.a) || collided.contains(c.b))) continue;
            collided.add(c.a);
            collided.add(c.b);
            lastCollisionTime = c.t;
        }
        int nonCollided = ps.size() - collided.size();
        return closest.id + " - " + nonCollided;
    }

    Particle findClosest(List<Particle> ps, List<Function<Particle, Long>> evaluators) {
        List<Particle> curParticles = ps;
        for (Function<Particle, Long> f : evaluators) {
            long lowestVal = Long.MAX_VALUE;
            List<Particle> closestParticles = new ArrayList<>();
            for (Particle p : curParticles) {
                Long val = f.apply(p);
                if (val < lowestVal) {
                    lowestVal = val;
                    closestParticles.clear();
                }
                if (val == lowestVal) {
                    closestParticles.add(p);
                }
            }
            if (closestParticles.size() == 1) {
                return closestParticles.get(0);
            }
            curParticles = closestParticles;
        }
        throw new RuntimeException("ooops");
    }

    private Collision getCollision(Particle a, Particle b) {
        if (a.pos.equals(b.pos)) return new Collision(a, b, 0.0);
        Double collisionTime = null;
        for (int i = 0; i < 3; i++) {
            double[] times = collisionTime(a, b, i);
            if (times == null) return null;
            for (int j = 0; j < 2; j++) {
                if (times[j] - EPSILON / 2 < 0) continue;
                if ((collisionTime == null || times[j] < collisionTime) && isCollision(a, b, times[j])) {
                    collisionTime = times[j];
                }
            }
        }
        if (collisionTime == null) return null;
        return new Collision(a, b, collisionTime);
    }

    private boolean isEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    private boolean isLt(double a, double b) {
        return !isEqual(a, b) && a < b;
    }

    private boolean isCollision(Particle p1, Particle p2, double t) {
        for (int i = 0; i < 3; i++) {
            long da = p1.acc.vals[i] - p2.acc.vals[i];
            long dv = p1.vel.vals[i] - p2.vel.vals[i];
            long dp = p1.pos.vals[i] - p2.pos.vals[i];
            // dp + dv * t + da * t * (t + 1) / 2 = 0
            // dp + dv * t + da * t^2 / 2 + da * t / 2
            // dp + t * (dv + da/2) + t^2 * (da/2)
            double a = da * 0.5;
            double b = dv + a;
            double c = dp;

            double val = a * t * t + b * t + c;
            if (!isEqual(val, 0.0)) {
                return false;
            }
        }
        return true;
    }

    private double[] collisionTime(Particle p1, Particle p2, int dimension) {
        double da = p1.acc.vals[dimension] - p2.acc.vals[dimension];
        long dv = p1.vel.vals[dimension] - p2.vel.vals[dimension];
        long dp = p1.pos.vals[dimension] - p2.pos.vals[dimension];
        // dp + dv * t + da * t * (t + 1) / 2 = 0
        // dp + dv * t + da * t^2 / 2 + da * t / 2
        // dp + t * (dv + da/2) + t^2 * (da/2)
        double a = da * 0.5;
        double b = dv + a;
        double c = dp;

        if (a == 0.0 && b == 0) {
            // not moving
            return c == 0 ? new double[]{0.0, -1.0} : null;
        }
        if (a == 0.0) {
            // linear
            return new double[]{((double) -c) / b, -1.0};
        }

        double delta = b * b - 4 * a * c;
        if (delta < 0) return null;
        double sqrt = Math.sqrt(delta);
        return new double[]{(-b + sqrt) / (2 * a), (-b - sqrt) / (2 * a)};
    }

    private static class Collision implements Comparable<Collision> {
        Particle a;
        Particle b;
        double t;

        public Collision(Particle a, Particle b, double t) {
            this.a = a;
            this.b = b;
            this.t = t;
        }

        @Override
        public int compareTo(Collision o) {
            return Double.compare(this.t, o.t);
        }

        @Override
        public String toString() {
            return a + " collides with " + b + " at t=" + t;
        }
    }

    private static class Particle {
        int id;
        Vector pos;
        Vector vel;
        Vector acc;

        Particle(int id, String line) {
            this.id = id;
            int lt = line.indexOf('<');
            int gt = line.indexOf('>');
            pos = new Vector(line.substring(lt + 1, gt));
            lt = line.indexOf('<', lt + 1);
            gt = line.indexOf('>', gt + 1);
            vel = new Vector(line.substring(lt + 1, gt));
            lt = line.indexOf('<', lt + 1);
            gt = line.indexOf('>', gt + 1);
            acc = new Vector(line.substring(lt + 1, gt));
        }

        @Override
        public String toString() {
            return "p" + pos + ", v" + vel + ", a" + acc;
        }
    }

    private static class Vector {
        long[] vals;

        Vector(Vector v) {
            this.vals = Arrays.copyOf(v.vals, v.vals.length);
        }

        Vector(String vals) {
            this.vals = Utils.toLongArray(vals.split(","));
        }

        long norm1() {
            return LongStream.of(vals).map(Math::abs).sum();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vector vector = (Vector) o;

            return Arrays.equals(vals, vector.vals);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(vals);
        }

        @Override
        public String toString() {
            return Arrays.toString(vals);
        }

        public long dist(Vector v) {
            long dist = 0;
            for (int i = 0; i < vals.length; i++) dist += Math.abs(vals[i] - v.vals[i]);
            return dist;
        }
    }
}
