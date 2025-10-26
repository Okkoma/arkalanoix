package com.okkoma.arkalanoix;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GameObject {
	protected Rectangle2D rect_;
	protected Color color_;
	protected boolean isDestroyed_;
    
	public GameObject() {
		
        this.color_ = Color.WHITE;
        this.isDestroyed_ = false; 
	}
	
    public GameObject(double x, double y, double width, double height, Color color) {
    	
    	this.rect_ = new Rectangle2D(x, y, width, height);
        this.color_ = color;
    }
    
    public abstract void draw(GraphicsContext gc);
    
    public void destroy() {
        isDestroyed_ = true;
    }

    public Rectangle2D getRect() {
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
