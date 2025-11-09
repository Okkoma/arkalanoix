package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.GameContext;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Game extends Application {

    private static Logger Log = LogManager.getLogger();

    @Override
    public void start(Stage primaryStage) {
    	
        // StackPane comme conteneur principal
        StackPane root = new StackPane();

        // Créer la scène de jeu
        GameSceneJavaFX gameScene = new GameSceneJavaFX(root);
        gameScene.setUIContainer(root);

        // Créer la scène avec le StackPane comme racine
        Scene scene = new Scene(root, GameContext.getScreenWidth(), GameContext.getScreenHeight());
        
        primaryStage.setTitle("ArkaLaNoïx FX");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Boucle du jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	
            	if (!gameScene.isPlaying())
        			return;
            	
                gameScene.update();
                gameScene.render();
            }
        }.start();        
    }
    
    public static void main(String[] args) {
    	
        Log.info("Game Started ...");
        launch(args);
        Log.info("Game Exit.");
    }
}