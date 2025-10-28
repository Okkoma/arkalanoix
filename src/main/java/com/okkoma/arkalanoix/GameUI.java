package com.okkoma.arkalanoix;

import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class GameUI extends StackPane implements IGameUI {

    private UIPanel gameOver_, gameWin_, gameMenu_;
    
    public GameUI(GameSceneJavaFX gameScene, StackPane root) {
    	
        gameOver_ = new GameOverUI(gameScene);
        gameWin_ = new GameWinUI(gameScene);      
        gameMenu_ = new GameMenuUI(gameScene);
               
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