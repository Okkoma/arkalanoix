package com.okkomastudio.arkalanoix.android.renderer.gdx;

import com.okkomastudio.arkalanoix.core.IControllableScene;
import com.okkomastudio.arkalanoix.core.GameContext;
import com.okkomastudio.arkalanoix.core.GameScene;

import com.okkomastudio.arkalanoix.android.ui.compose.GameUIAndroidCompose;

import android.content.Context;
import android.view.ViewGroup;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;

import android.util.Log;

public class GameSceneLibGDX implements
        ApplicationListener,
        IControllableScene
    {
    
    private static final String DEBUG_TAG = "Gestures";
    private static final String APP_TAG = "App";
    
    private GameScene gameScene_;
    private RendererLibGDX renderer_;
    private boolean isRunning_;
    private boolean initialized_;
    
    private Context androidContext_;
    private GestureDetector gestureDetector_;

    public GameSceneLibGDX(Context context) {

        androidContext_ = context;
        
        gameScene_ = new GameScene();
        renderer_ = new RendererLibGDX();
        gameScene_.renderer_ = renderer_;
        
        isRunning_ = false;
        initialized_ = false;

        Log.i(APP_TAG, "GameSceneLibGDX constructor completed");
    }

    public void setUIContainer(ViewGroup container) {
        gameScene_.ui_ = new GameUIAndroidCompose(androidContext_, this);
        gameScene_.ui_.setContainer(container);
    }

    // ApplicationListener methods (LibGDX lifecycle)
    @Override
    public void create() {
        Log.i(APP_TAG, "LibGDX create() called");
        
        try {
            // Initialize the renderer
            if (renderer_ != null) {
                renderer_.initialize();
                Log.i(APP_TAG, "Renderer initialized successfully");
            }
            
            // Set up initial screen dimensions
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();
            GameContext.setScreenWidth(width);
            GameContext.setScreenHeight(height);
            GameContext.smallFontSize_ = height / 40;
            GameContext.mediumFontSize_ = height / 30;
            GameContext.bigFontSize_ = height / 20;
            
            // Set up projection matrices for coordinate system
            // LibGDX uses bottom-left origin, but we want top-left like Android Canvas
            com.badlogic.gdx.math.Matrix4 projection = new com.badlogic.gdx.math.Matrix4();
            projection.setToOrtho2D(0, 0, width, height);
            projection.translate(0, height, 0);
            projection.scale(1, -1, 1);
            
            // Apply projection to renderer components
            if (renderer_.isInitialized()) {
                ShapeRenderer shapeRenderer = renderer_.getShapeRenderer();
                SpriteBatch spriteBatch = renderer_.getSpriteBatch();
                if (shapeRenderer != null) {
                    shapeRenderer.setProjectionMatrix(projection);
                }
                if (spriteBatch != null) {
                    spriteBatch.setProjectionMatrix(projection);
                }
            }

            gestureDetector_ = new GestureDetector(new GestureDetector.GestureAdapter() {
                    @Override
                    public boolean touchDown(float x, float y, int pointer, int button) {
                        Log.d(DEBUG_TAG, "adapter touchDown: ");
                        if (gameScene_.paddle_ != null)
                        {
                            if (x < gameScene_.paddle_.getX() + gameScene_.paddle_.getWidth()/2) {
                                gameScene_.leftPressed_ = true;
                                Log.d(DEBUG_TAG, "Left pressed");
                            } else {
                                gameScene_.rightPressed_ = true;
                                Log.d(DEBUG_TAG, "Right pressed");
                            }
                            return true;
                        }
                        return false;
                    }
                    @Override
                    public boolean tap(float x, float y, int count, int button) {
                        Log.d(DEBUG_TAG, "adapter tap: ");
                        if (count == 1) {
                            // Action on single tap
                            gameScene_.spacePressed_ = true;
                            Log.d(DEBUG_TAG, "Single tap: ");
                        } else if (count == 2) {
                            // Action on double tap
                            Log.d(DEBUG_TAG, "Double tap: ");
                        }
                        return true;
                    }
                }) {
                @Override
                public boolean touchUp (int x, int y, int pointer, int button) {
                    Log.d(DEBUG_TAG, "touchUp: ");
                    gameScene_.leftPressed_ = false;
                    gameScene_.rightPressed_ = false;
                    gameScene_.spacePressed_ = false;
                    return super.touchUp(x, y, pointer, button);
                }
            };

            Gdx.input.setInputProcessor(gestureDetector_);

            initialized_ = true;
            isRunning_ = true;

            Log.i(APP_TAG, String.format("GameSceneLibGDX created - w:%d h:%d", width, height));
            
        } catch (Exception e) {
            Log.e(APP_TAG, "Error in create()", e);
        }
    }
    
    @Override
    public void resize(int width, int height) {
        Log.i(APP_TAG, String.format("LibGDX resize w:%d h:%d", width, height));
        
        // Update game context
        GameContext.setScreenWidth(width);
        GameContext.setScreenHeight(height);
        GameContext.smallFontSize_ = height / 40;
        GameContext.mediumFontSize_ = height / 30;
        GameContext.bigFontSize_ = height / 20;
        
        // Update projection matrices for new dimensions
        if (initialized_ && renderer_ != null && renderer_.isInitialized()) {
            com.badlogic.gdx.math.Matrix4 projection = new com.badlogic.gdx.math.Matrix4();
            projection.setToOrtho2D(0, 0, width, height);
            projection.translate(0, height, 0);
            projection.scale(1, -1, 1);
            
            ShapeRenderer shapeRenderer = renderer_.getShapeRenderer();
            SpriteBatch spriteBatch = renderer_.getSpriteBatch();
            if (shapeRenderer != null) {
                shapeRenderer.setProjectionMatrix(projection);
            }
            if (spriteBatch != null) {
                spriteBatch.setProjectionMatrix(projection);
            }

            if (gameScene_.ui_ != null) {
                gameScene_.ui_.getGameMenuPanel().show();
            }
        }

        if (gameScene_.ui_ == null) {
            restartGame();
        }
    }
    
    public boolean isPlaying() {
        return gameScene_ != null && gameScene_.isPlaying_;
    }

    public void update() {
        if (gameScene_ != null && gameScene_.isPlaying_) {
            gameScene_.update();
        }
    }

    @Override
    public void render() {
        if (!initialized_ || !isRunning_) {
            return;
        }
        
        try {
            // Update game logic
            update();
            // Render game scene
            if (gameScene_ != null && renderer_ != null && renderer_.isInitialized()) {
                renderer_.begin();
                gameScene_.render();            
                renderer_.end();
            }
            
        } catch (Exception e) {
            Log.e(APP_TAG, "Error in render loop", e);
        }
    }

    @Override
    public void pause() {
        Log.i(APP_TAG, "GameSceneLibGDX pause() called");
        if (gameScene_ != null) {
            gameScene_.pause();
        }
        isRunning_ = false;
    }
    
    @Override
    public void resume() {
        Log.i(APP_TAG, "GameSceneLibGDX resume() called");
        if (gameScene_ != null && gameScene_.paddle_ != null) {
            gameScene_.resume();
        }
        isRunning_ = true;
    }

    @Override
    public void dispose() {
        Log.i(APP_TAG, "GameSceneLibGDX dispose() called");
        if (renderer_ != null) {
            renderer_.dispose();
        }
        isRunning_ = false;
        initialized_ = false;
    }
    
    // Game control methods
    @Override
    public void restartGame() {
        Log.i(APP_TAG, "restartGame called");
        if (gameScene_ != null) {
            gameScene_.restartGame();
        }
    }

    @Override
    public void togglePause() {
        Log.i(APP_TAG, "togglePause called");
        if (gameScene_ != null) {
            gameScene_.togglePause();
        }
    }

    @Override
    public void nextLevel() {
        Log.i(APP_TAG, "nextLevel called");
        if (gameScene_ != null) {
            gameScene_.nextLevel();
        }
    }

    @Override
    public void focus() {
        Log.i(APP_TAG, "focus called");

    }
}