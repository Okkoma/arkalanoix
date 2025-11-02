package com.okkomastudio.arkalanoix.core;

public class Color {
    private int r, g, b, a;

    public static final Color BLACK = new Color(0,0,0);
    public static final Color WHITE = new Color(255,255,255);
    public static final Color GRAY = new Color(128,128,128);
    public static final Color DARK_GRAY = new Color(64,64,64);
    public static final Color LIGHT_GRAY = new Color(192,192,192);
    public static final Color BLUE = new Color(0,0,255);
    public static final Color CYAN = new Color(0,255,255);
    public static final Color YELLOW = new Color(255,255,0);
    public static final Color GREEN = new Color(0,255,0);
    public static final Color ORANGE = new Color(255,200,0);
    public static final Color PINK = new Color(255,175,175);
    public static final Color MAGENTA = new Color(255,0,255);
    public static final Color RED = new Color(255,0,0);

    public Color(int r, int g, int b) {
        this(r,g,b,255);
    }

    public Color(int r, int g, int b, int a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 255) return 255;
        return v;
    }

    public int getRed() { return r; }
    public int getGreen() { return g; }
    public int getBlue() { return b; }
    public int getAlpha() { return a; }

    /**
     * Return a darker version of this color (approximate).
     */
    public Color darker() {
        return new Color((int)(r * 0.7), (int)(g * 0.7), (int)(b * 0.7), a);
    }

    /**
     * Return a brighter version of this color (approximate).
     */
    public Color brighter() {
        return new Color(Math.min(255, (int)(r / 0.7)), Math.min(255, (int)(g / 0.7)), Math.min(255, (int)(b / 0.7)), a);
    }
}
