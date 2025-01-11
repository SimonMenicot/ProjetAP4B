package main.java.controller;

import main.java.model.De;
import main.java.model.FeuilleDeJoueur;
import main.java.model.PlateauDeJeu;
import main.java.model.Secteur;
import main.java.view.ConsoleInterface;
import main.java.view.UI;

import java.util.Scanner;

public class JeuController {
    protected PlateauDeJeu plateau;
    protected UI ui;

    public JeuController() {

    }

    public void lancerPartie(){
        // Choix du nombre de joueurs
        Scanner myObj = new Scanner(System.in);
        System.out.println("Entrez le nombre de joueurs : ");
        String n_input = myObj.nextLine();
        System.out.println("Nombre de joueurs : " + n_input);
        int n_players = Integer.valueOf(n_input);

        // Création plateau de jeu et de la roue des semestres
        plateau = new PlateauDeJeu(n_players);
        ui = new ConsoleInterface();

        // Choix de l'interface
        System.out.println("Entrez 1 pour l'UI graphique et 2 pour l'UI console : ");
        String choix = myObj.nextLine();
        int choix_ui = Integer.valueOf(choix);
        if (choix_ui == 1){
            ui = new GraphicalInterface();
        }
        else {
            ui = new ConsoleInterface();
        }
        this.jouerTour();
    }

    public void jouerTour() {
        /* Initialisation du tour */
        plateau.getRoue().nouveauTour();
        int tour = plateau.getRoue().getTour();
        if (tour >= 6){ // 6 semestres = 3 années
            int i = 0;
            while (!plateau.getRoue().getDe(i).isTransparent()){
                i++;
            }
            De deNoir = plateau.getRoue().getDe(i);
            int secteur = plateau.getRoue().getSecteur(deNoir);
            for(int j = 0; j < plateau.getNbJoueur(); j++){
                plateau.getFeuillesJoueurs(j).coupureBudget(secteur, deNoir.getValeur());
            }
        }
        /* Actions des joueurs */
        for ( int i = 0 ; i < plateau.getNbJoueur() ; i++){
            FeuilleDeJoueur f = plateau.getFeuillesJoueurs(i);
            System.out.println("C'est à " + f.getName() + " de jouer\n\n");
            boolean peuxJouer = plateau.getRoue().getDe(0).isTransparent();
            // peux jouer est false si le joueur n'a aucune ressource et que le dé noir est en 1ère position
            for(int j = 0 ; j < 3 ; j++){
                if(f.getRessources(j)>0){
                    peuxJouer = true;
                }
            }
            if(peuxJouer){
                peuxJouer(f);
            }else{
                System.out.println("Vous n'avez pas de ressource pour jouer, gain de ressource minimum\n");
                for(int j = 0 ; j < 3 ; j++){
                    f.getSecteur(j).ajouterRessource(1);
                }

            }
        }
        majTourSuivant();
    }

