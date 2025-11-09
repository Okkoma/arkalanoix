package com.okkomastudio.arkalanoix.android.renderer.gdx;

import com.okkomastudio.arkalanoix.core.IRenderer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class RendererLibGDX implements IRenderer {
    
    private ShapeRenderer shapeRenderer_;
    private SpriteBatch spriteBatch_;
    private BitmapFont font_;
    private GlyphLayout glyphLayout_;
    private float textDefaultHeight_;
    private com.badlogic.gdx.graphics.Color fillColor_;
    private com.badlogic.gdx.graphics.Color strokeColor_;
    private boolean initialized_;
    
    public RendererLibGDX() {
        initialized_ = false;
        fillColor_ = new com.badlogic.gdx.graphics.Color();
        strokeColor_ = new com.badlogic.gdx.graphics.Color();
    }
    
    public void initialize() {
        if (!initialized_) {
            shapeRenderer_ = new ShapeRenderer();
            spriteBatch_ = new SpriteBatch();
            font_ = new BitmapFont(); // Use default font
            glyphLayout_ = new GlyphLayout();
            glyphLayout_.setText(font_, "T");
            textDefaultHeight_ = glyphLayout_.height;
            initialized_ = true;
        }
    }
    
    public void begin() {
        if (!initialized_ || shapeRenderer_ == null) return;
        shapeRenderer_.end();
        shapeRenderer_.begin(ShapeType.Filled);
    }
    
    public void end() {
        if (!initialized_ || shapeRenderer_ == null) return;
        shapeRenderer_.end();
    }
    
    public void beginText() {
        if (!initialized_ || spriteBatch_ == null) return;
        spriteBatch_.begin();
    }
    
    public void endText() {
        if (!initialized_ || spriteBatch_ == null) return;
        spriteBatch_.end();
    }
    
    @Override
    public void setFill(com.okkomastudio.arkalanoix.core.Color color) {
        fillColor_.set(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );
        if (initialized_ && shapeRenderer_ != null) {
            shapeRenderer_.setColor(fillColor_);
        }
        if (initialized_ && font_ != null) {
            font_.setColor(fillColor_);
        }
    }
    
    @Override
    public void setStroke(com.okkomastudio.arkalanoix.core.Color color) {
        strokeColor_.set(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );
    }
    
    @Override
    public void fillText(String text, double x, double y, String fontname, int fontsize, int halign, int valign) {
        if (!initialized_ || font_ == null || spriteBatch_ == null) return;
        // Set font size (scale based on default height)
        float scale = fontsize / textDefaultHeight_;
        font_.getData().setScale(scale, -scale);

        // Calculate text bounds
        glyphLayout_.setText(font_, text);
        float textWidth = glyphLayout_.width;
        float textHeight = glyphLayout_.height;

        float textX = (float) x;
        // LibGDX uses bottom-left origin for text, so we need to adjust
        float textY = (float) y;
        
        // Horizontal alignment
        switch (halign) {
            case 1: // CENTER
                textX -= textWidth / 2;
                break;
            case 2: // RIGHT
                textX -= textWidth;
                break;
            // case 0: LEFT is default
        }
        
        // Vertical alignment - LibGDX draws from bottom, so we adjust
        switch (valign) {
            case 1: // CENTER
                textY += textHeight / 2;
                break;
            case 2: // TOP
                textY += textHeight;
                break;
            case 3: // BOTTOM
                // textY is already at baseline
                break;
            // case 0: BASELINE is default
        }

        beginText();
        font_.draw(spriteBatch_, text, textX, textY);
        endText();
    }
    
    @Override
    public void fillRect(double x, double y, double w, double h) {
        if (!initialized_ || shapeRenderer_ == null) return;
        shapeRenderer_.rect((float) x, (float) y, (float) w, (float) h);
    }
    
    @Override
    public void fillRoundRect(double x, double y, double w, double h, double arcw, double arch) {
        if (!initialized_ || shapeRenderer_ == null) return;
        // LibGDX doesn't have direct rounded rect, so we approximate with multiple rectangles
        // For simplicity, we'll just draw a regular rectangle
        // In a full implementation, you could use a Polygon or TextureRegion
        shapeRenderer_.rect((float) x, (float) y, (float) w, (float) h);
    }
    
    @Override
    public void fillOval(double x, double y, double w, double h) {
        if (!initialized_ || shapeRenderer_ == null) return;
        float centerX = (float) (x + w / 2);
        float centerY = (float) (y + h / 2);
        float radiusX = (float) w / 2;
        float radiusY = (float) h / 2;
        shapeRenderer_.ellipse(centerX - radiusX, centerY - radiusY, (float) w, (float) h);
    }
    
    @Override
    public void strokeRect(double x, double y, double w, double h) {
        if (!initialized_ || shapeRenderer_ == null) return;
        // Save current state
        ShapeType currentType = shapeRenderer_.getCurrentType();
        com.badlogic.gdx.graphics.Color currentColor = shapeRenderer_.getColor();
        
        // Switch to line mode for stroke
        shapeRenderer_.end();
        shapeRenderer_.begin(ShapeType.Line);
        shapeRenderer_.setColor(strokeColor_);
        shapeRenderer_.rect((float) x, (float) y, (float) w, (float) h);
        shapeRenderer_.end();
        
        // Restore previous state
        shapeRenderer_.begin(currentType);
        shapeRenderer_.setColor(currentColor);
    }
    
    public ShapeRenderer getShapeRenderer() {
        if (!initialized_) return null;
        return shapeRenderer_;
    }
    
    public SpriteBatch getSpriteBatch() {
        if (!initialized_) return null;
        return spriteBatch_;
    }
    
    public boolean isInitialized() {
        return initialized_;
    }
    
    public void dispose() {
        if (shapeRenderer_ != null) {
            shapeRenderer_.dispose();
        }
        if (spriteBatch_ != null) {
            spriteBatch_.dispose();
        }
        if (font_ != null) {
            font_.dispose();
        }
    }
}

