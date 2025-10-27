package com.okkoma.arkalanoix;

import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GameObject {

	private int defaultWidth_;
	private int defaultSpeed_ = 10;
    private int speed_;
    
    private boolean isSticky_;
    
    public Paddle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        defaultWidth_ = width;
        speed_ = defaultSpeed_;
        isSticky_ = false;
    }
    
    public void move(int inc) {
    	
        int x = Math.clamp(rect_.x + inc * speed_, 0, GameScene.screenWidth - rect_.width);
        if (x != rect_.x) 
        	rect_ = new Rectangle(x, rect_.y, rect_.width, rect_.height);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
    	
        if (!isDestroyed_) {
            gc.setFill(color_);
            gc.fillRoundRect(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight(), 20.0, 5.0);
        }
    }
    
    public void setWidth(int width) {
    	rect_ = new Rectangle(rect_.x, rect_.y, width, rect_.height);
    }
    
    public void resetWidth() {
    	setWidth(defaultWidth_);
    }
    
    public void setSticky(boolean enable) {
    	isSticky_ = enable;
    }
    
    public boolean isSticky() {
    	return isSticky_;
    }
    
    public void setSpeed(int speed) {
    	speed_ = speed;
    }
    
    public int getSpeed() {
    	return speed_;
    }
    
    public void resetSpeed() {
    	setSpeed(defaultSpeed_);
    }    

}
