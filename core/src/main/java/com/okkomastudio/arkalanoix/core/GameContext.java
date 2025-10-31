package com.okkomastudio.arkalanoix.core;

public class GameContext {
    public static int screenWidth_ = 800;
    public static int screenHeight_ = 600;

    public static int getScreenWidth() {
        return screenWidth_;
    }

    public static int getScreenHeight() {
        return screenHeight_;
    }

    public static void setScreenWidth(int w) {
        screenWidth_ = w;
    }

    public static void setScreenHeight(int h) {
        screenHeight_ = h;
    }
}
