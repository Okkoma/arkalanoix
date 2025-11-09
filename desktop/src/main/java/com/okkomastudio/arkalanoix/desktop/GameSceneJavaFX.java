package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.IControllableScene;
import com.okkomastudio.arkalanoix.core.GameScene;
import com.okkomastudio.arkalanoix.core.GameContext;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class GameSceneJavaFX extends Pane implements IControllableScene {
    private final Canvas canvas_;
    private GameScene gameScene_;

    private static Logger Log = LogManager.getLogger();

    public GameSceneJavaFX(StackPane root) {

        gameScene_ = new GameScene();
        canvas_ = new Canvas(GameContext.getScreenWidth(), GameContext.getScreenHeight());
        this.getChildren().add(canvas_);

        gameScene_.renderer_ = new RendererJavaFX(canvas_.getGraphicsContext2D());
        
        setFocusTraversable(true);

        // togglePause if unfocus
        focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if (newVal == false && gameScene_.isPlaying_) {
                Log.info("Focus changed : {}" + newVal);
        		togglePause();
        	}
        });
    
        // Gestion des touches pressées
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                case KP_LEFT:
                case Q:
                    gameScene_.leftPressed_ = true;
                    break;
                case RIGHT:
                case KP_RIGHT:
                case D:
                    gameScene_.rightPressed_ = true;
                    break;
                case SPACE:
                	gameScene_.spacePressed_ = true;
                	break;
                case M:
                	gameScene_.applyBonus(10);
                	break;
                case W:
                	gameScene_.gameWin();
                	break;
                case ESCAPE:
                	togglePause();
                	break;
                default: break;
            }
        });

        // Gestion des touches relâchées
        setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                case KP_LEFT:
                case Q:
                    gameScene_.leftPressed_ = false;
                    break;
                case RIGHT:
                case KP_RIGHT:
                case D:
                    gameScene_.rightPressed_ = false;
                    break;
                case SPACE:
                	gameScene_.spacePressed_ = false;
                	break;                    
                default: break;
            }
        });
    }
    
    public void setUIContainer(StackPane container) {
        gameScene_.ui_ = new GameUIJavaFX(this, container);
        gameScene_.ui_.setContainer(container);    
    }

    public boolean isPlaying() {
        return gameScene_.isPlaying_;
    }
    
    public void update() {
        gameScene_.update();
    }

    public void render() {
        gameScene_.render();
    }

    @Override
    public void restartGame() {
        Log.info("restartGame called");
        gameScene_.restartGame();
        requestFocus();
    }

    @Override
    public void togglePause() {
        Log.info("togglePause called");
        gameScene_.togglePause();
        requestFocus();
    }

    @Override
    public void nextLevel() {
        Log.info("nextLevel called");
        gameScene_.nextLevel();
        requestFocus();
    }

    @Override
    public void focus() {
        Log.info("focus called");
        requestFocus();
    }    
}