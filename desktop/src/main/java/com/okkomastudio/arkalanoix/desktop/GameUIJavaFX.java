package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.IGameUI;
import com.okkomastudio.arkalanoix.core.IUIPanel;

import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class GameUIJavaFX extends StackPane implements IGameUI {

    private final UIPanelJavaFX gameOver_, gameWin_, gameMenu_;
    
    public GameUIJavaFX(GameSceneJavaFX gameScene, StackPane root) {
    	
        gameOver_ = new GameOverUIJavaFX(gameScene);
        gameWin_ = new GameWinUIJavaFX(gameScene);      
        gameMenu_ = new GameMenuUIJavaFX(gameScene);
               
        // Ajouter les éléments au StackPane (l'ordre d'ajout est important)
        root.getChildren().addAll(gameScene, gameOver_, gameWin_, gameMenu_);        
        this.setAlignment(Pos.CENTER);
    
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