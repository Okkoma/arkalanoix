package com.okkoma.arkalanoix;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

public abstract class UIPanel extends StackPane {

    private String name_;
    private int state_ = 0;
    
    @SuppressWarnings("unused")
	protected GameScene gameScene_;

    public UIPanel(String name, GameScene gameScene) {
    	
        gameScene_ = gameScene;
        name_ = name;
        
        // Fond semi-transparent qui couvre toute la scène
        @SuppressWarnings("unused")
		Rectangle overlay = new Rectangle(GameScene.screenWidth, GameScene.screenHeight, Color.rgb(0, 0, 0, 0.7));

        this.getChildren().add(overlay);
        
        set();
        
        this.setAlignment(Pos.CENTER);
        this.setVisible(false);
    }

    public abstract void set();
    
    public void show() {
    	
        this.setVisible(true);
        this.toFront();
        System.out.printf("GameUI : %s show.\n", name_);
    }

    public void hide() {
    	
        this.setVisible(false);
        System.out.printf("GameUI : %s hide.\n", name_);
    }

	public int getState() {
		return state_;
	}

	public void setState(int state) {
		state_ = state;
	}
}