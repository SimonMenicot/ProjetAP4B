package main.java.model;

public class Salle {
    protected int secteurRecto;
    protected int secteurVerso;
    protected boolean retourne;

    public Salle(int secteurRecto, int secteurVerso) {
        this.secteurRecto = secteurRecto;
        this.secteurVerso = secteurVerso;
        this.retourne = false;
    }

    public void retourner() {
        retourne = !retourne;
    }

    public int getSecteur(){
        if (retourne){
            return secteurVerso;
        }else{
            return secteurRecto;
        }
    }
}

