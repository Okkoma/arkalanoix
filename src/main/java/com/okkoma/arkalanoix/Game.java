package com.okkoma.arkalanoix;

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
        new GameScene(root);
                
        // Créer la scène avec le StackPane comme racine
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("ArkaLaNoïx FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
    	
        System.out.println("Game Started ...");
        launch(args);
        System.out.println("Game Exit.");
    }
}