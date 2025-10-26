package com.okkoma.arkalanoix;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class GameOverUI extends UIPanel {
	
    private VBox menuBox;
    private Text gameOverText;
    private Button replayButton;
    private Button quitButton;
    
    public GameOverUI(GameScene gameScene) {
		super("GameOverUI", gameScene);
	}

    @Override
    public void set() {

        gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.WHITE);
        gameOverText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        replayButton = new Button("Rejouer");
        replayButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        replayButton.setOnAction(e -> {
            // Relancer le jeu
            hide();
            gameScene_.restartGame();
        });

        quitButton = new Button("Quitter");
        quitButton.setStyle("-fx-font-size: 20px; -fx-background-color: #f44336; -fx-text-fill: white;");
        quitButton.setOnAction(e -> {
            System.exit(0);
        });

        // Conteneur pour les éléments
        menuBox = new VBox(20, gameOverText, replayButton, quitButton);
        menuBox.setAlignment(Pos.CENTER);
        
        // Ajout au StackPane
        this.getChildren().add(menuBox);
        
        System.out.println("GameOverUI set");
    }
}