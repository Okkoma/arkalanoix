package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IControllableScene;
import com.okkomastudio.arkalanoix.core.IGameUI;
import com.okkomastudio.arkalanoix.core.IUIPanel;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GameUIAndroid implements IGameUI {

    private UIPanelAndroid gameOver_, gameWin_, gameMenu_;

    public GameUIAndroid(Context context, IControllableScene gameScene) {
    	
        gameOver_ = new GameOverUIAndroid(context, gameScene);
        gameWin_ = new GameWinUIAndroid(context, gameScene);
        gameMenu_ = new GameMenuUIAndroid(context, gameScene);
               
        // Configuration des layouts pour qu'ils prennent toute la place
        setupPanelLayout(gameOver_);
        setupPanelLayout(gameWin_);
        setupPanelLayout(gameMenu_);
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
    public void setContainer(Object viewGroup)
    {
        ViewGroup container = (ViewGroup)viewGroup;
        if (container != null) {
            container.addView(gameOver_);
            container.addView(gameWin_);
            container.addView(gameMenu_);
        }
        hideAllPanels();
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
