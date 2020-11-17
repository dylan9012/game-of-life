package com.dylantjb.tick3;

import java.util.Arrays;

public class FibonacciCache {
    public static long[] fib;

    public static void main(String[] args) {
        fib = new long[Integer.parseInt(args[0])];

        // Test for fib store and fetch an index
        store();
        System.out.println(get(3) + "\n"); // 2

        // Test for correct fibonacci numbers
        for (long i : fib) {
            System.out.println(i); // 0, 1, ..., 4181
        }
        System.out.println("\n");

        // Test for fib rest and fetch an invalid index
        reset();
        for (long i : fib) {
            System.out.println(i); // 0, 0, ..., 0
        }
        System.out.println(get(20)); // -1
    }

    public static void store() {
        for (int i = 0; i < fib.length; i++) {
            if (i < 2) {
                fib[i] = i;
            } else {
                fib[i] = fib[i - 1] + fib[i - 2];
            }
        }
    }

    public static long get(int i) {
        if (i < fib.length) {
            return fib[i];
        } else {
            return -1L;
        }
    }

    public static void reset() {
        Arrays.fill(fib, 0);
    }

}
