package com.okkoma.arkalanoix;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

public abstract class UIPanel extends StackPane implements IUIPanel {

    private String name_;
    private int state_ = 0;
    
    @SuppressWarnings("unused")
	protected GameSceneJavaFX gameScene_;

    public UIPanel(String name, GameSceneJavaFX gameScene) {
    	
        gameScene_ = gameScene;
        name_ = name;
        
        // Fond semi-transparent qui couvre toute la scène
        this.getChildren().add(new Rectangle(GameContext.getScreenWidth(), GameContext.getScreenHeight(), new javafx.scene.paint.Color(0., 0., 0., 0.7)));
        
        set();

        this.setAlignment(Pos.CENTER);
        this.setVisible(false);
    }
    
    @Override
    public void show() {
    	
        this.setVisible(true);
        this.toFront();
        System.out.printf("GameUI : %s show.\n", name_);
    }

    @Override
    public void hide() {
    	
        this.setVisible(false);
        System.out.printf("GameUI : %s hide.\n", name_);
    }

    @Override
	public int getState() {
		return state_;
	}

    @Override
	public void setState(int state) {
		state_ = state;
	}
}