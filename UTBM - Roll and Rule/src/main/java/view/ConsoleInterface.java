package main.java.view;

import main.java.model.PlateauDeJeu;
import main.java.model.RoueDesSemestres;

public class ConsoleInterface implements UI{
    public void affichageDe(PlateauDeJeu plateau){
        System.out.println("Affichage des dés :\n");

        for(int j = 0 ; j < 4 ; j++){
            if(plateau.getRoue().getTour()%2==1){
                if(plateau.getRoue().getDe(j).isTransparent()){
                    System.out.println("De"  + (j+1) + " : transparent\t Valeur : " + plateau.getRoue().getDe(j).getValeur() + ", Couleur : " + plateau.couleurSalle(plateau.getRoue().getSalle(j+1).getSecteur()));
                }else{
                    System.out.println("De"  + (j+1) + " : noir\t\t\t Valeur : " + plateau.getRoue().getDe(j).getValeur() + ", Couleur : " + plateau.couleurSalle(plateau.getRoue().getSalle(j+1).getSecteur()));
                }
            }else{
                if(plateau.getRoue().getDe(j).isTransparent()){
                    System.out.println("De"  + (j+1) + " : transparent\t Valeur : " + plateau.getRoue().getDe(j).getValeur() + ", Couleur : " + plateau.couleurSalle(plateau.getRoue().getSalle(j+5).getSecteur()));
                }else{
                    System.out.println("De"  + (j+1) + " : noir\t\t\t Valeur : " + plateau.getRoue().getDe(j).getValeur() + ", Couleur : " + plateau.couleurSalle(plateau.getRoue().getSalle(j+5).getSecteur()));
                }
            }
        }
    }
}