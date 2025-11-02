package com.okkomastudio.arkalanoix.core;

public class Rectangle {
    public int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public double getMinX() { return x; }
    public double getMinY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public double getMaxX() { return x + width; }
    public double getMaxY() { return y + height; }

    public boolean intersects(Rectangle other) {
        return this.x < other.x + other.width && this.x + this.width > other.x
            && this.y < other.y + other.height && this.y + this.height > other.y;
    }
}
