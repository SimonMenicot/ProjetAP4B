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

    public void jouerTour() {
        /* Initialisation du tour */
        roue.nouveauTour();
        int tour = roue.getTour();
        if (tour >= 6){ // 6 semestres = 3 années
            int i = 0;
            while (!roue.des.get(i).isTransparent()){
                i++;
            }
            De deNoir = roue.des.get(i);
            int secteur = roue.getSecteur(deNoir);
            for(int j = 0; j < nbJoueur; j++){
                feuillesJoueurs.get(j).coupureBudget(secteur, deNoir.getValeur());
            }
        }

        /* Actions des joueurs */
        for ( int i = 0 ; i < nbJoueur ; i++){
            FeuilleDeJoueur f = feuillesJoueurs.get(i);
            System.out.println("C'est à " + f.getName() + " de jouer\n\n");
            boolean peuxJouer = roue.des.get(0).isTransparent();
            // peux jouer est false si le joueur n'a aucune ressource et que le dé noir est en 1ère position
            for(int j = 0 ; j < 3 ; j++){
                if(f.getRessources(j)>0){
                    peuxJouer = true;
                }
            }

            if(peuxJouer){
                System.out.println("Affichage des dés :\n");

                for(int j = 0 ; j < 4 ; j++){
                    if(tour%2==1){
                        System.out.println("De " + (j+1) +"\tValeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+tour/2).getSecteur()));
                    }else{
                        System.out.println("De " + (j+1) +"\tValeur : " + roue.des.get(j).getValeur() + ", Couleur : " + couleurSalle(roue.salles.get(j+tour/2+4).getSecteur()));
                    }
                }
                /* --------J'ai arrêté ma relecture ici--------- */
                Scanner myObj = new Scanner(System.in);
                System.out.println("Entrez le numéro du dé que vous souhaitez sélectionner\n");
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
                    if(incorrect){
                        System.out.println("Vous ne pouvez pas selectionner ce de\nEntrez le numero du de que vous souhaitez selectionner");
                        numero = myObj.nextInt();
                    }

                }while(incorrect);

                int numeroSalle;
                if(roue.numeroTour%2==1){
                    numeroSalle = numero;
                }else{
                    numeroSalle = numero + 4;
                }
                int couleurInitial = roue.salles.get(numeroSalle).getSecteur();
                int couleurFinal = roue.salles.get(numeroSalle).getSecteur();
                int valeurInitial = roue.des.get(numeroSalle-1).getValeur();
                int valeurFinal = roue.des.get(numero-1).getValeur();
                boolean actionEffectuee = false;
                do{
                    System.out.println("Votre de : \tValeur : " + valeurFinal + ", Couleur : " + couleurFinal + "\n");
                    System.out.println("Choisissez ce que vous souhaitez faire :\n");
                    System.out.println("\t1 - Modifier la valeur du de\n" +
                            "\t2 - Modifier la couleur de la salle\n" +
                            "\t3 - Gagner des ressources\n" +
                            "\t4 - Faire une action de prestige/construire un batiment de fonction\n");
                    int choix = myObj.nextInt();
                    int ressourceDisponible = 0;
                    switch(choix){
                        case 1:
                            ressourceDisponible = f.secteurs.getFirst().ressources-f.secteurs.getFirst().ressourcesUtilisees;
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
                                    if (valeurFinal + choix > 6 || valeurFinal - choix < 1) {
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
                                                }
                                                break;
                                            case '-':
                                                temp = valeurFinal - choix;
                                                if (Math.abs(temp - valeurInitial) > ressourceDisponible) {
                                                    System.out.println("Vous n'avez pas les ressources pour modifier la valeur du de\n");
                                                    incorrect = true;
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
                            ressourceDisponible = f.secteurs.get(2).ressources-f.secteurs.get(2).ressourcesUtilisees;
                            if(ressourceDisponible<2){
                                System.out.println("Vous n'avez pas assez de Connaissance pour modifier la valeur du de\n");
                            }else{
                                do{
                                    incorrect = false;
                                    System.out.println("Vous avez " + ressourceDisponible + " ressource disponible pour modifier la couleur de la salle \n" +
                                            "(Couleur initial : " + couleurSalle(couleurInitial) +",\tCouleur actuelle : " + couleurSalle(couleurFinal) + "\n" +
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
                            f.secteurs.get(1).ressources += valeurFinal;
                            actionEffectuee = true;
                            break;
                        case 4:
                            Secteur secteur = f.secteurs.get(couleurFinal);
                            if(secteur.projetConcevable[valeurFinal]){
                                if(secteur.actPrestige[valeurFinal] && secteur.batFonction[valeurFinal]){
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
                                                if(secteur.actPrestige[valeurFinal]){
                                                    System.out.println("L'action a deja ete faite\n");
                                                    incorrect = true;
                                                }else{
                                                    secteur.actPrestige[valeurFinal] = true;
                                                    actionEffectuee = true;
                                                }
                                                break;
                                            case 2:
                                                if(secteur.batFonction[valeurFinal]){
                                                    System.out.println("Le batiment a deja ete construit\n");
                                                    incorrect = true;
                                                }else{
                                                    secteur.batFonction[valeurFinal] = true;
                                                    actionEffectuee = true;
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
                            break;
                        default:
                            System.out.println("Veuillez choisir un chiffre correct");
                            break;
                    }
                }while (!actionEffectuee);

                f.secteurs.getFirst().ressourcesUtilisees+=Math.abs(valeurFinal-valeurInitial);
                if(couleurInitial!=couleurFinal){
                    f.secteurs.get(3).ressourcesUtilisees+=2;
                }

                //Implémentation de l'application des effets du batiment construit manquant,à rajouter

            }else{
                System.out.println("Vous n'avez pas de ressource pour jouer, gain de ressource minimum\n");
                for(int j = 0 ; j < 3 ; j++){
                    f.secteurs.get(j).ajouterRessource(1);
                }

            }
        }
        //Manque implémentation du changement de coté de la salle avec dé noir
    }

    private String couleurSalle(int couleur){
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
}
