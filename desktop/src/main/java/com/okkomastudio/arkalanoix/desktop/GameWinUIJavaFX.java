package com.okkomastudio.arkalanoix.desktop;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class GameWinUIJavaFX extends UIPanelJavaFX {
	
	private VBox menuBox;
    private Text text;
    private Button replayButton;

    public GameWinUIJavaFX(GameSceneJavaFX gameScene) {
		super("GameWinUI", gameScene);
	}

    @Override
    public void set() {

        text = new Text("NEXT LEVEL");
        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        replayButton = new Button("Continuer");
        replayButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        replayButton.setOnAction(e -> {
            // Relancer le jeu
            hide();
            gameScene_.nextLevel();
        });

        // Conteneur pour les éléments
        menuBox = new VBox(20, text, replayButton);
        menuBox.setAlignment(Pos.CENTER);

        // Ajout au StackPane
        this.getChildren().add(menuBox);

        System.out.println("GameWinUI set");
    }
}