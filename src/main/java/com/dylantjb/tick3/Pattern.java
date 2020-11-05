package com.dylantjb.tick3;

public class Pattern {
    private final String name;
    private final String author;
    private final int width;
    private final int height;
    private final int startCol;
    private final int startRow;
    private final String cells;

    public Pattern(String format) {
        String[] details = format.split(":");
        this.name = details[0];
        this.author = details[1];
        this.width = Integer.parseInt(details[2]);
        this.height = Integer.parseInt(details[3]);
        this.startCol = Integer.parseInt(details[4]);
        this.startRow = Integer.parseInt(details[5]);
        this.cells = details[6];
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public String getCells() {
        return cells;
    }

    public void initialise(boolean[][] world) {
        String[] bits = cells.split(" ");

        for (int row = 0; row < bits.length; row++) {
            char[] line = bits[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                world[startRow + row][startCol + col] = line[col] == '1';
            }
        }
    }

}
