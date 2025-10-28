package com.okkoma.arkalanoix;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class GameQuitUIJavaFX extends UIPanelJavaFX {
	
    private VBox menuBox;
    private Text gameOverText;
    private Button continueButton;
    private Button quitButton;
    
    public GameQuitUIJavaFX(GameSceneJavaFX gameScene) {
		super("GameQuitUI", gameScene);
	}

    @Override
    public void set() {

        gameOverText = new Text("QUIT");
        gameOverText.setFill(javafx.scene.paint.Color.WHITE);
        gameOverText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        continueButton = new Button("Continuer");
        continueButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        continueButton.setOnAction(e -> {
            hide();
        });

        quitButton = new Button("Quitter");
        quitButton.setStyle("-fx-font-size: 20px; -fx-background-color: #f44336; -fx-text-fill: white;");
        quitButton.setOnAction(e -> {
            System.exit(0);
        });

        // Conteneur pour les éléments
        menuBox = new VBox(20, gameOverText, continueButton, quitButton);
        menuBox.setAlignment(Pos.CENTER);
        
        // Ajout au StackPane
        this.getChildren().add(menuBox);

        System.out.println("GameQuitUI set");
    }
}