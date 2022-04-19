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
    //private SubScene subScene;
    private Scene scene;
    private Camera camera;
    private Vaisseau vaisseau;
    private static Translate pivot;
    private static Translate zoom;


    public Controlleur(Stage stage, Scene scene, Camera camera, Vaisseau vaisseau) {
        this.stage = stage;
        this.scene = scene;
        this.camera = camera;
        this.vaisseau = vaisseau;
        mouseControl();
    }

    public Controlleur(Vaisseau vaisseau, Scene scene) {
        this.vaisseau = vaisseau;
        this.scene = scene;
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
        pivot.xProperty().bind(vaisseau.translateXProperty());
        pivot.yProperty().bind(vaisseau.translateYProperty());

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
        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case W-> up();
                case S -> dowm();
                case D -> right();
                case A -> left();
                case E -> zPlus();
                case Q -> zMoins();
            }
        });
    }

    private void up() {
        vaisseau.setTranslateX(vaisseau.getTranslateX()-5);
        System.out.println("up");
        /*
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromY(vaisseau.getTranslateY());
        translateTransitionRight.setToY(vaisseau.getTranslateY()-5);
        translateTransitionRight.play();
        System.out.println("up");*/
    }
    private void dowm() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromY(vaisseau.getTranslateY());
        translateTransitionRight.setToY(vaisseau.getTranslateY()+5);
        translateTransitionRight.play();
        System.out.println("down");
    }
    private void right() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromX(vaisseau.getTranslateX());
        translateTransitionRight.setToX(vaisseau.getTranslateY()+5);
        translateTransitionRight.play();
        System.out.println("right");
    }
    private void left() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromX(vaisseau.getTranslateX());
        translateTransitionRight.setToX(vaisseau.getTranslateX()-5);
        translateTransitionRight.play();
        System.out.println("left");
    }
    private void zPlus() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromZ(vaisseau.getTranslateX());
        translateTransitionRight.setToZ(vaisseau.getTranslateX()+5);
        translateTransitionRight.play();
        System.out.println("zPlus");
    }
    private void zMoins() {
        TranslateTransition translateTransitionRight = new TranslateTransition();
        translateTransitionRight.setDuration(Duration.millis(200));
        translateTransitionRight.setNode(vaisseau);
        translateTransitionRight.setFromZ(vaisseau.getTranslateX());
        translateTransitionRight.setToZ(vaisseau.getTranslateX()-5);
        translateTransitionRight.play();
        System.out.println("zMoins");
    }


    public static Translate getPivot() {
        return pivot;
    }

    public static Translate getZoom() {
        return zoom;
    }
}
