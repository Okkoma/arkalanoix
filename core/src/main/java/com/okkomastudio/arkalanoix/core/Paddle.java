package com.okkomastudio.arkalanoix.core;

import java.lang.Math;

public class Paddle extends GameObject {

	private int defaultWidth_;
	private int defaultSpeed_;
    private int speed_;
    private boolean isSticky_;
    
    public Paddle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        defaultWidth_ = width;
        defaultSpeed_ = GameContext.getScreenWidth() / 80;
        speed_ = defaultSpeed_;
        isSticky_ = false;
    }
    
    public void move(int inc) {
    	
        int x = Math.min(Math.max(rect_.x + inc * speed_, 0), GameContext.getScreenWidth() - rect_.width);
        if (x != rect_.x) 
        	rect_ = new Rectangle(x, rect_.y, rect_.width, rect_.height);
    }
    
    @Override
    public void draw(IRenderer renderer) {
    	
        if (!isDestroyed_) {
            renderer.setFill(color_);
            renderer.fillRoundRect(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight(), 20.0, 5.0);
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
