package main.java.model;

public class FeuilleDeJoueur {
    protected String nomJoueur;
    protected int nbEtudiants;
    protected int nbEnseignants;
    protected int nbPersonnels;
    protected int scoreFinal;

    private Secteur[] secteurs;

    public FeuilleDeJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
        this.nbEtudiants = 0; // vérifier si aucun "habitant" en début de partie
        this.nbEnseignants = 0;
        this.nbPersonnels = 0;
        this.scoreFinal = 0;

        this.secteurs = new Secteur[3];
    }

    public int calculScore() {
        // à implémenter (appeler calculScore des secteur, points des habitants,...)
        return 0;
    }


    public int getScoreFinal() {
        return scoreFinal;
    }
}
