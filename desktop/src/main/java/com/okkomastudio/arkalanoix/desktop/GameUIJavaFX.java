package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.IGameUI;
import com.okkomastudio.arkalanoix.core.IUIPanel;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class GameUIJavaFX extends StackPane implements IGameUI {

    private final GameSceneJavaFX gameScene_;
    private final UIPanelJavaFX gameOver_, gameWin_, gameMenu_;
    
    public GameUIJavaFX(GameSceneJavaFX gameScene, StackPane root) {
    	
        gameScene_ = gameScene;
        gameOver_ = new GameOverUIJavaFX(gameScene);
        gameWin_ = new GameWinUIJavaFX(gameScene);      
        gameMenu_ = new GameMenuUIJavaFX(gameScene);
    }

    @Override
    public void setContainer(Object root)
    {
        StackPane container = (StackPane)root;
        if (container != null) {
            container.getChildren().addAll(gameScene_, gameOver_, gameWin_, gameMenu_);        
            this.setAlignment(Pos.CENTER);
        }

        gameMenu_.show();
    }

    @Override
    public IUIPanel getGameOverPanel() {
        return gameOver_;
    }
    
    @Override
    public IUIPanel getGameWinPanel() {
        return gameWin_;
    }

    @Override
    public IUIPanel getGameMenuPanel() {
        return gameMenu_;
    }
}