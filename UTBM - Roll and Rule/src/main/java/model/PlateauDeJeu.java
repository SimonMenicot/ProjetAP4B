package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlateauDeJeu {
    private RoueDesSemestres roue;
    private List<FeuilleDeJoueur> feuillesJoueurs;
    private nbJoueur;



    public PlateauDeJeu(int nombreJoueurs) {
        this.roue = new RoueDesSemestres();
        this.feuillesJoueurs = new ArrayList<>();
        this.nbJoueur = nombreJoueurs;

        // Création des feuilles de joueurs
        for (int i = 1; i < nombreJoueurs + 1; i++) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Entrez le nom du joueur " + i + " ");
            String name = myObj.nextLine();  // Read user input
            //System.out.println("Nom du joueur " + i + ": ");  // Output user input

            this.feuillesJoueurs.add(new FeuilleDeJoueur(name));
        }
        for (FeuilleDeJoueur joueur : this.feuillesJoueurs){
            System.out.println(joueur.getName());
        }
    }

    public void jouerTour() {
        roue.nouveauTour();
        if (roue.numeroTour >= 3){
            int i = 0;
            while (!roue.des.get(i).isTransparent()){
                i++;
            }
            int numero = roue.des.get(i).valeur;
            for(int j = 0; j < nbJoueur; j++){
                if(!feuillesJoueurs.get(j).secteurs.get(0).actPrestige[numero]){
                    for( i = 0; i < 3 ; i++){
                        feuillesJoueurs.get(j).secteurs.get(i).projetConcevable[numero] = false;
                    }
                }
            }
        }
        for ( int i = 0 ; i < nbJoueur ; i++){
            FeuilleDeJoueur f = feuillesJoueurs.get(i);
            System.out.println("C'est a " + f.getName() + " de jouer\n\n");
            boolean peuxJouer = false;
            for(int j = 0 ; j < 3 ; j++){
                if(f.secteurs.get(j).ressources-f.secteurs.get(j).ressourcesUtilisees>0){
                    peuxJouer = true;
                }
            }
            if(peuxJouer){
                System.out.println("Affichage des des :\n");
                for(int j = 0 ; j < 4 ; j++){
                    if(roue.numeroTour%2==1){
                        System.out.println("De " + (i+1) +"\tValeur : " + roue.des.get(i).valeur + ", Couleur : " + couleurSalle(roue.salles.get(i+1)));
                    }else{
                        System.out.println("De " + (i+1) +"\tValeur : " + roue.des.get(i).valeur + ", Couleur : " + couleurSalle(roue.salles.get(i+5)));
                    }
                }
                Scanner myObj = new Scanner(System.in);
                System.out.println("Entrez le numero du de que vous souhaitez selectionner");
                int numero = myObj.nextInt();
                boolean incorrect;
                do{
                    incorrect = false;
                    if(numero < 1 || numero > 4){
                        incorrect = true;
                    }else if(!roue.des.get(numero - 1).isTransparent){
                        incorrect = true;
                    }else if(f.secteurs.get(1).ressources-f.secteurs.get(1).ressourcesUtilisees==0 && numero == 3 ) {
                        incorrect = true;
                    }else if(f.secteurs.get(1).ressources-f.secteurs.get(1).ressourcesUtilisees==1 && numero == 4){
                        incorrect = true;
                    }else if(numero == 2){
                        System.out.println("Quelle ressource souhaitez-vous dépensez (F/M/C)?");
                        char choix = myObj.next().charAt(0);
                        switch(choix){
                            case 'F':
                                if(f.secteurs.getFirst().ressources-f.secteurs.getFirst().ressourcesUtilisees==0){
                                    System.out.println("Vous n'avez pas assez de Fonds\n");
                                    incorrect = true;
                                }else{
                                    f.secteurs.getFirst().ressourcesUtilisees--;
                                }
                                break;
                            case 'M':
                                if(f.secteurs.get(1).ressources-f.secteurs.get(1).ressourcesUtilisees==0){
                                    System.out.println("Vous n'avez pas assez de Motivation\n");
                                    incorrect = true;
                                }else{
                                    f.secteurs.get(1).ressourcesUtilisees--;
                                }
                                break;
                            case 'C':
                                if(f.secteurs.get(2).ressources-f.secteurs.get(2).ressourcesUtilisees==0){
                                    System.out.println("Vous n'avez pas assez de Connaissance\n");
                                    incorrect = true;
                                }else{
                                    f.secteurs.get(2).ressourcesUtilisees--;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }else{
                        if(numero==3){
                            f.secteurs.get(1).ressourcesUtilisees--;
                        }else if(numero==4){
                            f.secteurs.get(1).ressourcesUtilisees-=2;
                        }
                    }

                }while(incorrect);
            }else{
                System.out.println("Vous n'avez pas de ressource pour jouer, gain de ressource minimum\n");
                for(int j = 0 ; j < 3 ; j++){
                    f.secteurs.get(j).ajouterRessource(1);
                }

            }
        }
        // à continuer d'implémenter
    }

    private String couleurSalle(Salle salle){
        int num = salle.getSecteur();
        switch(num){
            case 0:
                return "Orange";
            case 1:
                return "Bleu";
            case 2:
                return "Blanc";
            default:
                return "ERROR";
        }
}
