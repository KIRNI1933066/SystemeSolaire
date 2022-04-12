package com.example.systemesolaire;

import javafx.animation.Interpolatable;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import java.io.FileNotFoundException;

public class Vaisseau extends Cylinder {

    private double posX,posY;
    private Planete[] planetes;

    public Vaisseau(double posX, double posY, Planete[] planetes) {
        this.posX = posX;
        this.posY = posY;
        this.planetes = planetes;
        super.setTranslateX(posX);
        super.setTranslateY(posY);
        super.setHeight(50);
        super.setRadius(20);
        PhongMaterial matVaisseau = new PhongMaterial();
        matVaisseau.setDiffuseColor(Color.RED);
        super.setMaterial(matVaisseau);
    }

    public Vecteur2 r(Planete planete) {
        return new Vecteur2(planete.getTranslateX()-posX,planete.getTranslateY()-posY);
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
        Vecteur2 r = r(planetePlusProche);
        double normR = Math.sqrt(((r.getX())*(r.getX()) + ((r.getY())*(r.getY()))));
        Vecteur2 a = new Vecteur2((-r.getX()*mu)/(normR*normR*normR),(-r.getY()*mu)/(normR*normR*normR));

    }



}
