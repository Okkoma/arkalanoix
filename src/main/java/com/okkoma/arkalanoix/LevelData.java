package com.okkoma.arkalanoix;

public class LevelData {
    private int id;
    private int rows;
    private int cols;
    private int[][] pattern;

    public int getId() { return id; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int[][] getPattern() { return pattern; }

    public void setId(int id) { this.id = id; }
    public void setRows(int rows) { this.rows = rows; }
    public void setCols(int cols) { this.cols = cols; }
    public void setPattern(int[][] pattern) { this.pattern = pattern; }
}