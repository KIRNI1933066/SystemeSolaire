package com.example.systemesolaire.corpscelestes;

import com.example.systemesolaire.utilitaires.Constantes;
import com.example.systemesolaire.utilitaires.Vecteur3;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.io.FileNotFoundException;
import java.util.Random;

public class Vaisseau extends Sphere {

    private Planete[] planetes;
    private Vecteur3 vitesse, position;
    private boolean bouger = false;
    private Random rand = new Random();

    public Vaisseau(Planete[] planetes, Vecteur3 position) {
        this.planetes = planetes;
        super.setRadius(10);
        PhongMaterial matVaisseau = new PhongMaterial();
        matVaisseau.setDiffuseColor(Color.RED);
        super.setMaterial(matVaisseau);
        this.position = position;
        vitesse = new Vecteur3(rand.nextInt(5),rand.nextInt(5),rand.nextInt(5));
        super.translateXProperty().bind(position.XProperty());
        super.translateYProperty().bind(position.YProperty());
        super.translateZProperty().bind(position.ZProperty());

    }

    public Vecteur3 r(Planete planete) {
        return new Vecteur3(planete.getTranslateX() - position.getX(), planete.getTranslateY() - position.getY(),
                planete.getTranslateZ() - position.getZ());
    }

    public Planete planetePlusProche() throws FileNotFoundException {
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

    public void updatePosition() throws FileNotFoundException {
        if (bouger) {
            Planete planetePlusProche = planetePlusProche();
            if (planetePlusProche == null) {
                acc(new Vecteur3(0, 0, 0));
                System.out.println("aucune planete");
                return;
            }
            String nom = planetePlusProche.name;
            System.out.println(nom.toUpperCase() + position);
            double mu = Constantes.InfoPlanetes.valueOf(nom.toUpperCase()).mu;
            Vecteur3 r = r(planetePlusProche);
            /*if (r.getX() < 10 && r.getY() < 10 && r.getZ() < 10) {
                bouger = false;
            }*/
            double normR = r.normaliser();
            System.out.println("norm" + normR);
            Vecteur3 a = r.multiScalaire((mu/(normR * normR * normR))/9E+9);
            System.out.println(a);
            acc(a);
        }
    }

    public void acc(Vecteur3 a) {
        vitesse.add(a);
        position.add(vitesse.multiScalaire(0.0166666));
    }

    public void setBouger(boolean bouger) {
        this.bouger = bouger;
    }
}
