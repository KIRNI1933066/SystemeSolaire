package com.example.systemesolaire;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;

public class Vecteur3 {


    private DoubleProperty x,y,z;

    public Vecteur3() {
        this.setX(0);
        this.setY(0);
        this.setZ(0);
    }

    public Vecteur3(double x, double y, double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public final void setX(double var1) {
        this.XProperty().set(var1);
    }

    public final double getX() {
        return this.x == null ? 0 : this.x.get();
    }

    public final DoubleProperty XProperty() {
        if (this.x == null) {
            this.x = new DoublePropertyBase(0) {
                public Object getBean() {
                    return Vecteur3.this;
                }

                public String getName() {
                    return "X";
                }
            };
        }

        return this.x;
    }

    public final void setY(double var1) {
        this.YProperty().set(var1);
    }

    public final double getY() {
        return this.y == null ? 0 : this.y.get();
    }

    public final DoubleProperty YProperty() {
        if (this.y == null) {
            this.y = new DoublePropertyBase(0) {
                public Object getBean() {
                    return Vecteur3.this;
                }

                public String getName() {
                    return "Y";
                }
            };
        }

        return this.y;
    }

    public void setZ(double z) {this.z.set(z);}

    public final double getZ() {
        return this.z == null ? 0 : this.z.get();
    }

    public final DoubleProperty ZProperty() {
        if (this.z == null) {
            this.z = new DoublePropertyBase(0) {
                public Object getBean() {
                    return Vecteur3.this;
                }

                public String getName() {
                    return "Z";
                }
            };
        }

        return this.z;
    }

    public static double distanceTo(Vecteur3 vectorA, Vecteur3 vectorB) {
        double x = vectorA.getX() - vectorB.getX();
        double y = vectorA.getY() - vectorB.getY();
        double z = vectorA.getZ() - vectorB.getZ();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public void add(double x, double y, double z) {
        this.setX(getX() + x);
        this.setY(getY() + y);
        this.setZ(getZ() + z);
    }

    public void add(Vecteur3 other) {
        this.setX(getX() + other.getX());
        this.setY(getY() + other.getY());
        this.setZ(getZ() + other.getZ());
    }

    public void substract(Vecteur3 other) {
        this.setX(getX() - other.getX());
        this.setY(getY() - other.getY());
        this.setZ(getY() - other.getZ());
    }

    public void multiScalaire(double nombre) {
        this.setX(getX() * nombre);
        this.setY(getY() * nombre);
        this.setZ(getZ() * nombre);
    }

    public boolean equals(Vecteur3 other) {
        return (this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ());
    }

    @Override
    public String toString() {
        return "X: " + x.get() + " Y: " + y.get() + " Z: " + z.get();
    }
}

