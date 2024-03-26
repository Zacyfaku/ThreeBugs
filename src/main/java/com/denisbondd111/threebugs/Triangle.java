package com.denisbondd111.threebugs;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;

public class Triangle extends Polygon {
    private Double legLength;
    private Point2D center;

    public Triangle() {
    }

    public Triangle(Double legLength, Point2D center) {
        this.legLength = legLength;
        this.center = center;
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(2.0);
        this.setFill(Color.web("#1e1f22"));
        this.setTranslateZ(-100.0);
        applyChanges();
    }

    public Double getLegLength() {
        return legLength;
    }

    public void setLegLength(Double legLength) {
        this.legLength = legLength;
        applyChanges();
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
        applyChanges();
    }

    private void applyChanges(){
        this.getPoints().addAll(
                new Double[]{
                        this.center.getX() - (this.legLength / 2.0),
                        this.center.getY() + (this.legLength / 2.0),
                        this.center.getX() - (this.legLength / 2.0) + this.legLength,
                        this.center.getY() + (this.legLength / 2.0),
                        this.center.getX() - (this.legLength / 2.0),
                        this.center.getY() + (this.legLength / 2.0) - this.legLength
                }
        );
    }
}
