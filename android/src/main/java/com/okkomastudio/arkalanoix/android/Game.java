package com.okkomastudio.arkalanoix.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class Game extends Activity {

    private GameSceneAndroid gameScene_;
    private FrameLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Création du container principal
        mainContainer = new FrameLayout(this);

        // Créer la scène de jeu
        /*
        gameScene_ = new GameSceneAndroid(this, mainContainer);
        mainContainer.addView(gameScene_, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        */        
        // Définir la vue principale
        setContentView(mainContainer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (gameScene_ != null) {
            gameScene_.pause();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (gameScene_ != null) {
            gameScene_.resume();
        }*/
    }
}
