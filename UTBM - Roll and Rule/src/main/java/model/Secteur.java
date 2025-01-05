package main.java.model;

public class Secteur {
    protected int numeroSecteur;
    protected boolean[] actPrestige = new boolean[6];
    protected boolean[] batFonction = new boolean[6];
    protected String nomActPrestige;
    protected String nomBatFonction;
    protected boolean[] projetConcevable = new boolean[6];
    protected int ressources;
    protected int ressourcesUtilisees;
    protected int multBatFonction;
    protected int multActPrestige;

    public Secteur(int numeroSecteur, String nomActPrestige, String nomBatFonction) {
        this.numeroSecteur = numeroSecteur;
        this.nomActPrestige = nomActPrestige;
        this.nomBatFonction = nomBatFonction;
        this.multBatFonction = 0;
        this.multActPrestige = 0;
        this.ressources = 3;
        this.ressourcesUtilisees = 0;
    }

    public void ajouterRessource(int val) {
        this.ressources += val;
    }

    public int getRessources() {return this.ressources;}

    public int getRessourcesUtilisees() {
        return this.ressourcesUtilisees;
    }

    public boolean utiliserRessource(int val) {
        if (val <= ressources) {
            this.ressources -= val;
            this.ressourcesUtilisees += val;
            return true;
        }
        return false;
    }

    public int calculScore() {
        int score = 0;
        for (int i = 0; i < 6; i++) {
            if (actPrestige[i]) {
                score += multActPrestige;
            }
            if (batFonction[i]) {
                score += multBatFonction;
            }
        }
        score += ressources/2; // int et int -> division entière
        return score;
    }

    public void coupureBudget(int colonne){
        projetConcevable[colonne] = false;
    }

    public boolean isConcevable(int colonne){
        return projetConcevable[colonne];
    }
}
