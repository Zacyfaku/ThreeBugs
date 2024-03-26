package com.denisbondd111.threebugs;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Bug extends Circle {
    private Point2D velocityNormalize = new Point2D(0.0, 0.0);
    private Double velocityAbs;
    private Double angle;
    private Point2D target;
    private Rectangle collisionRectangle;

    public Bug(Point2D center, Double velocityAbs, Double collisionEpsilon) {
        this.velocityAbs = velocityAbs;
        this.setRadius(20);
        this.setCenterX(center.getX());
        this.setCenterY(center.getY());
        ImagePattern imagePattern = new ImagePattern(new Image(String.valueOf(Bug.class.getResource("Bug.png")), 20, 20, true, true));
        this.setFill(imagePattern);
        this.collisionRectangle = new Rectangle(center.getX()-this.getRadius()/2.0, center.getY()-this.getRadius()/2.0, collisionEpsilon, collisionEpsilon);
        this.collisionRectangle.setVisible(false);
    }

    public void setTarget(Point2D target){
        this.target = target;
        applyChanges();
    }

    public Point2D getTarget() {
        return target;
    }

    public Double getVelocityAbs() {
        return velocityAbs;
    }

    public Point2D getVelocityNormalize() {
        return velocityNormalize;
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    private void updateCollisionRectangle(Point2D coordinates){
        this.collisionRectangle.setX(coordinates.getX());
        this.collisionRectangle.setY(coordinates.getY());
    }

    private void applyChanges(){
        this.velocityNormalize = target.subtract(new Point2D(this.getCenterX(), this.getCenterY())).normalize();
        this.angle = velocityNormalize.angle(new Point2D(1.0, 1000.0));
        this.setRotate(angle);
        if(angle < 91.0 && angle > 89.0){
            this.setRotate(-angle);
        }
    }



//    public ColisionRectangle getCollisionRectangle(){
//        return
//    }
//
//    private class ColisionRectangle extends Rectangle{
//
//        public ColisionRectangle(Point2D coordinates, Double collisionEpsilon){
//            super(coordinates.getX(), coordinates.getY(), collisionEpsilon, collisionEpsilon);
//        }
//
//        public void updateCoordinates(Point2D coordinates){
//            this.setX(coordinates.getX());
//            this.setY(coordinates.getY());
//        }
//    }
}
