package com.ignasm.spaceinvaders;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class PointTracker extends Text {
    private final int OFFSET = 10;
    private final String FONT_NAME = "Consolas";
    private int pointCount = 0;
    private Pane parent;

    public PointTracker(Pane parent) {
        this.parent = parent;
        setFont(new Font(FONT_NAME, 30));
        setFill(Color.WHITE);
        update();
    }

    public void update() {
        setText("Score: " + pointCount);
        setLayoutY(getLayoutBounds().getHeight() + OFFSET);
        setLayoutX(parent.getWidth() - getLayoutBounds().getWidth() - OFFSET);
    }

    public void addPoint() {
        addPoints(1);
    }

    public void addPoints(int points) {
        pointCount += points;
    }

    public int getPoints() {
        return pointCount;
    }

    public void setPoints(int points) {
        pointCount = points;
    }
}
