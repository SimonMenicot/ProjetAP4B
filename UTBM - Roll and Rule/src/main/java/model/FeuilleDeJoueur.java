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

    public String getName() {
        return this.nomJoueur;
    }
    // J'ai pas trop compris le Secteur[3] ducoup j'ai fait ca, ca marche pas vu que je sais pas comment me servir du Secteur[3]
    // mais dans l'idée faut juste modifier le .add et mettre le bon truc pour ajouter
    public int createSecteur() {
        // A faire
        for (int i = 1; i < 4 + 1; i++) {
            if (i == 1) {
                this.secteurs.add(new Secteur(i,"contratSub", "poleAdmin"));
            }
            else if (i == 2) {
                this.secteurs.add(new Secteur(i,"constitAsso", "resEtudiante"));
            }
            else if (i == 3){
                this.secteurs.add(new Secteur(i, "publiThese", "labRecherche"));
            }
        }
        return 0;
    }


    public int getScoreFinal() {
        return scoreFinal;
    }
}
