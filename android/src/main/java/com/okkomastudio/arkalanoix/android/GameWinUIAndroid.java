package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IControllableScene;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameWinUIAndroid extends UIPanelAndroid {
    
    private LinearLayout menuBox;
    private TextView text;
    private Button replayButton;

    public GameWinUIAndroid(Context context, IControllableScene gameScene) {
        super(context, "GameWinUI", gameScene);
    }

    @Override
    public void set() {
        // Création du TextView pour le titre
        text = new TextView(getContext());
        text.setText("NEXT LEVEL");
        text.setTextColor(Color.WHITE);
        text.setTextSize(48);
        text.setTypeface(null, android.graphics.Typeface.BOLD);
        text.setGravity(Gravity.CENTER);

        // Création du bouton Continuer
        replayButton = new Button(getContext());
        replayButton.setText("Continuer");
        replayButton.setTextSize(20);
        replayButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        replayButton.setTextColor(Color.WHITE);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer au niveau suivant
                hide();
                gameScene_.nextLevel();
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
        buttonParams.setMargins(0, 0, 0, 20); // Espacement pour les boutons

        // Ajout des vues au layout
        text.setLayoutParams(textParams);
        menuBox.addView(text);
        
        replayButton.setLayoutParams(buttonParams);
        menuBox.addView(replayButton);

        // Ajout du layout principal
        this.addView(menuBox);

        System.out.println("GameWinUI set");
    }
}
