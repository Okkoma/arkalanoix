package com.okkoma.arkalanoix;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class Score {
	
	private int score_;
	
	public Score(int score) {
		score_ = score;
	}
	
    public void draw(GraphicsContext gc) {
    	gc.setTextAlign(TextAlignment.CENTER);
    	gc.setFont(Font.font("Arial", 24)); // Augmentez la taille ici
    	gc.setFill(Color.WHITE);
    	gc.fillText(Integer.toString(score_),  GameScene.screenWidth / 2, 28);
    }
    
    public void increase(int amount) {
    	score_ += amount;
    }
    
    public int get() {
    	return score_;
    }
}
