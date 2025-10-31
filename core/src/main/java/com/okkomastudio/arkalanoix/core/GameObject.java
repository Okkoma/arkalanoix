package com.okkomastudio.arkalanoix.core;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class GameObject {
	protected Rectangle rect_;
	protected Color color_;
	protected boolean isDestroyed_;
    
	public GameObject() {
		
        this.color_ = Color.WHITE;
        this.isDestroyed_ = false; 
	}
	
    public GameObject(int x, int y, int width, int height, Color color) {
    	
    	this.rect_ = new Rectangle(x, y, width, height);
        this.color_ = color;
    }
    
    public abstract void draw(IRenderer renderer);
    
    public void destroy() {
        isDestroyed_ = true;
    }

    public Rectangle getRect() {
        return rect_;
    }
    
    public boolean intersects(GameObject other) {
        return rect_.intersects(other.getRect());
    }
    
    public double getX() { return rect_.getMinX(); }
    public double getY() { return rect_.getMinY(); }
    public double getWidth() { return rect_.getWidth(); }
    public double getHeight() { return rect_.getHeight(); }
    public boolean isDestroyed() { return isDestroyed_; }
}
