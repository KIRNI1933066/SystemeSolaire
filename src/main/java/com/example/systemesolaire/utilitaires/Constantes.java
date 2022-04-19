package com.example.systemesolaire.utilitaires;

import javafx.scene.paint.Color;

public class Constantes {

    public static final double ECHELLE = 200;
    public static final double MULTIPLE_INCL = 2;
    public static final double G = 6.67430E-11;

    public static final String IMAGES_PATH = "file:src/main/images/";
    public static final String TEXTURES_PATH = IMAGES_PATH + "textures/";


    public enum InfoPlanetes {
        MERCURE(60,2439.7 / ECHELLE,46000000, 70000000, "Mercure", 7*MULTIPLE_INCL,TEXTURES_PATH + "mercurymap.jpg",0.330E+24 * G,0.330),
        VENUS(110,6051.8 / ECHELLE,107.48e6, 108.94e6, "Venus", 3.39*MULTIPLE_INCL,TEXTURES_PATH + "venusmap.jpg",4.87E+24 * G,4.87),
        TERRE(150,6371.0 / ECHELLE,147.10e6, 152.10e6, "Terre",0*MULTIPLE_INCL,TEXTURES_PATH + "earthmap.jpg",5.97E+24 * G,5.97),
        MARS(230,3389.5  / ECHELLE,206.7e6, 249.2e6, "Mars",1.85*MULTIPLE_INCL,TEXTURES_PATH + "marsmap.jpg",0.642E+24 * G,0.642),
        JUPITER(800,69911.0 / ECHELLE,740.595e6, 816.363e6, "Jupiter",1.3*MULTIPLE_INCL,TEXTURES_PATH + "jupitermap.jpg",1898E+24 * G,1898),
        SATURNE(1400,58232.0 / ECHELLE,1357.554e6, 1506.527e6,"Saturne",2.49*MULTIPLE_INCL,TEXTURES_PATH + "saturnmap.jpg",568E+24 * G,568),
        URANUS(3000,25362.0 / ECHELLE,2732.696e6, 3001.390e6, "Uranus",0.77*MULTIPLE_INCL,TEXTURES_PATH + "uranusmap.jpg",86.8E+24 * G,86.8),
        NEPTUNE(4400,24622.0 / ECHELLE, 4471.050e6, 4558.856e6, "Neptune",1.77*MULTIPLE_INCL,TEXTURES_PATH + "neptunemap.jpg",102E+24 * G,102);

        public double periapsis;
        public double apoapsis;
        public final String name;
        public final double radius;
        public final double distSoleil;
        public final double inclination;
        public final String texture;
        public final double mu;
        public double masse;

        InfoPlanetes(double distSoleil, double radius, double periapsis, double apoapsis, String name, double inclination, String texture, double mu, double masse) {
            this.periapsis = periapsis;
            this.apoapsis = apoapsis;
            this.name = name;
            this.radius = radius;
            this.distSoleil = distSoleil;
            this.inclination = inclination;
            this.texture = texture;
            this.mu = mu;
            this.masse= masse;
        }
    }
}
