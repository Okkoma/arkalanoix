package com.okkomastudio.arkalanoix.core;

import java.awt.Color;
import java.awt.Rectangle;

public class Ball extends GameObject {

	private final int defaultSpeed_ = 3;
    private final int stickOverlap = 1;
    private int dx_ = 1, dy_ = -1;
    private int speed_;
    private GameObject parent_ = null;
    private int relx_, rely_;
    private int steel_;
    
    public Ball(int x, int y, int diameter, Color color) {
    	this.rect_ = new Rectangle(x, y, diameter, diameter);
        color_ = color;
        isDestroyed_ = false;     
        speed_ = defaultSpeed_;
        steel_ = 0;
    }

    public void move() {
    	if (parent_ == null) {
	    	rect_ = new Rectangle(rect_.x + dx_ * speed_, rect_.y + dy_ * speed_, 
	    							rect_.width, rect_.height);
	        // bounce on border 
	    	if (rect_.getMinX() <= 0 || rect_.getMinX() + rect_.getWidth() >= GameContext.getScreenWidth())
	        	dx_ *= -1;
	        if (rect_.getMinY() <= 0)
	        	dy_ *= -1;
	        else if (rect_.getMinY() >= GameContext.getScreenHeight())
	        	isDestroyed_ = true;
    	} else {
	    	rect_ = new Rectangle(parent_.getRect().x + relx_, parent_.getRect().y + rely_, 
									rect_.width, rect_.height);
    	}
    }
    
    public void putOver(GameObject other) {
    	
    	rect_ = new Rectangle(rect_.x, other.getRect().y - rect_.height, 
								rect_.width, rect_.height);    	    	
    }
    
    public void stickOn(GameObject other, boolean xCentered) {
    	
    	int x = xCentered ? other.getRect().x + other.getRect().width/2 - rect_.width/2 : rect_.x;
    		
    	rect_ = new Rectangle(x, other.getRect().y - rect_.height + stickOverlap, 
								rect_.width, rect_.height);
    	
    	parent_ = other;

    	relx_ = Math.clamp(rect_.x - other.getRect().x, -rect_.width/2, other.getRect().width - rect_.width/2);    	
    	rely_ = -rect_.height + stickOverlap;
    }
    
    public void unstick() {
    	parent_ = null;
    }
    
    boolean isStickOn(GameObject object) {
    	return object == parent_;
    }
    
    public void bounce(int dir) {
    	if (dir == 2)
    		dx_ *= -1;
    	else if (dir == 1)
    		dy_ *= -1;
    }
    
    @Override
    public void draw(IRenderer renderer) {
        if (!isDestroyed_) {
            renderer.setFill(steel_ > 0 ? Color.DARK_GRAY : color_);
            renderer.fillOval(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
        }
    }  
    
    public void setDx(int value) {
    	dx_ = value;
    }
    
    public int getDx() {
    	return dx_;
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
    
    public void setSteel(int value) {
    	steel_ = value;
    }
    
    public int getSteel() {
    	return steel_;
    }
}
