package com.example.systemesolaire.utilitaires;

public class Constantes {

    public static final double ECHELLE_RAYON_1 = 500;
    public static final double ECHELLE_RAYON_2 = 200;
    public static final int ECHELLE_MASSE = 275;
    public static final double MULTIPLE_INCL = 2;
    public static final double G = 6.67430E-11;

    public static final String IMAGES_PATH = "file:src/main/images/";
    public static final String TEXTURES_PATH = IMAGES_PATH + "textures/";


    public enum InfoPlanetes {
        MERCURE(2439.7,2439.7 / ECHELLE_RAYON_2,46000000, 70000000, "Mercure", 7*MULTIPLE_INCL,TEXTURES_PATH + "mercurymap.jpg",0.330E+24 * G * ECHELLE_MASSE,0.330, "88 jours"),
        VENUS(6051.8,6051.8 / ECHELLE_RAYON_2,107.48e6, 108.94e6, "Venus", 3.39*MULTIPLE_INCL,TEXTURES_PATH + "venusmap.jpg",4.87E+24 * G * ECHELLE_MASSE,4.87, "224.7 jours"),
        TERRE(6371.0,6371.0 / ECHELLE_RAYON_2,147.10e6, 152.10e6, "Terre",0*MULTIPLE_INCL,TEXTURES_PATH + "earthmap.jpg",5.97E+24 * G * ECHELLE_MASSE,5.97, "365 jours"),
        MARS(3389.5,3389.5  / ECHELLE_RAYON_2,206.7e6, 249.2e6, "Mars",1.85*MULTIPLE_INCL,TEXTURES_PATH + "marsmap.jpg",0.642E+24 * G * ECHELLE_MASSE,0.642, "687 jours"),
        JUPITER(69911.0,69911.0 / ECHELLE_RAYON_1,740.595e6, 816.363e6, "Jupiter",1.3*MULTIPLE_INCL,TEXTURES_PATH + "jupitermap.jpg",1898E+24 * G * ECHELLE_MASSE,1898, "11.9 années"),
        SATURNE(58232.0,58232.0 / ECHELLE_RAYON_1,1357.554e6, 1506.527e6,"Saturne",2.49*MULTIPLE_INCL,TEXTURES_PATH + "saturnmap.jpg",568E+24 * G * ECHELLE_MASSE,568, "29.5 années"),
        URANUS(25362.0,25362.0 / ECHELLE_RAYON_2,2732.696e6, 3001.390e6, "Uranus",0.77*MULTIPLE_INCL,TEXTURES_PATH + "uranusmap.jpg",86.8E+24 * G * ECHELLE_MASSE,86.8, "84 années"),
        NEPTUNE(24622.0,24622.0 / ECHELLE_RAYON_2, 4471.050e6, 4558.856e6, "Neptune",1.77*MULTIPLE_INCL,TEXTURES_PATH + "neptunemap.jpg",102E+24 * G * ECHELLE_MASSE,102, "164.8 années");

        public double periapsis;
        public double apoapsis;
        public final String name;
        public final double rayonScaled;
        public final double rayon;
        public final double inclination;
        public final String texture;
        public final double mu;
        public double masse;
        public final String tempsOrbite;

        InfoPlanetes(double rayon, double rayonScaled, double periapsis, double apoapsis, String name, double inclination, String texture, double mu, double masse, String tempsOrbite) {
            this.periapsis = periapsis;
            this.apoapsis = apoapsis;
            this.name = name;
            this.rayonScaled = rayonScaled;
            this.rayon = rayon;
            this.inclination = inclination;
            this.texture = texture;
            this.mu = mu;
            this.masse = masse;
            this.tempsOrbite = tempsOrbite;
        }
    }
}
