package com.dylantjb.tick2star;

public class PackedLong {

    /*
     * Set the nth bit in the packed number to the value given and return the new
     * packed number
     */
    public static long set(long packed, int position, boolean value) {
        long adjustment = (long) (Math.pow(2, position));
        if (value) {
            // update the value "packed" with the bit at "position" set to 1
            packed += adjustment;

        } else {
            // update the value "packed" with the bit at "position" set to 0
            packed -= adjustment;
        }
        return packed;
    }
}