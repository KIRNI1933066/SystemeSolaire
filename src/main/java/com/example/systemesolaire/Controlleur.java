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

        Vecteur3 basePos = new Vecteur3();
        Vecteur3 basePivot = new Vecteur3(pivot.getX(), pivot.getY(), pivot.getZ());
        Vecteur3 baseRotate = new Vecteur3();
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


    public static Translate getPivot() {
        return pivot;
    }

    public static Translate getZoom() {
        return zoom;
    }
}
