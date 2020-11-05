package com.dylantjb.tick3;


public class StringArrayLife {
    public static boolean getCell(boolean[][] world, int col, int row) {
        if (row < 0 || row > world.length - 1) return false;
        if (col < 0 || col > world[row].length - 1) return false;

        return world[row][col];
    }

    public static void setCell(boolean[][] world, int col, int row, boolean value) {
        if (getCell(world, col, row) != value) {
            world[row][col] = value;
        }
    }

    public static void print(boolean[][] world) {
        System.out.println("-");
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                System.out.print(getCell(world, col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

    public static int countNeighbours(boolean[][] world, int col, int row) {
        int neighbours = 0;
        for (int hor = -1; hor < 2; hor++) {
            for (int ver = -1; ver < 2; ver++) {
                if (hor == 0 && ver == 0) {
                    continue;
                }
                if (getCell(world, col + hor, row + ver)) {
                    neighbours++;
                }
            }
        }
        return neighbours;

    }

    public static boolean computeCell(boolean[][] world, int col, int row) {
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

    public static void nextGeneration(boolean[][] world) {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                boolean state = computeCell(world, col, row);
                setCell(world, col, row, state);
            }
        }
    }

    public static void play(boolean[][] world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            nextGeneration(world);
        }
    }

    public static void main(String[] args) throws Exception {
        String formatString = args[0];
        String[] details = formatString.split(":");
        int width = Integer.parseInt(details[2]);
        int height = Integer.parseInt(details[3]);
        boolean[][] world = new boolean[width][height];
        int startCol = Integer.parseInt(details[4]);
        int startRow = Integer.parseInt(details[5]);
        String cells = details[6];
        String[] bits = cells.split(" ");

        for (int row = 0; row < bits.length; row++) {
            char[] line = bits[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                world[startRow + row][startCol + col] = line[col] == '1';
            }
        }

        play(world);
    }
}
