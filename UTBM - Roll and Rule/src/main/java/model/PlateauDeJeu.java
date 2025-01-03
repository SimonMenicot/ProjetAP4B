package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlateauDeJeu {
    private RoueDesSemestres roue;
    private List<FeuilleDeJoueur> feuillesJoueurs;



    public PlateauDeJeu(int nombreJoueurs) {
        this.roue = new RoueDesSemestres();
        this.feuillesJoueurs = new ArrayList<>();

        // Création des feuilles de joueurs
        for (int i = 1; i < nombreJoueurs + 1; i++) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Entrez le nom du joueur " + i + " ");
            String name = myObj.nextLine();  // Read user input
            //System.out.println("Nom du joueur " + i + ": ");  // Output user input

            this.feuillesJoueurs.add(new FeuilleDeJoueur(name));
        }
        for (FeuilleDeJoueur joueur : this.feuillesJoueurs){
            System.out.println(joueur.getName());
        }
        this.jouerTour();
    }

    public void jouerTour() {
        roue.nouveauTour();
        // à continuer d'implémenter
    }
}
