package com.dylantjb.tick3star;

public class AnimatedLife {
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

    public static boolean[][] nextGeneration(boolean[][] world) {
        boolean[][] nextWorld = new boolean[world.length][world[0].length];
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                boolean state = computeCell(world, col, row);
                setCell(nextWorld, col, row, state);
            }
        }
        return nextWorld;
    }


    public static void main(String[] args) throws Exception {
        Pattern p = new Pattern(args[0]);
        int generations = Integer.parseInt(args[1]);
        String filename = System.getProperty("user.dir") + "/src/main/java/com/dylantjb/tick3star/" + args[2];

        OutputAnimatedGif animation = new OutputAnimatedGif(filename);

        boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
        p.initialise(world);
        animation.addFrame(world);

        for (int i = 0; i < generations - 1; i++) {
            world = nextGeneration(world);
            animation.addFrame(world);
        }

        animation.close();
    }
}