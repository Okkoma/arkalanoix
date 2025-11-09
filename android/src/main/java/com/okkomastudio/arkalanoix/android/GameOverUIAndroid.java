package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IControllableScene;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameOverUIAndroid extends UIPanelAndroid {
	
    private LinearLayout menuBox;
    private TextView gameOverText;
    private Button replayButton;
    private Button quitButton;
    
    public GameOverUIAndroid(Context context, IControllableScene gameScene) {
        super(context, "GameOverUI", gameScene);
    }

    @Override
    public void set() {
        // Création du TextView pour le titre "GAME OVER"
        gameOverText = new TextView(getContext());
        gameOverText.setText("GAME OVER");
        gameOverText.setTextColor(Color.WHITE);
        gameOverText.setTextSize(48);
        gameOverText.setTypeface(null, android.graphics.Typeface.BOLD);
        gameOverText.setGravity(Gravity.CENTER);

        // Création du bouton Rejouer
        replayButton = new Button(getContext());
        replayButton.setText("Rejouer");
        replayButton.setTextSize(20);
        replayButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        replayButton.setTextColor(Color.WHITE);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Relancer le jeu
                hide();
                gameScene_.restartGame();
            }
        });

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
        
        replayButton.setLayoutParams(buttonParams);
        menuBox.addView(replayButton);
        
        quitButton.setLayoutParams(buttonParams);
        menuBox.addView(quitButton);

        // Ajout du layout principal
        this.addView(menuBox);

        System.out.println("GameOverUI set");
    }
}
