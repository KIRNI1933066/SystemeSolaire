package com.example.systemesolaire.utilitaires;

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

    public Vecteur3(Vecteur3 autre)
    {
        this.setX(autre.getX());
        this.setY(autre.getY());
        this.setZ(autre.getZ());
    }

    public final void setX(double x) {
        this.XProperty().set(x);
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

    public final void setY(double y) {
        this.YProperty().set(y);
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

    public void setZ(double z) {this.ZProperty().set(z);}

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

    public static Vecteur3 soustraire(Vecteur3 vecteurA, Vecteur3 vecteurB)
    {
        return new Vecteur3(vecteurA.getX() - vecteurB.getX(), vecteurA.getY() - vecteurB.getY(), vecteurA.getZ() - vecteurB.getZ());
    }

    public static Vecteur3 add(Vecteur3 vecteurA, Vecteur3 vecteurB)
    {
        return new Vecteur3(vecteurA.getX() + vecteurB.getX(), vecteurA.getY() + vecteurB.getY(), vecteurA.getZ() + vecteurB.getZ());
    }

    public void add(Vecteur3 other) {
        this.setX(getX() + other.getX());
        this.setY(getY() + other.getY());
        this.setZ(getZ() + other.getZ());
    }

    public Vecteur3 multiScalaire(double nombre) {
        return new Vecteur3(getX() * nombre, getY() * nombre,getZ() * nombre);
    }

    public double norme() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    public double normeSqr()
    {
        return (this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    public Vecteur3 normalizer()
    {
        return new Vecteur3(this.getX() / norme(), this.getY() / norme(), this.getZ() / norme());
    }

    @Override
    public String toString() {
        return "X: " + x.get() + " Y: " + y.get() + " Z: " + z.get();
    }

}

