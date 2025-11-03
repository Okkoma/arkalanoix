package com.okkomastudio.arkalanoix.core;

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
	
    public void draw(IRenderer renderer) {
    	renderer.setFill(Color.WHITE);
    	renderer.fillText(Integer.toString(life_), GameContext.getScreenWidth() / 2,
                GameContext.getScreenHeight() - GameContext.bigFontSize_,
                "Arial", GameContext.bigFontSize_, 1, 1);
    }
    
    public int get() {
    	return life_;
    }
}
