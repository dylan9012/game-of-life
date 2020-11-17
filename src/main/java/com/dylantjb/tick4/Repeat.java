package com.dylantjb.tick4;

public class Repeat {
    public static void main(String[] args) {
        System.out.println(parseAndRep(args));
    }

    /*
     * Return the first string repeated by the number of times
     * specified by the integer in the second string, for example
     *
     *    parseAndRep(new String[]{"one","3"})
     *
     * should return "one one one". Adjacent copies of the repeated
     * string should be separated by a single space.
     *
     * Return a suitable error message in a string when there are
     * not enough arguments in "args" or the second argument is
     * not a valid positive integer. For example:
     *
     *  - parseAndRep(new String[]{"one"}) should return
     *    "Error: insufficient arguments"
     *
     *  - parseAndRep(new String[]{"one","five"}) should return
     *    "Error: second argument is not a positive integer"
     */

    public static String parseAndRep(String[] args) {
        try {
            String repeated = args[0];
            StringBuilder copies = new StringBuilder(repeated);

            int repetitions = Integer.parseInt(args[1]);

            String addition = " " + repeated;
            for (int i = 1; i < repetitions; i++) {
                copies.append(addition);
            }
            return copies.toString();

        } catch (ArrayIndexOutOfBoundsException e) {
            return "Error: insufficient arguments";
        } catch (NumberFormatException e) {
            return "Error: second argument is not a positive integer";
        }
    }
}
