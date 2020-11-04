package com.dylantjb.tick2;

public class TinyLife {
    public static boolean getCell(long world, int col, int row) {
        if (col < 0 || col > 7 || row < 0 || row > 7) {
            return false;
        }
        int pos = 8 * row + col;
        return PackedLong.get(world, pos);
    }

    public static long setCell(long world, int col, int row, boolean value) {
        if (getCell(world, col, row) != value) {
            int pos = 8 * row + col;
            return PackedLong.set(world, pos, value);
        }
        return world;
    }

    public static void print(long world) {
        System.out.println("-");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.print(getCell(world, col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

    public static int countNeighbours(long world, int col, int row) {
        int neighbours = 0;
        for (int hor = -1; hor < 2; hor++) {
            for (int ver = -1; ver < 2; ver++) {
                if (hor != 0 || ver != 0) {
                    if (getCell(world, col + hor, row + ver)) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;

    }

    public static boolean computeCell(long world, int col, int row) {
        // liveCell is true if the cell at position (col,row) in world is live
        boolean liveCell = getCell(world, col, row);

        // neighbours is the number of live neighbours to cell (col,row)
        int neighbours = countNeighbours(world, col, row);

        // we will return this value at the end of the method to indicate whether
        // cell (col,row) should be live in the next generation
        boolean nextCell = false;

        // A live cell with two or three neighbours lives (a balanced population)
        if (liveCell && neighbours >= 2 && neighbours < 4) {
            nextCell = true;
        }
        // A dead cell with three live neighbours comes alive
        if (!liveCell && neighbours == 3) {
            nextCell = true;
        }
        return nextCell;
    }

    public static long nextGeneration(long world) {
        long nextWorld = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean state = computeCell(world, col, row);
                nextWorld = setCell(nextWorld, col, row, state);
            }
        }
        return nextWorld;
    }

    public static void play(long world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
        }
    }

    public static void main(String[] args) throws Exception {
        play(Long.decode(args[0]));
    }
}
