package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class PlateauDeJeu {
    private RoueDesSemestres roue;
    private List<FeuilleDeJoueur> feuillesJoueurs;
    private int nbJoueur;



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

    public String couleurSalle(int couleur){
        switch(couleur){
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

    public void gainRessource(FeuilleDeJoueur feuille,int valeur,int couleur){
        feuille.getSecteur(couleur).ajouterRessource(valeur);
    }

    public RoueDesSemestres getRoue() {
        return roue;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public FeuilleDeJoueur getFeuillesJoueurs(int i) {
        return feuillesJoueurs.get(i);
    }





    private void verificationBonusHabitant(FeuilleDeJoueur feuille,int numSecteur,int nbHabitantGagne){
        Scanner myObj = new Scanner(System.in);
        switch(numSecteur){
            case 0:
                if(feuille.nbPersonnels+nbHabitantGagne>=3 && feuille.nbEnseignants>=3 && feuille.nbEtudiants>=3 && feuille.nbPersonnels-nbHabitantGagne<3){
                    for(int i = 0; i < 3; ++i){
                        gainRessource(feuille,1,i);
                    }
                }else if((feuille.nbPersonnels+nbHabitantGagne>=6 && feuille.nbEnseignants>=6 && feuille.nbEtudiants>=6 && feuille.nbPersonnels-nbHabitantGagne<6) || (feuille.nbPersonnels+nbHabitantGagne>=11 && feuille.nbEnseignants>=11 && feuille.nbEtudiants>11 && feuille.nbPersonnels-nbHabitantGagne<11)){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez construire le batiment de fonction de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Personnel P/Etudiant E/Enseignant C)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'P':
                                secteur = 0;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,0,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'E':
                                secteur = 1;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,1,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'C':
                                secteur = 2;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,2,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,false);

                }else if(feuille.nbPersonnels+nbHabitantGagne<=15 && feuille.nbPersonnels-nbHabitantGagne<15){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez faire l'action de prestige de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Etudiant E/Enseignant C)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'E':
                                secteur = 1;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'C':
                                secteur = 2;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,false);
                }else if(feuille.nbPersonnels+nbHabitantGagne>=20 && feuille.nbPersonnels<=20){
                    feuille.nbEtudiants++;
                    verificationBonusHabitant(feuille,1,1);
                    feuille.nbEnseignants++;
                    verificationBonusHabitant(feuille,2,1);
                }
                break;
            case 1:
                if(feuille.nbEtudiants+nbHabitantGagne>=3 && feuille.nbPersonnels>=3 && feuille.nbEnseignants>=3 && feuille.nbEtudiants-nbHabitantGagne<3){
                    for(int i = 0; i < 3; ++i){
                        gainRessource(feuille,1,i);
                    }
                }else if((feuille.nbEtudiants+nbHabitantGagne>=6 && feuille.nbPersonnels>=6 && feuille.nbEnseignants>=6 && feuille.nbEtudiants-nbHabitantGagne<6) || (feuille.nbEtudiants+nbHabitantGagne>=11 && feuille.nbPersonnels>=11 && feuille.nbEnseignants>11 && feuille.nbEtudiants-nbHabitantGagne<11)){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez construire le batiment de fonction de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Personnel P/Etudiant E/Enseignant C)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'P':
                                secteur = 0;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,0,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'E':
                                secteur = 1;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,1,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'C':
                                secteur = 2;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,2,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,false);

                }else if(feuille.nbEtudiants+nbHabitantGagne<=15 && feuille.nbEtudiants-nbHabitantGagne<15){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez faire l'action de prestige de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Personnels P/Enseignant E)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'P':
                                secteur = 0;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'E':
                                secteur = 2;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,true);
                }else if(feuille.nbEtudiants+nbHabitantGagne>=20 && feuille.nbEtudiants<=20){
                    feuille.nbPersonnels++;
                    verificationBonusHabitant(feuille,0,1);
                    feuille.nbEnseignants++;
                    verificationBonusHabitant(feuille,2,1);
                }
                break;
            case 2:
                if(feuille.nbEnseignants+nbHabitantGagne>=3 && feuille.nbPersonnels>=3 && feuille.nbEtudiants>=3 && feuille.nbEnseignants-nbHabitantGagne<3){
                    for(int i = 0; i < 3; ++i){
                        gainRessource(feuille,1,i);
                    }
                }else if((feuille.nbEnseignants+nbHabitantGagne>=6 && feuille.nbPersonnels>=6 && feuille.nbEtudiants>=6 && feuille.nbEnseignants-nbHabitantGagne<6) || (feuille.nbEnseignants+nbHabitantGagne>=11 && feuille.nbPersonnels>=11 && feuille.nbEtudiants>11 && feuille.nbEnseignants-nbHabitantGagne<11)){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez construire le batiment de fonction de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Personnel P/Etudiant E/Enseignant C)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'P':
                                secteur = 0;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,0,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'E':
                                secteur = 1;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,1,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'C':
                                secteur = 2;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,2,false)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,false);

                }else if(feuille.nbEnseignants+nbHabitantGagne<=15 && feuille.nbEnseignants-nbHabitantGagne<15){
                    boolean incorrect;
                    int secteur=0;
                    int numBat=0;
                    do{
                        incorrect = false;
                        System.out.println("Vous pouvez faire l'action de prestige de votre choix\n" +
                                "Veuillez choisir la catégorie du batiment (Personnels P/Etudiant E)\n");
                        char choix = myObj.next().charAt(0);
                        System.out.println("Choisissez maintenant le numero du batiment\n");
                        numBat = myObj.nextInt();
                        switch(choix){
                            case 'P':
                                secteur = 0;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            case 'E':
                                secteur = 1;
                                if(feuille.getSecteur(secteur).isConcevable(numBat)){
                                    if(!constructionBatiment(feuille,numBat,secteur,true)){
                                        incorrect = true;
                                    };
                                }else{
                                    incorrect = true;
                                }
                                break;
                            default:
                                incorrect = true;
                                break;
                        }
                    }while(incorrect);
                    verificationBonusAction(feuille,secteur,numBat,true);
                }else if(feuille.nbEnseignants+nbHabitantGagne>=20 && feuille.nbEnseignants<20){
                    feuille.nbPersonnels++;
                    verificationBonusHabitant(feuille,0,1);
                    feuille.nbEtudiants++;
                    verificationBonusHabitant(feuille,1,1);
                }
        }
    }




    private void majTourSuivant(){
        //Permet de retourner la salle avec le dé noir
        int place=0;
        for(int i = 0 ; i < 4 ; i++){
            if(!roue.des.get(i).isTransparent()){
                place = i;
            }
        }


        //Change l'ordre des salles
        if(roue.getTour()%2 == 1){
            roue.salles.get(place+1).retourner();
        }else{
            roue.salles.get(place+5).retourner();
        }
        roue.salles.addLast(roue.salles.getFirst());
        roue.salles.remove(roue.salles.getFirst());
    }

    private void affichageDe(){
        System.out.println("Affichage des dés :\n");

        for(int j = 0 ; j < 4 ; j++){
            if(roue.getTour()%2==1){
                if(roue.des.get(j).isTransparent()){
                    System.out.println("De"  + (j+1) + " : transparent\t Valeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+1).getSecteur()));
                }else{
                    System.out.println("De"  + (j+1) + " : noir\t\t\t Valeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+1).getSecteur()));
                }
            }else{
                if(roue.des.get(j).isTransparent()){
                    System.out.println("De"  + (j+1) + " : transparent\t Valeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+5).getSecteur()));
                }else{
                    System.out.println("De"  + (j+1) + " : noir\t\t\t Valeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+5).getSecteur()));
                }
            }
        }
    }

}