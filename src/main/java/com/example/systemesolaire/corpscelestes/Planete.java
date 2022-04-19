package com.example.systemesolaire.corpscelestes;

import com.example.systemesolaire.utilitaires.Orbit;
import com.example.systemesolaire.controllers.Controlleur;
import com.example.systemesolaire.utilitaires.Constantes;
import com.example.systemesolaire.utilitaires.InfoPlanete;
import com.example.systemesolaire.utilitaires.PolyLine3D;
import com.example.systemesolaire.utilitaires.Vecteur3;
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
import java.util.ArrayList;
import java.util.List;

import static com.example.systemesolaire.Main.*;

public class Planete extends Sphere {

    private Vecteur3 position;
    public String name;
    private Orbit orbit;
    private double periapsis, apoapsis;
    private PolyLine3D orbitPath;
    private double radius;
    private InfoPlanete infoPlanete;
    public double masse;

    private boolean drawPath = true;

    public Planete (double radius, double periapsis, double apoapsis, String name, String texture, int i, double masse) {
        super(radius);
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(new Image(texture));
        super.setMaterial(mat);
        position = new Vecteur3();
        super.setTranslateX(position.getX());
        super.setTranslateY(position.getY());
        super.translateXProperty().bind(position.XProperty());
        super.translateYProperty().bind(position.YProperty());
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
        this.masse = masse;
        orbit = new Orbit(5000);

        infoPlanete = new InfoPlanete(this);
        BorderPane bp = (BorderPane)infoPlanete.getChildren().get(0);
        infoPlanete.setVisible(false);
        principal.getChildren().add(infoPlanete);
        Platform.runLater(() -> principal.getChildren().remove(infoPlanete));
        position.XProperty().addListener((observable, oldVal, newVal) -> infoPlanete.setDistanceSoleil(Vecteur3.distanceTo(position, POS_SOLEIL)));

        super.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (Controlleur.getPivot().xProperty().isBound())
                {
                    Controlleur.getPivot().xProperty().unbind();
                }
                if (Controlleur.getPivot().yProperty().isBound())
                {
                    Controlleur.getPivot().yProperty().unbind();
                }
                Controlleur.getPivot().xProperty().bind(this.translateXProperty());
                Controlleur.getPivot().yProperty().bind(this.translateYProperty());
                Controlleur.getZoom().setX(0);
                Controlleur.getZoom().setY(0);
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
        super.setOnMouseExited(mouseEvent -> principal.getChildren().remove(infoPlanete));
    }
    public Planete() {}


    public void updateOrbitPath(Vecteur3 sunPosition, int indexPlanete) {
        if (orbitPath != null) {
            com.example.systemesolaire.Main.GROUP_SYSTEME_SOLAIRE.getChildren().remove(orbitPath);
        }
        Path orbitRealPath = orbit.getPathOrbit(sunPosition, periapsis/ ECHELLE, apoapsis/ ECHELLE);


        List<Vecteur3> listPoints3D = new ArrayList<>();
        for (int i = 1; i < orbitRealPath.getElements().size(); i++) {
            LineTo lineTo = (LineTo) orbitRealPath.getElements().get(i);
            listPoints3D.add(new Vecteur3(lineTo.getX(), lineTo.getY(), 0));
        }

        BorderPane bp = (BorderPane)infoPlanete.getChildren().get(0);
        orbitPath = new PolyLine3D(listPoints3D, 15, Color.PURPLE, PolyLine3D.LineType.TRIANGLE);

        orbitPath.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (Controlleur.getPivot().xProperty().isBound())
                {
                    Controlleur.getPivot().xProperty().unbind();
                }
                if (Controlleur.getPivot().yProperty().isBound())
                {
                    Controlleur.getPivot().yProperty().unbind();
                }
                Controlleur.getPivot().xProperty().bind(this.translateXProperty());
                Controlleur.getPivot().yProperty().bind(this.translateYProperty());
                Controlleur.getZoom().setX(0);
                Controlleur.getZoom().setY(0);
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

        com.example.systemesolaire.Main.GROUP_SYSTEME_SOLAIRE.getChildren().addAll(groupOrbit);
        drawPath = false;
    }

    public void updatePosition(Vecteur3 sunPosition, double t, int indexPlanete) {
        Vecteur3 newPosition = orbit.findOrbitPoint(sunPosition, periapsis/ ECHELLE, apoapsis/ ECHELLE, t);
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());

        if (drawPath)
            updateOrbitPath(sunPosition, indexPlanete);
    }

    public double getRadiusPlanete() {
        return radius;
    }
}