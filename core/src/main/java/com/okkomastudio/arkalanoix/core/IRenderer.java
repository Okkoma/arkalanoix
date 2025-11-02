package com.okkomastudio.arkalanoix.core;

public interface IRenderer {
    void setFill(Color color);
    void fillText(String text, double x, double y, String fontname, int fontsize, int halign, int valign);
    void fillRect(double x, double y, double w, double h);
    void fillRoundRect(double x, double y, double w, double h, double arcw, double arch);
    void fillOval(double x, double y, double w, double h);
    void setStroke(Color color);
    void strokeRect(double x, double y, double w, double h);
}