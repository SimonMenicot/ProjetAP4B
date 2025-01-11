package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class PlateauDeJeu {
    private RoueDesSemestres roue;
    private List<FeuilleDeJoueur> feuillesJoueurs;
    private int nbJoueur;



    public PlateauDeJeu(int nombreJoueurs) {
        this.roue = new RoueDesSemestres();
        this.feuillesJoueurs = new ArrayList<>();
        this.nbJoueur = nombreJoueurs;

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
    }

    public String couleurSalle(int couleur){
        switch(couleur){
            case 0:
                return "Orange";
            case 1:
                return "Bleu";
            case 2:
                return "Blanc";
            default:
                return "ERROR";
        }
    }

    public void gainRessource(FeuilleDeJoueur feuille,int valeur,int couleur){
        feuille.getSecteur(couleur).ajouterRessource(valeur);
    }

    public RoueDesSemestres getRoue() {
        return roue;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public FeuilleDeJoueur getFeuillesJoueurs(int i) {
        return feuillesJoueurs.get(i);
    }

}