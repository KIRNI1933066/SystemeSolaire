package com.example.systemesolaire.controllers;

import com.example.systemesolaire.corpscelestes.Vaisseau;
import com.example.systemesolaire.utilitaires.Vecteur3;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Controlleur {

    private final Stage STAGE;
    private final Scene SCENE;
    private final Camera CAMERA;
    private final Vaisseau VAISSEAU;
    private static Translate pivot;
    private static Translate zoom;


    public Controlleur(Stage stage, Scene scene, Camera camera, Vaisseau vaisseau) {
        this.STAGE = stage;
        this.SCENE = scene;
        this.CAMERA = camera;
        this.VAISSEAU = vaisseau;
        mouseControl();
    }

    private void mouseControl() {
        pivot = new Translate(0, 0, 0);
        zoom = new Translate(0, 0, -3000);
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

        CAMERA.getTransforms().addAll(
                pivot,
                rotateY,
                rotateX,
                zoom
        );

        Vecteur3 basePos = new Vecteur3();
        Vecteur3 basePivot = new Vecteur3(pivot.getX(), pivot.getY(), pivot.getZ());
        Vecteur3 baseRotate = new Vecteur3();

        SCENE.setOnMousePressed((mouseEvent -> {
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
        SCENE.setOnMouseDragged((mouseEvent ->
        {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (mouseEvent.isStillSincePress())
                {
                    return;
                }

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

        int maxZoom = -100;
        int minZoom = -100000;

        STAGE.addEventHandler(ScrollEvent.SCROLL, mouseEvent -> zoom.setZ(Math.max(minZoom, Math.min(maxZoom, zoom.getZ() + mouseEvent.getDeltaY()*3))));
    }


    public static Translate getPivot() {
        return pivot;
    }

    public static Translate getZoom() {
        return zoom;
    }
}
