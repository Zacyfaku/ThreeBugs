package com.denisbondd111.threebugs;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class BugTranslateTimer extends AnimationTimer{
    private long prevTime = (long) 0.0;
    private Bug bug;
    private int targetIndexX;
    private int targetIndexY;
    private ObservableList<Double> trianglePoints;
    public BugTranslateTimer(Bug bug, int targetIndexX, int targetIndexY, ObservableList<Double> trianglePoints) {
        this.bug = bug;
        this.targetIndexX = targetIndexX;
        this.targetIndexY = targetIndexY;
        this.trianglePoints = trianglePoints;
    }

    @Override
    public void handle(long l) {
        if(prevTime == 0.0){
            prevTime = l;
        }else{
            double deltaTime = (l-prevTime)*0.000000001;
            prevTime = l;
            double deltaMovingX = bug.getVelocityNormalize().getX() * bug.getVelocityAbs() * deltaTime;
            double deltaMovingY = bug.getVelocityNormalize().getY() * bug.getVelocityAbs() * deltaTime;

            if(!Utils.collision(new Point2D(bug.getCenterX(), bug.getCenterY()), bug.getTarget(), ((Math.abs(deltaMovingX) + Math.abs(deltaMovingY))/2.0)+5.0)) {
                bug.setCenterX(bug.getCenterX() + deltaMovingX);
                bug.setCenterY(bug.getCenterY() + deltaMovingY);

                bug.getCollisionRectangle().setX((bug.getCenterX()+deltaMovingX)-bug.getRadius()/2.0);
                bug.getCollisionRectangle().setY((bug.getCenterY()+deltaMovingY)-bug.getRadius()/2.0);
            }else{
                if(targetIndexX == 4 && targetIndexY == 5){
                    targetIndexX = 0;
                    targetIndexY = 1;
                    bug.setTarget(new Point2D(trianglePoints.get(targetIndexX), trianglePoints.get(targetIndexY)));
                }else{
                    targetIndexX += 2;
                    targetIndexY += 2;
                    bug.setTarget(new Point2D(trianglePoints.get(targetIndexX), trianglePoints.get(targetIndexY)));
                }
            }

        }
    }

}
