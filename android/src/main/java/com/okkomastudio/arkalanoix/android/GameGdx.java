package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.android.renderer.gdx.GameSceneLibGDX;

import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;


public class GameGdx extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {
    private static final String TAG = "Game";

    private GameSceneLibGDX gameScene_;
    private FrameLayout rootContainer_;
    private FrameLayout gdxContainer_;
    private FrameLayout composeContainer_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Game Activity onCreate");

        rootContainer_ = new FrameLayout(this);
        rootContainer_.setId(View.generateViewId());

        gdxContainer_ = new FrameLayout(this);
        gdxContainer_.setId(View.generateViewId());
        gdxContainer_.setFocusable(true);
        gdxContainer_.setFocusableInTouchMode(true);

        composeContainer_ = new FrameLayout(this);
        composeContainer_.setId(View.generateViewId());

        // Créer la scène de jeu
        gameScene_ = new GameSceneLibGDX(this);
        gameScene_.setUIContainer(composeContainer_);

        // Créer un "GameFragment" qui héberge LibGDX
        GameFragment libgdxFragment = new GameFragment();

        getSupportFragmentManager().beginTransaction()
                .add(gdxContainer_.getId(), libgdxFragment)
                .commit();

        // Ajouter la view libgdx
        rootContainer_.addView(gdxContainer_, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        // Créer la view compose
        rootContainer_.addView(composeContainer_, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Définir la vue principale
        setContentView(rootContainer_);
    }

    // Créer une classe interne pour le Fragment LibGDX
    public static class GameFragment extends AndroidFragmentApplication {
        @Override
        public View onCreateView (LayoutInflater inflater,
                                  ViewGroup container,
                                  Bundle savedInstanceState) {
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            config.useAccelerometer = false;
            config.useCompass = false;
            config.useGyroscope = false;
            config.useImmersiveMode = true;

            return initializeForView(((GameGdx) getActivity()).getGameScene(), config);
        }
    }

    public GameSceneLibGDX getGameScene() {
        return gameScene_;
    }

    @Override
    public void exit() {
        finish();
    }
}
