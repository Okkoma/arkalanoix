package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameSceneJavaFX extends Pane {
    private final Canvas canvas_;
    private GameScene gameScene_;

    public GameSceneJavaFX(StackPane root) {

        gameScene_ = new GameScene();
        canvas_ = new Canvas(GameContext.getScreenWidth(), GameContext.getScreenHeight());
        this.getChildren().add(canvas_);

        gameScene_.renderer_ = new RendererJavaFX(canvas_.getGraphicsContext2D());
        gameScene_.ui_ = new GameUIJavaFX(this, (StackPane)root);

        setFocusTraversable(true);

        // togglePause if unfocus
        focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if (newVal == false && gameScene_.isPlaying_) {
        		System.out.println("Focus changed : " + newVal);
        		togglePause();
                requestFocus();
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

    public boolean isPlaying() {
        return gameScene_.isPlaying_;
    }
    
    public void update() {
        gameScene_.update();
    }

    public void render() {
        gameScene_.render();
    }    
    
    public void togglePause() {
        gameScene_.togglePause();
        requestFocus();
    }

    public void nextLevel() {
        gameScene_.nextLevel();
        requestFocus();
    }

    public void restartGame() {
        gameScene_.restartGame();
        requestFocus();
    }
}