package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.GameContext;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        // StackPane comme conteneur principal
        StackPane root = new StackPane();

        // Créer la scène de jeu
        GameSceneJavaFX gameScene = new GameSceneJavaFX(root);
                
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
    	
        System.out.println("Game Started ...");
        launch(args);
        System.out.println("Game Exit.");
    }
}