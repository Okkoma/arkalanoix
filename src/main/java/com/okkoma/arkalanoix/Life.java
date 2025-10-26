package com.okkoma.arkalanoix;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Life {
	
	private int life_ = 3;
	
	public Life(int life) {
		life_ = life;
	}
	
	public void add() {
		life_++;
	}
	
	public void remove() {
		life_--;
	}
	
    public void draw(GraphicsContext gc) {
    	gc.setTextAlign(TextAlignment.CENTER);
    	gc.setFont(Font.font("Arial", 18));
    	gc.setFill(Color.WHITE);
    	gc.fillText(Integer.toString(life_), GameScene.screenWidth / 2, GameScene.screenHeight - 18);
    }
    
    public int get() {
    	return life_;
    }
}
