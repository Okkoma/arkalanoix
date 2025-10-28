package com.okkomastudio.arkalanoix.desktop;

import com.okkomastudio.arkalanoix.core.IRenderer;

import java.awt.Color;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class RendererJavaFX implements IRenderer {
    
    private GraphicsContext gc_;

    public RendererJavaFX(GraphicsContext gc) {
        gc_ = gc;
    }

    @Override
    public void setFill(Color color) {
        gc_.setFill(new javafx.scene.paint.Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0));
    }

    @Override
    public void fillText(String text, double x, double y, String fontname, int fontsize, int halign, int valign) {
    	gc_.setTextAlign(TextAlignment.values()[halign]);
        gc_.setTextBaseline(VPos.values()[valign]);
    	gc_.setFont(Font.font(fontname, fontsize));
    	gc_.fillText(text, x, y);
    }
    
    @Override
    public void fillRect(double x, double y, double w, double h) {
        gc_.fillRect(x, y, w, h);
    }

    @Override
    public void fillRoundRect(double x, double y, double w, double h, double arcw, double arch) {
        gc_.fillRoundRect(x, y, w, h, 20.0, 5.0);
    }

    @Override
    public void fillOval(double x, double y, double w, double h) {
        gc_.fillOval(x, y, w, h);
    }

    @Override
    public void setStroke(Color color) {
        gc_.setStroke(new javafx.scene.paint.Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0));
    }   

    @Override
    public void strokeRect(double x, double y, double w, double h) {
        gc_.strokeRect(x, y, w, h);
    }
}
