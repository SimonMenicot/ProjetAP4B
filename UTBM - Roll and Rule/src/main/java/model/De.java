package main.java.model;

public class De {
    protected int valeur;
    protected boolean isTransparent;

    public De(boolean isTransparent) {
        this.isTransparent = isTransparent;
        this.valeur = 1;
    }

    public int lancer() {
        this.valeur = (int) (Math.random() * 6) + 1;
        return this.valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
