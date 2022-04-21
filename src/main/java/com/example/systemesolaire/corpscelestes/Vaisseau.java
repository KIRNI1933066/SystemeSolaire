package com.example.systemesolaire.corpscelestes;

import com.example.systemesolaire.Main;
import com.example.systemesolaire.utilitaires.Constantes;
import com.example.systemesolaire.utilitaires.Vecteur3;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Vaisseau extends Sphere {

    private final ICorpsCelestes[] corpsCelestes;
    private final Vecteur3 vitesse, position;
    private boolean bouger = false;
    private double masse = 10000;

    public Vaisseau(ICorpsCelestes[] corpsCelestes, Vecteur3 position) {
        this.corpsCelestes = corpsCelestes;
        super.setRadius(10);
        PhongMaterial matVaisseau = new PhongMaterial();
        matVaisseau.setDiffuseColor(Color.RED);
        super.setMaterial(matVaisseau);
        this.position = position;
        float min = 100;
        float max = 200;
        vitesse = new Vecteur3(0.5f, 1f, 0);
        super.translateXProperty().bind(position.XProperty());
        super.translateYProperty().bind(position.YProperty());
        super.translateZProperty().bind(position.ZProperty());

    }

    /*public Vecteur3 r(Planete planete) {
        return new Vecteur3(planete.getTranslateX() - position.getX(), planete.getTranslateY() - position.getY(),
                planete.getTranslateZ() - position.getZ());
    }

    public Planete planetePlusProche() {
        Planete plusProche = null;
        double distanceMin = 100000000;
        double normR;
        for (Planete planete : planetes) {
            normR = Math.sqrt(((planete.getTranslateX() - position.getX()) * (planete.getTranslateX() - position.getX()))
                    + ((planete.getTranslateY() - position.getY()) * (planete.getTranslateY() - position.getY())));
            if (normR < distanceMin) {
                plusProche = planete;
                distanceMin = normR;
            }
        }
        return plusProche;
    }

    public void updatePosition() {
        if (bouger) {
            Planete planetePlusProche = planetePlusProche();
            if (planetePlusProche == null) {
                System.out.println("aucune planete");
                return;
            }
            String nom = planetePlusProche.nom;
            System.out.println(nom.toUpperCase() + position);
            double mu = Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).mu;
            Vecteur3 r = r(planetePlusProche);
            if (Math.abs(r.getX()) < Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).radius&&
                    Math.abs(r.getY()) < Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).radius &&
                    Math.abs(r.getZ()) < Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).radius) {
                bouger = false;
                System.out.println("r :" + r  + "infLuence : " + Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).radius);

            }
            double normR = r.norme();
            System.out.println("norm" + normR);
            Vecteur3 a = r.multiScalaire((mu/(normR * normR * normR))/9E+9);
            System.out.println(a);
        }
    }*/

    public void updateVitesse() {
        for (ICorpsCelestes corpsCelestes : corpsCelestes) {
            if (corpsCelestes != null)
            {
                double mu = corpsCelestes.getMU();
                double distanceSqr = (Vecteur3.soustraire(corpsCelestes.getPosition().multiScalaire(Main.ECHELLE * 1000), position.multiScalaire(Main.ECHELLE * 1000))).normeSqr();
                Vecteur3 directionForce = (Vecteur3.soustraire(corpsCelestes.getPosition().multiScalaire(Main.ECHELLE * 1000), position.multiScalaire(Main.ECHELLE * 1000))).normalizer();
                Vecteur3 force = directionForce.multiScalaire(mu * masse / distanceSqr);
                Vecteur3 acceleration = force.multiScalaire(1/masse);
                System.out.println("Acceleration de " + corpsCelestes.getNom() + ": " + acceleration);
                vitesse.add(acceleration);
            }
        }
    }

    public void updatePosition() {
        if (bouger)
        {
            position.add(vitesse);
        }
    }

    public void setBouger(boolean bouger) {
        this.bouger = bouger;
    }

    public void setPosition(Vecteur3 position)
    {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        this.position.setZ(position.getZ());
    }

    public void setVitesse(Vecteur3 vitesse)
    {
        this.vitesse.setX(vitesse.getX());
        this.vitesse.setY(vitesse.getY());
        this.vitesse.setZ(vitesse.getZ());
    }

    public Vecteur3 getPosition() {
        return position;
    }
}
