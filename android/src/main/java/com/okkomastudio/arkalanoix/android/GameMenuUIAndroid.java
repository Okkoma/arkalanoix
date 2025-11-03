package com.okkomastudio.arkalanoix.android;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameMenuUIAndroid extends UIPanelAndroid {
	
    private LinearLayout menuBox;
    private TextView gameOverText;
    private Button continueButton;
    private Button quitButton;
    
    public GameMenuUIAndroid(Context context, GameSceneAndroid gameScene) {
        super(context, "GameMenuUI", gameScene);
    }

    @Override
    public void set() {
        // Création du TextView pour le titre
        gameOverText = new TextView(getContext());
        gameOverText.setText("MENU");
        gameOverText.setTextColor(Color.WHITE);
        gameOverText.setTextSize(48);
        gameOverText.setTypeface(null, android.graphics.Typeface.BOLD);
        gameOverText.setGravity(Gravity.CENTER);

        // Création du bouton Continuer/Commencer
        continueButton = new Button(getContext());
        continueButton.setText("Commencer");
        continueButton.setTextSize(20);
        continueButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        continueButton.setTextColor(Color.WHITE);

        // Création du bouton Quitter
        quitButton = new Button(getContext());
        quitButton.setText("Quitter");
        quitButton.setTextSize(20);
        quitButton.setBackgroundColor(Color.parseColor("#f44336"));
        quitButton.setTextColor(Color.WHITE);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pour quitter l'application Android
                if (getContext() instanceof android.app.Activity) {
                    ((android.app.Activity) getContext()).finish();
                }
                System.exit(0);
            }
        });

        // Configuration du layout vertical
        menuBox = new LinearLayout(getContext());
        menuBox.setOrientation(LinearLayout.VERTICAL);
        menuBox.setGravity(Gravity.CENTER);
        
        // Paramètres de layout pour l'espacement
        LayoutParams textParams = new LayoutParams(
            LayoutParams.WRAP_CONTENT, 
            LayoutParams.WRAP_CONTENT
        );
        textParams.setMargins(0, 0, 0, 40); // Espacement en bas du texte
        
        LayoutParams buttonParams = new LayoutParams(
            LayoutParams.WRAP_CONTENT, 
            LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 0, 0, 20); // Espacement entre les boutons

        // Ajout des vues au layout
        gameOverText.setLayoutParams(textParams);
        menuBox.addView(gameOverText);
        
        continueButton.setLayoutParams(buttonParams);
        menuBox.addView(continueButton);
        
        quitButton.setLayoutParams(buttonParams);
        menuBox.addView(quitButton);

        // Ajout du layout principal
        this.addView(menuBox);

        setState(0);

        System.out.println("GameMenuUI set");
    }
    
    @Override
    public void setState(int state) {
        System.out.println("GameMenuUI setState " + state);
        super.setState(state);
        
        continueButton.setText(state == 0 ? "Commencer" : "Continuer");

        // Modification du listener selon l'état
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (state == 0) {
                    gameScene_.restartGame();
                } else {
                    gameScene_.togglePause();
                }
            }
        });
    }
}