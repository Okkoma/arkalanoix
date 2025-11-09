package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IRenderer;
import com.okkomastudio.arkalanoix.core.Color;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class RendererAndroid implements IRenderer {
    
    private Canvas canvas_;
    private Paint fillPaint_;
    private Paint strokePaint_;
    private Paint textPaint_;

    public RendererAndroid(Canvas canvas) {
        setCanvas(canvas);
    }
    
    public void setCanvas(Canvas canvas) {
        canvas_ = canvas;
        
        // Initialisation des paints (seulement si pas déjà fait)
        if (fillPaint_ == null) {
            fillPaint_ = new Paint();
            fillPaint_.setStyle(Style.FILL);
            
            strokePaint_ = new Paint();
            strokePaint_.setStyle(Style.STROKE);
            
            textPaint_ = new Paint();
            textPaint_.setStyle(Style.FILL);
            textPaint_.setAntiAlias(true);
        }
    }

    @Override
    public void setFill(Color color) {
        fillPaint_.setColor(android.graphics.Color.argb(
            color.getAlpha(), 
            color.getRed(), 
            color.getGreen(), 
            color.getBlue()
        ));
        textPaint_.setColor(fillPaint_.getColor());
    }

    @Override
    public void fillText(String text, double x, double y, String fontname, int fontsize, int halign, int valign) {
        // Configuration de l'alignement horizontal
        switch (halign) {
            case 0: // LEFT
                textPaint_.setTextAlign(Align.LEFT);
                break;
            case 1: // CENTER
                textPaint_.setTextAlign(Align.CENTER);
                break;
            case 2: // RIGHT
                textPaint_.setTextAlign(Align.RIGHT);
                break;
        }
        
        // Configuration de la police
        textPaint_.setTextSize(fontsize);
        
        // Pour l'alignement vertical
        Paint.FontMetrics fm = textPaint_.getFontMetrics();
        float textY;
        switch (valign) {
            case 0: // BASELINE
                textY = (float) y;
                break;
            case 1: // CENTER
                textY = (float) y - (fm.ascent + fm.descent) / 2;
                break;
            case 2: // TOP
                textY = (float) y - fm.ascent;
                break;
            case 3: // BOTTOM
                textY = (float) y - fm.descent;
                break;
            default:
                textY = (float) y;
                break;
        }
        
        canvas_.drawText(text, (float) x, textY, textPaint_);
    }
    
    @Override
    public void fillRect(double x, double y, double w, double h) {
        canvas_.drawRect((float) x, (float) y, (float) (x + w), (float) (y + h), fillPaint_);
    }

    @Override
    public void fillRoundRect(double x, double y, double w, double h, double arcw, double arch) {
        RectF rect = new RectF((float) x, (float) y, (float) (x + w), (float) (y + h));
        canvas_.drawRoundRect(rect, (float) arcw, (float) arch, fillPaint_);
    }

    @Override
    public void fillOval(double x, double y, double w, double h) {
        RectF oval = new RectF((float) x, (float) y, (float) (x + w), (float) (y + h));
        canvas_.drawOval(oval, fillPaint_);
    }

    @Override
    public void setStroke(Color color) {
        strokePaint_.setColor(android.graphics.Color.argb(
            color.getAlpha(), 
            color.getRed(), 
            color.getGreen(), 
            color.getBlue()
        ));
    }   

    @Override
    public void strokeRect(double x, double y, double w, double h) {
        canvas_.drawRect((float) x, (float) y, (float) (x + w), (float) (y + h), strokePaint_);
    }
}
