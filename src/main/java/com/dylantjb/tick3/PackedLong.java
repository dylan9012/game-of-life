package com.dylantjb.tick3;

public class PackedLong {
    /*
     * Unpack and return the nth bit from the packed number at index position; *
     * position counts from zero (representing the least significant bit) up to 63
     * (representing the most significant bit).
     */
    public static boolean get(long packed, int position) {
        // set "check" to equal 1 if the "position" bit in "packed" is set to 1
        packed >>= position;
        int check = (packed % 2 == 0) ? 0 : 1;
        return (check == 1);
    }

}