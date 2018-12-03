package adventofcode.y2017;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D23_CoprocessorConflagration_2 {

    @Test
    public void test() throws IOException {
        assertThat(run(0), is(1L));
        assertThat(run(1), is(915L));
    }

    // I basically decompiled the program to java and optimized it.
    // During this process I realized that it was counting the amount of
    // numbers between a range that were prime and also had an offset from the
    // start that is divisible by 17.
    // Variables were renamed for readability.
    // Variable definitions:
    // a: defines if we check a range or just 1 number for primes
    // b: start of range and also the current number being tested
    // c: end of range
    // d: used to find a factor of the current number being tested
    // e: used to find a factor of the current number being tested
    // f: indicates if b is currently prime or not
    // g: auxiliary variable, no other meaning
    // h: prime count
    private long run(long a) {
        long start = 57; // b
        long end = 57; // c
        if (a != 0L) {
            start = 105_700;
            end = 122_700;
        }
        // g generally just holds temporary computations, could be called aux
        long primeCount = 0; // h
        for (long n = start; n <= end; n += 17)
            if (isPrime(n)) ++primeCount;
        return primeCount;
    }

    private boolean isPrime(long n) {
        // f tracks if prime is found or not
        // d and e are involved in the computation.
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return true;
        return false;
    }
}
