package main.java.view;

import main.java.model.FeuilleDeJoueur;
import main.java.model.PlateauDeJeu;

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

    public void affichageNomJoueur(FeuilleDeJoueur f){
        System.out.println("C'est à " + f.getName() + " de jouer\n\n");
    }

    public void affichageRessourceInsuffisante(){
        System.out.println("Vous n'avez pas de ressource pour jouer, gain de ressource minimum\n");
    }

    public void affichageFondInsuffisant(){
        System.out.println("Vous n'avez pas assez de Fond pour modifier la valeur du de\n");
    }

    public int MenuTour(PlateauDeJeu plateau,int valeurFinal,int couleurFinal){
        System.out.println("Votre de : \tValeur : " + valeurFinal + ", Couleur : " + plateau.couleurSalle(couleurFinal) + "\n");
        System.out.println("Choisissez ce que vous souhaitez faire :\n");
        System.out.println("\t1 - Modifier la valeur du de\n" +
                "\t2 - Modifier la couleur de la salle\n" +
                "\t3 - Gagner des ressources\n" +
                "\t4 - Faire une action de prestige/construire un batiment de fonction\n");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
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