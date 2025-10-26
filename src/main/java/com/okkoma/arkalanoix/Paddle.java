package com.okkoma.arkalanoix;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GameObject {

	private double defaultWidth_;
	private float defaultSpeed_ = 10.f;
    private float speed_;
    
    private boolean isSticky_;
    
    public Paddle(double x, double y, double width, double height, Color color) {
        super(x, y, width, height, color);
        defaultWidth_ = width;
        speed_ = defaultSpeed_;
        isSticky_ = false;
    }
    
    public void move(int inc) {
    	
        double x = Math.clamp(rect_.getMinX() + inc * speed_, 0., GameScene.screenWidth - rect_.getWidth());
        if (x != rect_.getMinX()) 
        	rect_ = new Rectangle2D(x, rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
    }
    
    @Override
    public void draw(GraphicsContext gc) {
    	
        if (!isDestroyed_) {
            gc.setFill(color_);
            gc.fillRoundRect(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight(), 20.0, 5.0);
        }
    }
    
    public void setWidth(double width) {
    	rect_ = new Rectangle2D(rect_.getMinX(), rect_.getMinY(), width, rect_.getHeight());
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
    
    public void setSpeed(float speed) {
    	speed_ = speed;
    }
    
    public float getSpeed() {
    	return speed_;
    }
    
    public void resetSpeed() {
    	setSpeed(defaultSpeed_);
    }    

}
