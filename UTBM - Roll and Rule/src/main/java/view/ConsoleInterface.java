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

    public void affichageFiche(FeuilleDeJoueur f){
        System.out.println("\nC'est à " + f.getName() + " de jouer\n");

        for(int i = 0 ; i < 3 ; i++){
            System.out.print("\t\t\t\t\t");
            for(int j = 0 ; j < 6 ; j++){
                if(f.getSecteur(0).isDonePrestige(j)){
                    System.out.print("__^__");
                }else{
                    if(j==0){
                        System.out.print(" ");
                    }
                    System.out.print("      ");
                }
            }
            System.out.print("\n");
            switch (i){
                case 0:
                    System.out.print("   Secteur Orange");
                    break;
                case 1:
                    System.out.print("   Secteur Bleu  ");
                    break;
                case 2:
                    System.out.print("   Secteur Blanc ");
                    break;
                default:
                    System.out.print("   Secteur Couleur ");
                    break;
            }
            System.out.println("\t|  1  |  2  |  3  |  4  |  5  |  6  |");
            List<Character> temp = new ArrayList<Character>();
            for(int j = 0 ; j < 6 ; j++){
                if(f.getSecteur(i).isDonePrestige(j)){
                    temp.addLast('O');
                }else if(!f.getSecteur(i).isConcevable(j)){
                    temp.addLast('X');
                }else{
                    temp.addLast(' ');
                }
            }
            switch(i){
                case 0:
                    System.out.print("\tSubvention\t\t");
                    break;
                case 1:
                    System.out.print("\tAssociation\t\t");
                    break;
                case 2:
                    System.out.print("\t   Thèse\t\t");
                    break;
                default:
                    System.out.print("\tERREUR_NOM\t\t");
            }
            for(int j = 0 ; j < 6 ; j++){
                if(j==0){
                    System.out.print('|');
                }
                System.out.print(" [" + temp.get(j) + "] |");
            }
            System.out.print("\n");
            temp.clear();
            for(int j = 0 ; j < 6 ; j++) {
                if (f.getSecteur(i).isDoneFonction(j)) {
                    temp.addLast('O');
                } else if (!f.getSecteur(i).isConcevable(j)) {
                    temp.addLast('X');
                } else {
                    temp.addLast(' ');
                }
            }
            switch(i){
                case 0:
                    System.out.print("Pôle administratif\t");
                    break;
                case 1:
                    System.out.print("Résidence étudiante\t");
                    break;
                case 2:
                    System.out.print("\tLaboratoire\t\t");
                    break;
                default:
                    System.out.print("ERREUR_NOM\t");
            }
            for(int j = 0 ; j < 6 ; j++){
                if(j==0){
                    System.out.print('|');
                }
                System.out.print(" [" + temp.get(j) + "] |");
            }
            System.out.print("\n");
            switch(i){
                case 0:
                    System.out.print("Fonds\t\t\t");
                    break;
                case 1:
                    System.out.print("Motivation\t\t");
                    break;
                case 2:
                    System.out.print("Connaissance\t");
                    break;
                default:
                    System.out.print("Ressource\t");
            }
            int k;
            for(k = 0 ; k < 20 ; k++ ){
                if(k==0){
                    System.out.print('.');
                }
                if(f.getRessources(i)+f.getSecteur(i).getRessourcesUtilisees() - k >f.getRessources(i)){
                    System.out.print("X.");
                }else if(k<f.getRessources(i)){
                    System.out.print("O.");
                }else{
                    System.out.print(" .");
                }

            }
            System.out.print("\n");
        }
        




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