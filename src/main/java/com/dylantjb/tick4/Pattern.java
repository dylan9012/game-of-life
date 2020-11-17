package com.dylantjb.tick4;

public class Pattern {
    private final int index;
    private String name;
    private String author;
    private int width;
    private int height;
    private String cells;
    private int startCol;
    private int startRow;

    public Pattern(String format, int index) throws PatternFormatException {
        patternCheck(format);
        this.index = index;
        assignDetails(format);
    }

    private void patternCheck(String format) throws PatternFormatException {
        if (format.indexOf(':') == -1) {
            throw new PatternFormatException(errorMessage(1));
        }

        String[] details = format.split(":");

        for (int i = 2; i < 6; i++) {
            try {
                Integer.parseInt(details[i]);
            } catch (Exception e) {
                throw new PatternFormatException(errorMessage(i));
            }
        }

        if (details[6] == null) {
            throw new PatternFormatException(errorMessage(6));
        }
    }

    private void assignDetails(String format) {
        String[] details = format.split(":");
        this.name = details[0];
        this.author = details[1];
        this.width = Integer.parseInt(details[2]);
        this.height = Integer.parseInt(details[3]);
        this.startCol = Integer.parseInt(details[4]);
        this.startRow = Integer.parseInt(details[5]);
        this.cells = details[6];
    }

    private String errorMessage(int type) {
        switch (type) {
            case 1:
                return "Pattern has an incorrect structure (no colon)";
            case 2:
                return "Pattern does not have a defined width";
            case 3:
                return "Pattern does not have a defined height";
            case 4:
                return "Pattern does not have a defined start column";
            case 5:
                return "Pattern does not have a defined start row";
            case 6:
                return "Pattern does not have appropriate cells";
            default:
                return "Pattern has generated an error";
        }
    }


    public Pattern(String format) throws PatternFormatException {
        patternCheck(format);
        this.index = 0;
        assignDetails(format);
    }

    public int getIndex() {
        return index;
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

    public void initialise(boolean[][] world) throws PatternFormatException {
        String[] bits = cells.split(" ");

        for (int i = 0; i < cells.length(); i++) {
            char digit = cells.charAt(i);
            if (digit != '0' && digit != '1' && digit != ' ') {
                throw new PatternFormatException(errorMessage(6));
            }
        }

        for (int row = 0; row < bits.length; row++) {
            char[] line = bits[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                world[startRow + row][startCol + col] = line[col] == '1';
            }
        }
    }

}
