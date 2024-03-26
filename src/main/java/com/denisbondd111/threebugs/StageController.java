package com.denisbondd111.threebugs;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class StageController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private TextField legLengthTextField;
    @FXML
    private TextField speedFirstTextField;
    @FXML
    private TextField speedSecondTextField;
    @FXML
    private TextField speedThirdTextField;
    @FXML
    private Label crossPointLabel;
    @FXML
    private Label firstBugCoordinatesLabel;
    @FXML
    private Label secondBugCoordinatesLabel;
    @FXML
    private Label thirdBugCoordinatesLabel;
    @FXML
    private VBox controlPanel;
    @FXML
    private Pane viewportPane;
    @FXML
    private Label travelTimeLabel;

    private Triangle triangle;

    private Bug bug1;
    private Bug bug2;
    private Bug bug3;

    private Label warning;
    private BugTranslateTimer timerBug1;
    private BugTranslateTimer timerBug2;
    private BugTranslateTimer timerBug3;
    private AnimationTimer updateCoordinatesAndCrossTimer;
    private LocalTime time;
    private boolean isWorking = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warning = new Label("");
        warning.setTextFill(Color.RED);
        controlPanel.getChildren().add(warning);

        startButton.setOnMouseClicked(mouseEvent -> {
            if(!isWorking) {
                if (
                        !legLengthTextField.getText().trim().isEmpty() && !speedFirstTextField.getText().trim().isEmpty() && !speedThirdTextField.getText().trim().isEmpty() &&
                        Double.valueOf(legLengthTextField.getText().trim()) > 0.0 && Double.valueOf(legLengthTextField.getText().trim()) <= 500.0 &&
                        Double.valueOf(speedFirstTextField.getText().trim()) > 0.0 && Double.valueOf(speedFirstTextField.getText().trim()) <= 1000.0 &&
                        Double.valueOf(speedSecondTextField.getText().trim()) > 0.0 && Double.valueOf(speedSecondTextField.getText().trim()) <= 1000.0 &&
                        Double.valueOf(speedThirdTextField.getText().trim()) > 0.0 && Double.valueOf(speedThirdTextField.getText().trim()) <= 1000.0
                ) {
                    warning.setText("");

                    double timeOfBegin = System.currentTimeMillis();

                    Double legLength = Double.valueOf(legLengthTextField.getText().trim());
                    Double speedFirstBug = Double.valueOf(speedFirstTextField.getText().trim());
                    Double speedSecondBug = Double.valueOf(speedSecondTextField.getText().trim());
                    Double speedThirdBug = Double.valueOf(speedThirdTextField.getText().trim());

                    triangle = new Triangle(legLength, new Point2D(370, 280));
                    viewportPane.getChildren().add(triangle);

                    bug1 = new Bug(new Point2D(triangle.getPoints().get(4), triangle.getPoints().get(5)), speedFirstBug, speedFirstBug * 0.03);
                    bug1.setTarget(new Point2D(triangle.getPoints().get(0), triangle.getPoints().get(1)));
                    viewportPane.getChildren().addAll(bug1, bug1.getCollisionRectangle());


                    bug2 = new Bug(new Point2D(triangle.getPoints().get(0), triangle.getPoints().get(1)), speedSecondBug, speedSecondBug * 0.03);
                    bug2.setTarget(new Point2D(triangle.getPoints().get(2), triangle.getPoints().get(3)));
                    viewportPane.getChildren().addAll(bug2, bug2.getCollisionRectangle());

                    bug3 = new Bug(new Point2D(triangle.getPoints().get(2), triangle.getPoints().get(3)), speedThirdBug, speedThirdBug * 0.03);
                    bug3.setTarget(new Point2D(triangle.getPoints().get(4), triangle.getPoints().get(5)));
                    viewportPane.getChildren().addAll(bug3, bug3.getCollisionRectangle());

                    timerBug1 = new BugTranslateTimer(bug1, 0, 1, triangle.getPoints());
                    timerBug2 = new BugTranslateTimer(bug2, 2, 3, triangle.getPoints());
                    timerBug3 = new BugTranslateTimer(bug3, 4, 5, triangle.getPoints());
                    updateCoordinatesAndCrossTimer = new AnimationTimer() {
                        @Override
                        public void handle(long l) {
                            firstBugCoordinatesLabel.setText("(" + (int) bug1.getCenterX() + ":" + (int) bug1.getCenterY() + ")");
                            secondBugCoordinatesLabel.setText("(" + (int) bug2.getCenterX() + ":" + (int) bug2.getCenterY() + ")");
                            thirdBugCoordinatesLabel.setText("(" + (int) bug3.getCenterX() + ":" + (int) bug3.getCenterY() + ")");
                            if (
                                    Utils.checkBounds(bug1.getCollisionRectangle(), bug2.getCollisionRectangle()) && Utils.checkBounds(bug1.getCollisionRectangle(), bug3.getCollisionRectangle())
                            ) {
                                crossPointLabel.setText("(" + (int) ((bug1.getCenterX()+bug2.getCenterX()+bug3.getCenterX())/3) + ":" + (int)  ((bug1.getCenterX()+bug2.getCenterX()+bug3.getCenterY())/3) + ")");
                                timerBug1.stop();
                                timerBug2.stop();
                                timerBug3.stop();
                                travelTimeLabel.setText(String.valueOf((System.currentTimeMillis()-timeOfBegin)*0.001));
                                this.stop();
                            }
                        }
                    };

                    timerBug1.start();
                    timerBug2.start();
                    timerBug3.start();
                    updateCoordinatesAndCrossTimer.start();

                    isWorking = true;
                }else {
                    warning.setText("Incorrect data");
                }
            }
        });

        stopButton.setOnMouseClicked(mouseEvent -> {
            if (isWorking){
                timerBug1.stop();
                timerBug2.stop();
                timerBug3.stop();
                updateCoordinatesAndCrossTimer.stop();

                viewportPane.getChildren().remove(triangle);
                viewportPane.getChildren().remove(bug1);
                viewportPane.getChildren().remove(bug2);
                viewportPane.getChildren().remove(bug3);

                crossPointLabel.setText("");
                firstBugCoordinatesLabel.setText("");
                secondBugCoordinatesLabel.setText("");
                thirdBugCoordinatesLabel.setText("");
                travelTimeLabel.setText("");

                triangle = null;
                bug1 = null;
                bug2 = null;
                bug3 = null;
                isWorking = false;
            }
        });
    }


}