package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class PlateauDeJeu {
    private RoueDesSemestres roue;
    private List<FeuilleDeJoueur> feuillesJoueurs;



    public PlateauDeJeu(int nombreJoueurs) {
        this.roue = new RoueDesSemestres();
        this.feuillesJoueurs = new ArrayList<>();
        // à continuer d'implémenter
    }

    public void jouerTour() {
        roue.nouveauTour();
        // à continuer d'implémenter
    }
}
