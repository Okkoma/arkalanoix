package com.okkoma.arkalanoix;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class GameMenuUI extends UIPanel {
	
    private VBox menuBox;
    private Text gameOverText;
    private Button continueButton;
    private Button quitButton;
    
    public GameMenuUI(GameScene gameScene) {
		super("GameMenuUI", gameScene);
	}

    @Override
    public void set() {

        gameOverText = new Text("MENU");
        gameOverText.setFill(Color.WHITE);
        gameOverText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        continueButton = new Button("Commencer");
        continueButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        continueButton.setOnAction(e -> {
            hide();
            gameScene_.restartGame();
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
        
        System.out.println("GameMenuUI set");
    }
    
    @Override
    public void setState(int state) {
    	
    	System.out.println("GameMenuUI setState " + state);
    	super.setState(state);
        continueButton.setText(state == 0 ? "Commencer" : "Continuer");

        if (state == 0)
        {
        	continueButton.setOnAction(e -> {        
        		hide();
        		gameScene_.restartGame();
        	});
        } else {
        	continueButton.setOnAction(e -> {   
        		gameScene_.togglePause();
        	});
        }
    }
}