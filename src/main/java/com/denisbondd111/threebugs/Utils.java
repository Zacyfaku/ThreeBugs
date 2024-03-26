package com.denisbondd111.threebugs;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Utils {

    public static Circle createBug(Point2D center){
        Circle bug = new Circle();
        bug.setCenterX(center.getX());
        bug.setCenterY(center.getY());
        bug.setRadius(10);
        bug.setFill(Color.BLACK);
        return bug;
    }

    public static AnimationTimer createCollisionTimer(
            Circle first,
            Circle second,
            Circle third,
            Timeline timeline,
            Label crossPointLabel,
            Label firstBugCoordinatesLabel,
            Label secondBugCoordinatesLabel,
            Label thirdBugCoordinatesLabel
    ){
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                firstBugCoordinatesLabel.setText("( " + (int) first.getCenterX() + " : " + (int) first.getCenterY() + " )");
                secondBugCoordinatesLabel.setText("( " + (int) second.getCenterX() + " : " + (int) second.getCenterY() + " )");
                thirdBugCoordinatesLabel.setText("( " + (int) third.getCenterX() + " : " + (int) third.getCenterY() + " )");
//                if(checkCollision(first,second,third)){
//                    timeline.stop();
//                    crossPointLabel.setText("( " + first.getCenterX() + " : " + first.getCenterY() + " )");
//                }
            }
        };
    }

    

    public static Timeline createTimeline(
            Polygon triangle,
            Circle bug1,
            Circle bug2,
            Circle bug3,
            Double speedBug1,
            Double speedBug2,
            Double speedBug3,
            Double legLength
    ){
        KeyValue keyValueBug1X = new KeyValue(bug1.centerXProperty(), triangle.getPoints().get(2));
        KeyValue keyValueBug1Y = new KeyValue(bug1.centerYProperty(), triangle.getPoints().get(3));
        KeyValue keyValueBug2X = new KeyValue(bug2.centerXProperty(), triangle.getPoints().get(4));
        KeyValue keyValueBug2Y = new KeyValue(bug2.centerYProperty(), triangle.getPoints().get(5));
        KeyValue keyValueBug3X = new KeyValue(bug3.centerXProperty(), triangle.getPoints().get(0));
        KeyValue keyValueBug3Y = new KeyValue(bug3.centerYProperty(), triangle.getPoints().get(1));

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(5),keyValueBug1X,keyValueBug1Y,keyValueBug2X,keyValueBug2Y,keyValueBug3X,keyValueBug3Y);
        return new Timeline(keyFrame);


    }

    public static boolean checkBounds(Shape first, Shape second){
        return first.getBoundsInLocal().intersects(second.getBoundsInLocal());
    }

    public static boolean collision(Point2D first, Point2D second, double eps){
        return first.distance(second) <= eps;
    }

}
