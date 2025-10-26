package com.okkoma.arkalanoix;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Bonus extends GameObject {
	
	public static class BonusType {
		final static int Max = 11; 
		final static String Names[] = { 
				"reduit la raquette", "agrandit la raquette", "ajoute une vie",
				"colle à la raquette", "decolle de la raquette", 
				"reduit la vitesse de la raquette", "augmente la vitesse de la raquette",
				"reduit la vitesse de la balle", "augmente la vitesse de la balle",
				"balle d'acier", "multi-balle"
		};
		final static Color Colors[] = {
				Color.DARKBLUE, Color.CYAN, Color.GOLD, Color.LIGHTGREEN,
				Color.DARKGREEN, Color.MEDIUMORCHID, Color.MAGENTA, Color.BLUE,
				Color.YELLOW, Color.CRIMSON, Color.DEEPPINK
		};
	};
	
    private int type_; // Type de bonus (ex: 1 = agrandit la raquette, 2 = vie supplémentaire, etc.)
    private double speed_ = 2.0; // Vitesse de chute du bonus
    
    public Bonus(double x, double y, double width, double height, int type) {
        super(x, y, width, height, Bonus.BonusType.Colors[type]);
        type_ = type;        
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!isDestroyed_) {
        	final int fontSize = 16;
            gc.setFill(color_);
            gc.fillOval(rect_.getMinX(), rect_.getMinY(), rect_.getWidth(), rect_.getHeight());
        	gc.setTextAlign(TextAlignment.CENTER);
        	gc.setTextBaseline(VPos.CENTER);
        	gc.setFont(Font.font(fontSize));
        	gc.setFill(Color.WHITE);
        	gc.fillText(Integer.toString(type_), rect_.getMinX() + rect_.getWidth() / 2, rect_.getMinY() + rect_.getHeight() / 2);            
        }
    }

    // Méthode pour faire tomber le bonus
    public void fall() {
        rect_ = new javafx.geometry.Rectangle2D(
            rect_.getMinX(),
            rect_.getMinY() + speed_,
            rect_.getWidth(),
            rect_.getHeight()
        );
    }

    // Getter pour le type de bonus
    public int getType() {
        return type_;
    }
}