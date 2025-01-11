package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FeuilleDeJoueur {
    protected String nomJoueur;
    protected int nbEtudiants;
    protected int nbEnseignants;
    protected int nbPersonnels;
    protected int scoreFinal;

    private List<Secteur> secteurs;

    public FeuilleDeJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
        this.nbEtudiants = 0; // vérifier si aucun "habitant" en début de partie
        this.nbEnseignants = 0;
        this.nbPersonnels = 0;

        this.secteurs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                this.secteurs.add(new Secteur(i,"Contrat de Subvention", "Pole administratif"));
            }
            else if (i == 1) {
                this.secteurs.add(new Secteur(i,"Constitution d'une association", "Residence étudiante"));
            }
            else{
                this.secteurs.add(new Secteur(i, "Publication d'une thèse", "Laboratoire de recherche"));
            }
        }
        System.out.println("Création d'un secteur");
    }

    public int calculScore() {
        int score = 0;
        for (Secteur s : secteurs){
            score +=s.calculScore();
        }
        score+=nbEnseignants;
        score+=nbEtudiants;
        score+=nbPersonnels;
        return score;
    }

    public String getName() {
        return this.nomJoueur;
    }

    public void coupureBudget(int secteur, int colonne){
        if (!secteurs.get(0).actPrestige[colonne]){
            secteurs.get(secteur).coupureBudget(colonne);
        }
    }

    public boolean isConcevable(int secteur, int colonne){
        return secteurs.get(secteur).isConcevable(colonne);
    }

    public int getRessources(int secteur){return secteurs.get(secteur).getRessources();}

    public Secteur getSecteur(int secteur){return secteurs.get(secteur);}

    public int getNbEtudiants(){return nbEtudiants;}

    public int getNbEnseignants(){return nbEnseignants;}

    public int getNbPersonnels(){return nbPersonnels;}

    public void addEnseignant(int n){
        nbEnseignants += n;
    }

    public void addEtudiant(int n){
        nbEtudiants += n;
    }

    public void addPersonnel(int n){
        nbPersonnels += n;
    }
}
