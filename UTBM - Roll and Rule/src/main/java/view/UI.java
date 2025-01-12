package main.java.view;

import main.java.model.FeuilleDeJoueur;
import main.java.model.PlateauDeJeu;

public interface UI {

    void affichageDe(PlateauDeJeu plateau);

    void affichageFiche(FeuilleDeJoueur f);

    void affichageRessourceInsuffisante();

    int MenuTour(PlateauDeJeu plateau,int valeurFinal,int couleurFinal);

    void affichageFondInsuffisant();

    void affichageScore(PlateauDeJeu plateau);
}


