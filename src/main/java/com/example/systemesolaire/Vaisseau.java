package com.example.systemesolaire;

import javafx.animation.Interpolatable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;

import java.io.FileNotFoundException;

public class Vaisseau extends Cylinder {

    private double posX,posY,posZ;
    private Planete[] planetes;

    public Vaisseau(double posX, double posY, double posZ, Planete[] planetes, SubScene subScene) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.planetes = planetes;
        super.translateXProperty().bind(new SimpleDoubleProperty(posX));
        super.translateYProperty().bind(new SimpleDoubleProperty(posY));
        super.translateZProperty().bind(new SimpleDoubleProperty(posZ));
        super.setHeight(50);
        super.setRadius(20);
        PhongMaterial matVaisseau = new PhongMaterial();
        matVaisseau.setDiffuseColor(Color.RED);
        super.setMaterial(matVaisseau);
        new Controlleur(this,subScene);
    }

    public Vecteur3 r(Planete planete) {
        return new Vecteur3(planete.getTranslateX()-posX, planete.getTranslateY()-posY,
                planete.getTranslateZ()-posZ);
    }

    public Planete planetePlusProche() throws FileNotFoundException {
        Planete plusProche = new Planete(0, Color.BLACK,0,0,"","",0,0);
        double distanceMin = 100000000;
        double normR;
        for (Planete planete : planetes) {
            normR = Math.sqrt(((plusProche.getTranslateX()-posX)*(plusProche.getTranslateX()-posX))
                    + ((plusProche.getTranslateY()-posY)*(plusProche.getTranslateY()-posY)));
            if (normR < distanceMin) {
                plusProche = planete;
                distanceMin = normR;
            }
        }
        return plusProche;
    }

    public void updatePosition() throws FileNotFoundException {
        Constantes.InfoPlanetes[] infoPlanetes = Constantes.InfoPlanetes.values();
        Planete planetePlusProche = planetePlusProche();
        String nom = planetePlusProche.name;
        double mu = Constantes.InfoPlanetes.valueOf(nom).mu;
        Vecteur3 r = r(planetePlusProche);
        double normR = Math.sqrt(((r.getX())*(r.getX()) + ((r.getY())*(r.getY()))));
        Vecteur3 a = new Vecteur3((-r.getX()*mu)/(normR*normR*normR),(-r.getY()*mu)/(normR*normR*normR),
                (-r.getZ()*mu)/(normR*normR*normR));

    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }
}
