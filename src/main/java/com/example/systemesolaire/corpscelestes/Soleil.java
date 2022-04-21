package com.example.systemesolaire.corpscelestes;

import com.example.systemesolaire.utilitaires.Constantes;
import com.example.systemesolaire.utilitaires.Vecteur3;

public class Soleil implements ICorpsCelestes {

    private final Vecteur3 position;

    public Soleil(Vecteur3 position)
    {
        this.position = position;
    }

    public Vecteur3 getPosition() {
        return position;
    }

    public double getMU() {
        double masse = 1.989E30;
        return masse * Constantes.G;
    }

    public String getNom() {
        return "Soleil";
    }
}