    private void peuxJouer(FeuilleDeJoueur f){
        ui.affichageDe(plateau);
        int numero = choixDuDe(f);
        Scanner myObj = new Scanner(System.in);


        int numeroSalle ;
        if(plateau.getRoue().getTour()%2==1){
            numeroSalle = numero;
        }else{
            numeroSalle = numero + 4;
        }
        int couleurInitial = plateau.getRoue().getSalle(numeroSalle).getSecteur();
        int couleurFinal = plateau.getRoue().getSalle(numeroSalle).getSecteur();
        int valeurInitial = plateau.getRoue().getDe(numeroSalle-1).getValeur();
        int valeurFinal = plateau.getRoue().getDe(numero-1).getValeur();
        boolean actionEffectuee = false;
        boolean incorrect;
        do{
            System.out.println("Votre de : \tValeur : " + valeurFinal + ", Couleur : " + plateau.couleurSalle(couleurFinal) + "\n");
            System.out.println("Choisissez ce que vous souhaitez faire :\n");
            System.out.println("\t1 - Modifier la valeur du de\n" +
                    "\t2 - Modifier la couleur de la salle\n" +
                    "\t3 - Gagner des ressources\n" +
                    "\t4 - Faire une action de prestige/construire un batiment de fonction\n");
            int choix = myObj.nextInt();
            int ressourceDisponible = 0;
            switch(choix){
                case 1:
                    ressourceDisponible = f.getSecteur(0).getRessources()-f.getSecteur(0).getRessourcesUtilisees();
                    if(ressourceDisponible==0){
                        System.out.println("Vous n'avez pas assez de Fond pour modifier la valeur du de\n");
                        break;
                    }else {
                        do {
                            incorrect = false;
                            System.out.println("Vous avez " + ressourceDisponible + " ressource disponible pour modifier la valeur du de\n" +
                                    "(Valeur initial = " + valeurInitial + ",\tValeur actuel = " + valeurFinal + ")\n" +
                                    "De combien souhaitez-vous modifier la valeur du de?\n");
                            choix = myObj.nextInt();
                            System.out.println("Souhaitez-vous agmenter ou diminuer  la valeur du de (+/-)\n");
                            char operation = myObj.next().charAt(0);
                            if ((valeurFinal + choix > 6 && operation == '+') || (valeurFinal - choix < 1 && operation == '-')) {
                                System.out.println("Valeur du de incorrect, veuillez choisir une autre valeur et\\ou operation\n");
                                incorrect = true;
                            }else {
                                int temp;
                                switch (operation) {
                                    case '+':
                                        temp = valeurFinal + choix;
                                        if (Math.abs(temp - valeurInitial) > ressourceDisponible) {
                                            System.out.println("Vous n'avez pas les ressources pour modifier la valeur du de\n");
                                            incorrect = true;
                                        }else{
                                            valeurFinal = temp;
                                        }
                                        break;
                                    case '-':
                                        temp = valeurFinal - choix;
                                        if (Math.abs(temp - valeurInitial) > ressourceDisponible) {
                                            System.out.println("Vous n'avez pas les ressources pour modifier la valeur du de\n");
                                            incorrect = true;
                                        }else{
                                            valeurFinal = temp;
                                        }
                                        break;
                                    default:
                                        System.out.println("ERREUR");
                                        incorrect = true;
                                        break;
                                }
                            }
                        } while (incorrect);
                    }
                    break;
                case 2:
                    ressourceDisponible = f.getSecteur(2).getRessources()-f.getSecteur(2).getRessourcesUtilisees();
                    if(ressourceDisponible<2){
                        System.out.println("Vous n'avez pas assez de Connaissance pour modifier la valeur du de\n");
                    }else{
                        do{
                            incorrect = false;
                            System.out.println("Vous avez " + ressourceDisponible + " ressource disponible pour modifier la couleur de la salle \n" +
                                    "(Couleur initial : " + plateau.couleurSalle(couleurInitial) +",\tCouleur actuelle : " + plateau.couleurSalle(couleurFinal) + "\n" +
                                    "En quelle couleur voulez-vous changer la salle (Orange/Bleu/Blanc)?\n");
                            String couleur = myObj.next();
                            if(!couleur.equals("Bleu") && !couleur.equals("Blanc") && !couleur.equals("Orange")){
                                System.out.println("Couleur incorrect");
                                incorrect = true;
                            }else{
                                if((couleur.equals("Orange") && couleurFinal==0)||(couleur.equals("Bleu") && couleurFinal==1) || (couleur.equals("Blanc") && couleurFinal==2)){
                                    System.out.println("Couleur identique a la couleur actuel, pas de changement");
                                }else{
                                    switch(couleur){
                                        case "Orange":
                                            couleurFinal = 0;
                                            break;
                                        case "Bleu":
                                            couleurFinal = 1;
                                            break;
                                        case "Blanc":
                                            couleurFinal = 2;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }while(incorrect);

                    }
                    break;
                case 3:
                    plateau.gainRessource(f,valeurFinal,couleurFinal);
                    actionEffectuee = true;
                    break;
                case 4:
                    Secteur secteur = f.getSecteur(couleurFinal);
                    if(secteur.isConcevable(valeurFinal)){
                        if(secteur.isDonePrestige(valeurFinal) && secteur.isDoneFonction(valeurFinal)){
                            System.out.println("Impossible d'effectue une action avec ce de : les 2 actions ont deja ete faite\n");
                        }else{
                            do{
                                incorrect = false;
                                System.out.println("Souhaitez-vous :\n" +
                                        "1 - Faire l'action de prestige\n" +
                                        "2 - Construire le batiment de fonction\n" +
                                        "3 - Revenir en arrière\n");
                                choix = myObj.nextInt();
                                switch(choix){
                                    case 1:
                                        actionEffectuee = constructionBatiment(f,valeurFinal,couleurFinal,true);
                                        if(!actionEffectuee){
                                            incorrect = true;
                                        }
                                        break;
                                    case 2:
                                        actionEffectuee = constructionBatiment(f,valeurFinal,couleurFinal,false);
                                        if(!actionEffectuee){
                                            incorrect = true;
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }while(incorrect);
                        }
                    }else{
                        System.out.println("Impossible de faire une action");
                    }
                default:
                    System.out.println("Veuillez choisir un chiffre correct");
                    break;
            }
        }while (!actionEffectuee);

        f.getSecteur(0).ressourcesUtilisees+=Math.abs(valeurFinal-valeurInitial);
        if(couleurInitial!=couleurFinal){
            f.getSecteur(2).ressourcesUtilisees+=2;
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

    private int choixDuDe(FeuilleDeJoueur f){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Entrez le numéro du dé que vous souhaitez sélectionner\n");
        int numero = myObj.nextInt();

        boolean incorrect;
        do{
            incorrect = false;
            if(numero < 1 || numero > 4){
                incorrect = true;
            }else if(!plateau.getRoue().getDe(numero - 1).isTransparent()){
                incorrect = true;
            }else if(f.getSecteur(1).getRessources()-f.getSecteur(1).getRessourcesUtilisees()==0 && numero == 3 ) {
                incorrect = true;
            }else if(f.getSecteur(1).getRessources()-f.getSecteur(1).getRessourcesUtilisees()<=1 && numero == 4){
                incorrect = true;
            }else if(numero == 2){
                System.out.println("Quelle ressource souhaitez-vous dépensez (F/M/C)?");
                char choix = myObj.next().charAt(0);
                switch(choix){
                    case 'F':
                        if(f.getSecteur(0).getRessources()-f.getSecteur(0).getRessourcesUtilisees()==0){
                            System.out.println("Vous n'avez pas assez de Fonds\n");
                            incorrect = true;
                        }else{
                            f.getSecteur(0).utiliserRessource(1);
                        }
                        break;
                    case 'M':
                        if(f.getSecteur(1).getRessources()-f.getSecteur(1).getRessourcesUtilisees()==0){
                            System.out.println("Vous n'avez pas assez de Motivation\n");
                            incorrect = true;
                        }else{
                            f.getSecteur(1).utiliserRessource(1);
                        }
                        break;
                    case 'C':
                        if(f.getSecteur(2).getRessources()-f.getSecteur(2).getRessourcesUtilisees()==0){
                            System.out.println("Vous n'avez pas assez de Connaissance\n");
                            incorrect = true;
                        }else{
                            f.getSecteur(2).utiliserRessource(1);
                        }
                        break;
                    default:
                        incorrect = true;
                        break;
                }
            }else{
                if(numero==3){
                    f.getSecteur(1).utiliserRessource(1);
                }else if(numero==4){
                    f.getSecteur(1).utiliserRessource(2);
                }
            }
            if(incorrect){
                System.out.println("Vous ne pouvez pas selectionner ce de\nEntrez le numero du de que vous souhaitez selectionner");
                numero = myObj.nextInt();
            }

        }while(incorrect);

        return numero;
    }

    private boolean constructionBatiment(FeuilleDeJoueur f, int valeur, int couleur, boolean isActPrestige){
        boolean actionEffectuee = false;
        if(isActPrestige){
            if(f.getSecteur(couleur).isDonePrestige(valeur)){
                System.out.println("L'action a deja ete faite\n");
            }else{
                f.getSecteur(couleur).doPrestige(valeur);
                verificationBonusAction(f,couleur,valeur,true);
                actionEffectuee = true;
            }
        }else{
            if(f.getSecteur(couleur).isDoneFonction(valeur)){
                System.out.println("L'action a deja ete faite\n");
            }else{
                f.getSecteur(couleur).doFonction(valeur);
                verificationBonusAction(f,couleur,valeur,false);
                actionEffectuee = true;
            }
        }
        return actionEffectuee;
    }

    private void verificationBonusAction(FeuilleDeJoueur feuille,int numSecteur,int numBatimentConstruit,boolean isActionPrestige){
        Secteur secteur;
        numBatimentConstruit--;
        switch(numSecteur){
            case 0:
                secteur = feuille.getSecteur(0);
                if(isActionPrestige){
                    if((secteur.isDonePrestige() && numBatimentConstruit == 1) || (secteur.isDonePrestige(1) && numBatimentConstruit == 0)){
                        feuille.nbPersonnels++;
                        verificationBonusHabitant(feuille,0,1);
                    }else if((secteur.actPrestige[2] && numBatimentConstruit == 3) || (secteur.actPrestige[3] && numBatimentConstruit == 2)){
                        feuille.nbEtudiants++;
                        verificationBonusHabitant(feuille,1,1);
                    }else if((secteur.actPrestige[4] && numBatimentConstruit == 5) || (secteur.actPrestige[5] && numBatimentConstruit == 4)){
                        feuille.nbEnseignants++;
                        verificationBonusHabitant(feuille,2,1);
                    }
                }else{
                    if((secteur.batFonction[0] && numBatimentConstruit == 1) || (secteur.batFonction[1] && numBatimentConstruit == 0)){
                        feuille.nbPersonnels+=2;
                        verificationBonusHabitant(feuille,0,2);
                    }else if((secteur.batFonction[4] && numBatimentConstruit == 5) || (secteur.batFonction[5] && numBatimentConstruit == 4)){
                        gainRessource(feuille,3,0);
                    }
                }
                break;
            case 1:
                secteur = feuille.getSecteur(1);
                if(isActionPrestige){
                    int temp=0;
                    switch(numBatimentConstruit){
                        case 0:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(0).actPrestige[i]){
                                    temp++;
                                }
                            }
                            gainRessource(feuille,temp*3,0);
                            break;
                        case 1:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(0).batFonction[i]){
                                    temp++;
                                }
                            }
                            feuille.nbPersonnels+=2*temp;
                            verificationBonusHabitant(feuille,0,2*temp);
                            break;
                        case 2:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(1).actPrestige[i]){
                                    temp++;
                                }
                            }
                            gainRessource(feuille,temp*3,1);
                            break;
                        case 3:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(1).batFonction[i]){
                                    temp++;
                                }
                            }
                            feuille.nbEtudiants+=2*temp;
                            verificationBonusHabitant(feuille,1,2*temp);
                            break;
                        case 4:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(2).actPrestige[i]){
                                    temp++;
                                }
                            }
                            gainRessource(feuille,temp*3,2);
                            break;
                        case 5:
                            for(int i = 0; i<6; i++){
                                if(feuille.getSecteur(2).batFonction[i]){
                                    temp++;
                                }
                            }
                            feuille.nbEnseignants+=2*temp;
                            verificationBonusHabitant(feuille,2,2*temp);
                            break;
                        default:
                            System.out.println("Erreur de numero de batiments");
                    }
                }else{
                    if((secteur.batFonction[0] && numBatimentConstruit == 1) || (secteur.batFonction[1] && numBatimentConstruit == 0)){
                        gainRessource(feuille,3,1);
                    }else if((secteur.batFonction[2] && numBatimentConstruit == 3) || (secteur.batFonction[3] && numBatimentConstruit == 2)){
                        feuille.nbEtudiants+=2;
                        verificationBonusHabitant(feuille,1,2);
                    }
                }
                break;
            case 2:
                secteur = feuille.getSecteur(2);
                if(isActionPrestige){
                    int temp=0;
                    for(int i = 0; i<6; i++){
                        if(secteur.actPrestige[i]) {
                            temp++;
                        }
                    }
                    int bonus=0;
                    if(temp == 1 || temp == 2){
                        bonus = 1;
                    }else if(temp == 3 || temp == 4){
                        bonus = 2;
                    }else if(temp == 5 || temp == 6){
                        bonus = 3;
                    }
                    switch(numBatimentConstruit){
                        case 0:
                            feuille.getSecteur(0).multActPrestige = bonus;
                            break;
                        case 1:
                            feuille.getSecteur(0).multBatFonction = bonus;
                            break;
                        case 2:
                            feuille.getSecteur(1).multActPrestige = bonus;
                            break;
                        case 3:
                            feuille.getSecteur(1).multBatFonction = bonus;
                            break;
                        case 4:
                            feuille.getSecteur(2).multActPrestige = bonus;
                            break;
                        case 5:
                            feuille.getSecteur(2).multBatFonction = bonus;
                            break;
                        default:
                            System.out.println("Erreur de numero de batiments");
                            break;
                    }
                }else{
                    if((secteur.batFonction[2] && numBatimentConstruit == 3) || (secteur.batFonction[3] && numBatimentConstruit == 2)){
                        gainRessource(feuille,3,2);
                    }else if((secteur.batFonction[4] && numBatimentConstruit == 5) || (secteur.batFonction[5] && numBatimentConstruit == 4)){
                        feuille.nbEnseignants+=2;
                        verificationBonusHabitant(feuille,2,2);
                    }
                }
                break;
            default:
                System.out.println("Erreur de numero de secteur");
        }
    }

    public boolean relance(){

        return false;
    }
}
