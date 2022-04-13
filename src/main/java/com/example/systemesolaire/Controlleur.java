package com.example.systemesolaire;

import javafx.animation.TranslateTransition;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controlleur {

    private Stage stage;
    private SubScene subScene;
    private Scene scene;
    private Camera camera;
    private Vaisseau vaisseau;
    private static Translate pivot;
    private static Translate zoom;


    public Controlleur(Stage stage, Scene scene, Camera camera) {
        this.stage = stage;
        this.scene = scene;
        this.camera = camera;
        mouseControl();
    }

    public Controlleur(Vaisseau vaisseau, SubScene scene) {
        this.vaisseau = vaisseau;
        this.subScene = scene;
        controleVaisseau();
    }


    private void mouseControl() {
        pivot = new Translate(0, 0, 0);
        zoom = new Translate(0, 0, -3000);
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

        camera.getTransforms().addAll(
                pivot,
                rotateY,
                rotateX,
                zoom
        );

        Vecteur2 basePos = new Vecteur2();
        Vecteur2 basePivot = new Vecteur2(pivot.getX(), pivot.getY());
        Vecteur2 baseRotate = new Vecteur2();
        scene.setOnMousePressed((mouseEvent -> {
            basePos.setX(mouseEvent.getSceneX());
            basePos.setY(mouseEvent.getSceneY());

            if (mouseEvent.isPrimaryButtonDown())
            {
                basePivot.setX(zoom.getX());
                basePivot.setY(zoom.getY());
            }
            if (mouseEvent.isSecondaryButtonDown())
            {
                baseRotate.setX(rotateY.angleProperty().get());
                baseRotate.setY(rotateX.angleProperty().get());
            }
        }));
        scene.setOnMouseDragged((mouseEvent ->
        {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (pivot.xProperty().isBound())
                {
                    pivot.xProperty().unbind();
                    pivot.xProperty().set(0);
                }
                if (pivot.yProperty().isBound()) {
                    pivot.yProperty().unbind();
                    pivot.yProperty().set(0);
                }

                double moveX = basePivot.getX() + (basePos.getX() - mouseEvent.getSceneX());
                double moveY = basePivot.getY() + (basePos.getY() - mouseEvent.getSceneY());

                zoom.setX(moveX);
                zoom.setY(moveY);
            }
            if (mouseEvent.isSecondaryButtonDown())
            {
                double rotateByX = baseRotate.getX() + (basePos.getX() - mouseEvent.getSceneX());
                double rotateByY = baseRotate.getY() + (basePos.getY() - mouseEvent.getSceneY());
                rotateY.angleProperty().set(rotateByX);
                rotateX.angleProperty().set(rotateByY);
            }
        }));

        stage.addEventHandler(ScrollEvent.SCROLL, mouseEvent -> zoom.setZ(zoom.getZ() + mouseEvent.getDeltaY()*3));
    }

    private void controleVaisseau() {
        subScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case UP -> up();
                case DOWN -> dowm();
                case RIGHT -> right();
                case LEFT -> left();
            }
        });
    }

    private void up() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromY(vaisseau.getTranslateY());
        translateTransitionRight.setToY(vaisseau.getTranslateY()-20);
        translateTransitionRight.play();
    }
    private void dowm() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromY(vaisseau.getTranslateY());
        translateTransitionRight.setToY(vaisseau.getTranslateY()+20);
        translateTransitionRight.play();
    }
    private void right() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromX(vaisseau.getTranslateX());
        translateTransitionRight.setToX(vaisseau.getTranslateY()+20);
        translateTransitionRight.play();
    }
    private void left() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromX(vaisseau.getTranslateX());
        translateTransitionRight.setToX(vaisseau.getTranslateX()-20);
        translateTransitionRight.play();
    }


    public static Translate getPivot() {
        return pivot;
    }

    public static Translate getZoom() {
        return zoom;
    }
}
