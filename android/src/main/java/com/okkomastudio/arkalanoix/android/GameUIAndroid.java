package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IGameUI;
import com.okkomastudio.arkalanoix.core.IUIPanel;

import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GameUIAndroid implements IGameUI {

    private UIPanelAndroid gameOver_, gameWin_, gameMenu_;

    public GameUIAndroid(GameSceneAndroid gameScene, ViewGroup container) {
    	
        gameOver_ = new GameOverUIAndroid(gameScene.getContext(), gameScene);
        gameWin_ = new GameWinUIAndroid(gameScene.getContext(), gameScene);      
        gameMenu_ = new GameMenuUIAndroid(gameScene.getContext(), gameScene);
               
        // Configuration des layouts pour qu'ils prennent toute la place
        setupPanelLayout(gameOver_);
        setupPanelLayout(gameWin_);
        setupPanelLayout(gameMenu_);
        
        // Ajouter les éléments au container parent
        container.addView(gameOver_);
        container.addView(gameWin_);
        container.addView(gameMenu_);
        
        // Masquer tous les panels sauf le menu
        hideAllPanels();
        gameMenu_.show();
    }

    private void setupPanelLayout(UIPanelAndroid panel) {
        // S'assurer que le panel prend toute la place
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        );
        panel.setLayoutParams(params);
    }

    private void hideAllPanels() {
        gameOver_.hide();
        gameWin_.hide();
        gameMenu_.hide();
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