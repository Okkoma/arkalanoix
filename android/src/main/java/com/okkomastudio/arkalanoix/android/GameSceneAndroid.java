package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.GameScene;
import com.okkomastudio.arkalanoix.core.IControllableScene;
import com.okkomastudio.arkalanoix.core.GameContext;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;

import androidx.core.view.GestureDetectorCompat;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import android.util.Log;

public class GameSceneAndroid extends SurfaceView
        implements SurfaceHolder.Callback, Runnable,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        IControllableScene {

    private SurfaceHolder holder;
    private Thread thread;
    private Canvas canvas_;
    private GameScene gameScene_;
    private boolean isRunning_;
    private RendererAndroid renderer_;
    private static final String DEBUG_TAG = "Gestures";
    private static final String APP_TAG = "App";
    private GestureDetectorCompat gestureDetector_;

    public GameSceneAndroid(Context context) {
        super(context);

        gameScene_ = new GameScene();
        holder = getHolder();
        holder.addCallback(this);
        isRunning_ = false;

        // Configuration pour la transparence
        this.setZOrderMediaOverlay(true);
        holder.setFormat(PixelFormat.TRANSLUCENT);

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener.
        gestureDetector_ = new GestureDetectorCompat(context, this);
        // Set the gesture detector as the double-tap
        // listener.
        gestureDetector_.setOnDoubleTapListener(this);
    }

    public void setUIContainer(ViewGroup container) {
        gameScene_.ui_ = new GameUIAndroid(getContext(), this);
        gameScene_.ui_.setContainer(container);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector_.onTouchEvent(event)) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < GameContext.getScreenWidth() / 2) {
                    gameScene_.leftPressed_ = true;
                } else {
                    gameScene_.rightPressed_ = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                gameScene_.leftPressed_ = false;
                gameScene_.rightPressed_ = false;
                gameScene_.spacePressed_ = false;
                break;
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return false;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        //Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        togglePause();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        gameScene_.spacePressed_ = true;
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        isRunning_ = true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        GameContext.setScreenWidth(width);
        GameContext.setScreenHeight(height);
        GameContext.smallFontSize_ = height / 40;
        GameContext.mediumFontSize_ = height / 30;
        GameContext.bigFontSize_ = height / 20;
        Log.i(APP_TAG, String.format("surfaceChanged w:%d h:%d\n", width, height));

        gameScene_.ui_.getGameMenuPanel().show();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning_ = false;
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (isRunning_) {
            if (isPlaying()) {
                update();
                render();
            }
            
            // Contrôle de la fréquence de rafraîchissement
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        return gameScene_.isPlaying_;
    }
    
    public void update() {
        gameScene_.update();
    }

    public void render() {
        if (!holder.getSurface().isValid()) {
            return;
        }
        
        canvas_ = null;
        try {
            // Récupérer le canvas pour dessiner
            canvas_ = holder.lockCanvas();
            synchronized (holder) {
                if (canvas_ != null) {
                    // Créer ou mettre à jour le renderer avec le canvas actuel
                    if (renderer_ == null) {
                        renderer_ = new RendererAndroid(canvas_);
                        gameScene_.renderer_ = renderer_;
                    } else {
                        renderer_.setCanvas(canvas_);
                    }
                    
                    // Effacer l'écran en le rendant transparent
                    canvas_.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    
                    // Appeler le rendu de la scène de jeu
                    gameScene_.render();
                }
            }
        } finally {
            if (canvas_ != null) {
                try {
                    // Libérer le canvas et afficher le dessin
                    holder.unlockCanvasAndPost(canvas_);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void resume() {
        if (gameScene_.paddle_ != null)
            gameScene_.resume();
    }

    public void pause() {
        gameScene_.pause();
    }

    @Override
    public void restartGame() {
        Log.i(APP_TAG, "restartGame called");
        gameScene_.restartGame();
    }

    @Override
    public void togglePause() {
        Log.i(APP_TAG, "togglePause called");
        gameScene_.togglePause();
    }

    @Override
    public void nextLevel() {
        Log.i(APP_TAG, "nextLevel called");
        gameScene_.nextLevel();
    }

    @Override
    public void focus() {
        Log.i(APP_TAG, "focus called");
        requestFocus();
    }      
}
