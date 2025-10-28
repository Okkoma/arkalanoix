package com.okkomastudio.arkalanoix.core;

import java.awt.Color;
import java.awt.Rectangle;

public class Brick extends GameObject {
    
	private int type_;
	private int resistance_;
	private int hitPoints_;
	
    public Brick(int x, int y, int width, int height, Color color, int type) {
        super(x, y, width, height, color);
        type_ = type;
        resistance_ = type == 2 ? 2 : 1;
        reset(); 
    }
    
    @Override
    public void draw(IRenderer renderer) {
        if (!isDestroyed_) {
            renderer.setFill(color_);
            renderer.fillRect(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
            renderer.setStroke(Color.BLACK);
            renderer.strokeRect(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
        }
    }
    
    public int getCollisionDirection(GameObject other) {
    	// collide on X side
    	if (other.getRect().getMinY() >= rect_.getMinY() && other.getRect().getMaxY() <= rect_.getMaxY() &&
    		(other.getRect().getMinX() <= rect_.getMaxX() || other.getRect().getMaxX() >= rect_.getMinX())) {
    		return 2;
    	}
    	// by default collide on Y side
    	return 1;
    }
    
    public void hit() {
    	hitPoints_--;
    	if (hitPoints_ > 0)
    		color_ = color_.darker();
    	else
    		destroy();
    }
    
    public void reset() {
    	hitPoints_ = resistance_;
        isDestroyed_ = false; // Réinitialiser l'état de la brique
    }    
    
    int getType() {
    	return type_;
    }
}
