package com.okkomastudio.arkalanoix.core;

public class Score {
	
	private int score_;
	
	public Score(int score) {
		score_ = score;
	}
	
    public void draw(IRenderer renderer) {
		renderer.setFill(Color.WHITE);
    	renderer.fillText(Integer.toString(score_), GameContext.getScreenWidth() / 2, 
						GameContext.bigFontSize_ + 5, "Arial", 
						GameContext.bigFontSize_, 1, 1);
    }
    
    public void increase(int amount) {
    	score_ += amount;
    }
    
    public int get() {
    	return score_;
    }
}
