package com.example.systemesolaire;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.example.systemesolaire.Main.*;

public class Planete extends Sphere {

    private final com.example.systemesolaire.Vecteur2 mousepos = new com.example.systemesolaire.Vecteur2();
    private com.example.systemesolaire.Vecteur2 position, speed;
    public String name;
    private Orbit orbit;
    private double periapsis, apoapsis;
    private com.example.systemesolaire.PolyLine3D orbitPath;
    private double radius;
    private Color couleur;
    private InfoPlanete infoPlanete;
    public double masse;

    private boolean drawPath = true;

    public Planete (double radius, Color color, double periapsis, double apoapsis, String name, String texture, int i, double masse) throws FileNotFoundException {
        super(radius);
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(new Image(new FileInputStream(texture)));
        super.setMaterial(mat);
        position = new com.example.systemesolaire.Vecteur2(0, 0);
        super.setTranslateX(position.getX());
        super.setTranslateY(position.getY());
        super.translateXProperty().bind(position.XProperty());
        super.translateYProperty().bind(position.YProperty());
        speed = new com.example.systemesolaire.Vecteur2();
        super.getTransforms().addAll(new Rotate(90,Rotate.X_AXIS));


        Constantes.InfoPlanetes[] infoPlanetes = Constantes.InfoPlanetes.values();
        Rotate rotationX;
        Rotate rotationY;
        super.getTransforms().addAll(
                rotationX = new Rotate(0, Rotate.X_AXIS),
                rotationY = new Rotate(0, Rotate.Y_AXIS)
        );
        rotationX.angleProperty().bind(new SimpleDoubleProperty(infoPlanetes[i].inclination));
        rotationY.angleProperty().bind(new SimpleDoubleProperty(infoPlanetes[i].inclination));

        this.name = name;
        this.apoapsis = apoapsis;
        this.periapsis = periapsis;
        this.radius = radius;
        this.couleur = color;
        this.masse = masse;
        orbit = new Orbit(5000);

        infoPlanete = new InfoPlanete(this);
        BorderPane bp = (BorderPane)infoPlanete.getChildren().get(0);
        infoPlanete.setVisible(false);
        principal.getChildren().add(infoPlanete);
        Platform.runLater(() -> {
            principal.getChildren().remove(infoPlanete);
        });
        position.XProperty().addListener((observable, oldVal, newVal) -> {
            infoPlanete.setDistanceSoleil(Vecteur2.distanceTo(position, POS_SOLEIL));
        });
        super.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (com.example.systemesolaire.Main.pivot.xProperty().isBound())
                {
                    com.example.systemesolaire.Main.pivot.xProperty().unbind();
                }
                if (com.example.systemesolaire.Main.pivot.yProperty().isBound())
                {
                    com.example.systemesolaire.Main.pivot.yProperty().unbind();
                }
                com.example.systemesolaire.Main.pivot.xProperty().bind(this.translateXProperty());
                com.example.systemesolaire.Main.pivot.yProperty().bind(this.translateYProperty());
                zoom.setX(0);
                zoom.setY(0);
            }
        });
        super.setOnMouseEntered(mouseEvent -> {
            if (!principal.getChildren().contains(infoPlanete))
            {
                infoPlanete.setVisible(false);
                principal.getChildren().add(infoPlanete);
            }
            Platform.runLater(() -> {
                infoPlanete.setTranslateX(principal.getScene().getWidth() - bp.getWidth() - 20);
                infoPlanete.setTranslateY(principal.getScene().getHeight()/2 - bp.getHeight()/2);
                infoPlanete.setVisible(true);
            });
        });
        super.setOnMouseExited(mouseEvent -> {
            principal.getChildren().remove(infoPlanete);
        });
    }

    public void updateOrbitPath(com.example.systemesolaire.Vecteur2 sunPosition, int indexPlanete) {
        if (orbitPath != null) {
            com.example.systemesolaire.Main.systeme.getChildren().remove(orbitPath);
        }
        Path orbitRealPath = orbit.getPathOrbit(sunPosition, periapsis/ ECHELLE, apoapsis/ ECHELLE);


        List<com.example.systemesolaire.Point3D> listPoints3D = new ArrayList<>();
        for (int i = 1; i < orbitRealPath.getElements().size(); i++) {
            LineTo lineTo = (LineTo) orbitRealPath.getElements().get(i);
            listPoints3D.add(new com.example.systemesolaire.Point3D(lineTo.getX(), lineTo.getY(), 0));
        }

        BorderPane bp = (BorderPane)infoPlanete.getChildren().get(0);
        orbitPath = new com.example.systemesolaire.PolyLine3D(listPoints3D, 15, Color.ORANGE, com.example.systemesolaire.PolyLine3D.LineType.TRIANGLE);

        orbitPath.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (com.example.systemesolaire.Main.pivot.xProperty().isBound())
                {
                    com.example.systemesolaire.Main.pivot.xProperty().unbind();
                }
                if (com.example.systemesolaire.Main.pivot.yProperty().isBound())
                {
                    com.example.systemesolaire.Main.pivot.yProperty().unbind();
                }
                com.example.systemesolaire.Main.pivot.xProperty().bind(this.translateXProperty());
                com.example.systemesolaire.Main.pivot.yProperty().bind(this.translateYProperty());
                zoom.setX(0);
                zoom.setY(0);
            }
        });
        orbitPath.setOnMouseEntered(mouseEvent -> {
            super.setMouseTransparent(true);
            if (!principal.getChildren().contains(infoPlanete))
            {
                infoPlanete.setVisible(false);
                principal.getChildren().add(infoPlanete);
            }
            Platform.runLater(() -> {
                infoPlanete.setTranslateX(principal.getScene().getWidth() - bp.getWidth() - 20);
                infoPlanete.setTranslateY(principal.getScene().getHeight()/2 - bp.getHeight()/2);
                infoPlanete.setVisible(true);
            });
        });
        orbitPath.setOnMouseExited(mouseEvent -> {
            principal.getChildren().remove(infoPlanete);
            super.setMouseTransparent(false);
        });


        Constantes.InfoPlanetes[] infoPlanetes = Constantes.InfoPlanetes.values();
        orbitPath.getTransforms().addAll(new Rotate(infoPlanetes[indexPlanete].inclination, Rotate.X_AXIS),
                new Rotate(infoPlanetes[indexPlanete].inclination, Rotate.Y_AXIS));

        Group groupOrbit = new Group(orbitPath);

        com.example.systemesolaire.Main.systeme.getChildren().addAll(groupOrbit);
        drawPath = false;
    }

    public void updatePosition(com.example.systemesolaire.Vecteur2 sunPosition, double t, int indexPlanete) {
        com.example.systemesolaire.Vecteur2 newPosition = orbit.findOrbitPoint(sunPosition, periapsis/ ECHELLE, apoapsis/ ECHELLE, t);
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());

        if (drawPath) {
            updateOrbitPath(sunPosition, indexPlanete);
        }
    }

    public double getRadiusPlanete() {
        return radius;
    }

    public Color getCouleur() {
        return couleur;
    }
}
