package main.java.view;

import main.java.model.FeuilleDeJoueur;
import main.java.model.PlateauDeJeu;
import main.java.model.RoueDesSemestres;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public void affichageScore(PlateauDeJeu plateau){
        if(plateau.getNbJoueur()==1){
            System.out.println("Vous avez obtenu un score de " + plateau.getFeuillesJoueurs(0).calculScore() + " points. Félicitation !\n");
        }else{
            List <Integer> winner = new ArrayList<>();
            int best = 0;
            for(int i = 0 ; i < plateau.getNbJoueur()  ; i++){
                System.out.println("\n Le joueur " + (i + 1) + " a obtenu un score de " + plateau.getFeuillesJoueurs(i).calculScore() + " points\n");
                if(best == 0 || best < plateau.getFeuillesJoueurs(i).calculScore()){
                    winner.clear();
                    best = plateau.getFeuillesJoueurs(i).calculScore();
                    winner.add(i+1);
                }else if(best == plateau.getFeuillesJoueurs(i).calculScore()){
                    winner.add(i+1);
                }
            }
            if(winner.size() == 1){
                System.out.println( plateau.getFeuillesJoueurs(winner.getFirst()).getName() +" remporte la partie !\n");
            }else if(winner.size() > 1 && winner.size() < plateau.getNbJoueur()){
                System.out.println("Les joueurs suivant sont à égalité et remportent la victoire :\n");
                for (Integer integer : winner) {
                    System.out.println(plateau.getFeuillesJoueurs(integer).getName() + " remporte la partie !\n");
                }
            }else{
               System.out.println("Tout les joueurs sont à égalité et remportent la victoire :\n");
            }
        }

    }
}