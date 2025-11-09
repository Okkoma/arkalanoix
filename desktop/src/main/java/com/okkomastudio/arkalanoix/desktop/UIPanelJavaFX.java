package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.IUIPanel;
import com.okkomastudio.arkalanoix.core.GameContext;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public abstract class UIPanelJavaFX extends StackPane implements IUIPanel {

    private static final Logger Log = LogManager.getLogger();

    private final String name_;
    private int state_ = 0;
    
	protected GameSceneJavaFX gameScene_;

    public UIPanelJavaFX(String name, GameSceneJavaFX gameScene) {
    	
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
        Log.info("GameUI : {} show.", name_);
    }

    @Override
    public void hide() {
    	
        this.setVisible(false);
        Log.info("GameUI : {} hide.", name_);
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