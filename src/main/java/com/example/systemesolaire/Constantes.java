package com.example.systemesolaire;

import javafx.scene.paint.Color;

public class Constantes {

    public static final double ECHELLE = 200;
    public static final double MULTIPLE_INCL = 2;

    public enum InfoPlanetes {
        MERCURE(60,2439.7 / ECHELLE,46000000, 70000000, Color.GRAY, "Mercure", 7*MULTIPLE_INCL,"mercurymap.jpg"),
        VENUS(110,6051.8 / ECHELLE,107.48e6, 108.94e6, Color.SANDYBROWN, "Venus", 3.39*MULTIPLE_INCL,"venusmap.jpg"),
        TERRE(150,6371.0 / ECHELLE,147.10e6, 152.10e6, Color.BLUE, "Terre",0*MULTIPLE_INCL,"earthmap.jpg"),
        MARS(230,3389.5  / ECHELLE,206.7e6, 249.2e6, Color.RED, "Mars",1.85*MULTIPLE_INCL,"marsmap.jpg"),
        JUPITER(800,69911.0 / ECHELLE,740.595e6, 816.363e6, Color.ORANGE, "Jupiter",1.3*MULTIPLE_INCL,"jupitermap.jpg"),
        SATURNE(1400,58232.0 / ECHELLE,1357.554e6, 1506.527e6, Color.YELLOW, "Saturne",2.49*MULTIPLE_INCL,"saturnmap.jpg"),
        URANUS(3000,25362.0 / ECHELLE,2732.696e6, 3001.390e6, Color.DARKCYAN, "Uranus",0.77*MULTIPLE_INCL,"uranusmap.jpg"),
        NEPTUNE(4400,24622.0 / ECHELLE, 4471.050e6, 4558.856e6, Color.DARKBLUE, "Neptune",1.77*MULTIPLE_INCL,"neptunemap.jpg");

        public double periapsis;
        public double apoapsis;
        public final Color color;
        public final String name;
        public final double radius;
        public final double distSoleil;
        public final double inclination;
        public final String texture;

        InfoPlanetes(double distSoleil, double radius, double periapsis, double apoapsis, Color color, String name, double inclination, String texture) {
            this.periapsis = periapsis;
            this.apoapsis = apoapsis;
            this.color = color;
            this.name = name;
            this.radius = radius;
            this.distSoleil = distSoleil;
            this.inclination = inclination;
            this.texture = texture;
        }
    }
}
