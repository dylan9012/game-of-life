package com.dylantjb.tick4;

import java.io.IOException;
import java.util.List;

public class LoaderLife {
    public static void main(String[] args) {
        try {
            List<Pattern> patterns;
            String source = args[0];
            if (source.startsWith("http://") || source.startsWith("https://")) {
                patterns = PatternLoader.loadFromURL(source);
            } else {
                patterns = PatternLoader.loadFromDisk(source);
            }

            if (args.length > 1) {
                int index = Integer.parseInt(args[1]);

                Pattern p = null;
                for (Pattern pattern : patterns) {
                    if (pattern.getIndex() == index) {
                        p = pattern;
                    }
                }
                if (p == null) {
                    throw new Exception();
                }
                boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
                p.initialise(world);
                play(world);

            } else {
                for (Pattern p : patterns) {
                    System.out.println(p.getIndex() + ")" + " " + p.getName() + ":" + p.getAuthor() + ":" + p.getWidth()
                            + ":" + p.getHeight() + ":" + p.getStartCol() + ":" + p.getStartRow() + ":" + p.getCells());
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No file or url inputted");
        } catch (IOException e) {
            System.out.println("Invalid file or url");
        } catch (Exception e) {
            System.out.println("Chosen pattern is invalid");
        }
    }

    public static void play(boolean[][] world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
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

    public static boolean getCell(boolean[][] world, int col, int row) {
        if (row < 0 || row > world.length - 1) return false;
        if (col < 0 || col > world[row].length - 1) return false;

        return world[row][col];
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
}

