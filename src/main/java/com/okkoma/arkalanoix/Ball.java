package com.okkoma.arkalanoix;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GameObject {

    private double dx_ = 1, dy_ = -1;
	private float defaultSpeed_ = 3.f;
    private float speed_;
    private GameObject parent_ = null;
    private double relx_, rely_;
    private int steel_;
    private
    final int stickOverlap = 1;
    
    public Ball(double x, double y, double diameter, Color color) {
    	this.rect_ = new Rectangle2D(x, y, diameter, diameter);
        color_ = color;
        isDestroyed_ = false;     
        speed_ = defaultSpeed_;
        steel_ = 0;
    }

    public void move() {
    	if (parent_ == null) {
	    	rect_ = new Rectangle2D(rect_.getMinX() + dx_ * speed_, rect_.getMinY() + dy_ * speed_, 
	    							rect_.getWidth(), rect_.getHeight());
	        // bounce on border 
	    	if (rect_.getMinX() <= 0 || rect_.getMinX() + rect_.getWidth() >= GameScene.screenWidth)
	        	dx_ *= -1;
	        if (rect_.getMinY() <= 0)
	        	dy_ *= -1;
	        else if (rect_.getMinY() >= GameScene.screenHeight)
	        	isDestroyed_ = true;
    	} else {
	    	rect_ = new Rectangle2D(parent_.getRect().getMinX() + relx_, parent_.getRect().getMinY() + rely_, 
									rect_.getWidth(), rect_.getHeight());
    	}
    }
    
    public void putOver(GameObject other) {
    	
    	rect_ = new Rectangle2D(rect_.getMinX(), other.getRect().getMinY() - rect_.getHeight(), 
								rect_.getWidth(), rect_.getHeight());    	    	
    }
    
    public void stickOn(GameObject other, boolean xCentered) {
    	
    	double x = xCentered ? other.getRect().getMinX() + other.getRect().getWidth()/2 - rect_.getWidth()/2 : rect_.getMinX();
    		
    	rect_ = new Rectangle2D(x, other.getRect().getMinY() - rect_.getHeight() + stickOverlap, 
								rect_.getWidth(), rect_.getHeight());
    	
    	parent_ = other;

    	relx_ = Math.clamp(rect_.getMinX() - other.getRect().getMinX(), -rect_.getWidth()/2, other.getRect().getWidth() - rect_.getWidth()/2);    	
    	rely_ = -rect_.getHeight() + stickOverlap;
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
    public void draw(GraphicsContext gc) {
        if (!isDestroyed_) {
            gc.setFill(steel_ > 0 ? Color.DARKGRAY : color_);
            gc.fillOval(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
        }
    }  
    
    public void setDx(double value) {
    	dx_ = value;
    }
    
    public double getDx() {
    	return dx_;
    }
    
    public void setSpeed(float speed) {
    	speed_ = speed;
    }
    
    public float getSpeed() {
    	return speed_;
    }
        
    public void resetSpeed() {
    	setSpeed(defaultSpeed_);
    }     
    
    public void setSteel(int value) {
    	steel_ = value;
    }
    
    public float getSteel() {
    	return steel_;
    }
}
