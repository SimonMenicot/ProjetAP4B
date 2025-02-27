package main.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoueDesSemestres {
    protected int numeroTour;
    protected List<Salle> salles;
    protected List<De> des;

    public RoueDesSemestres() {
        this.numeroTour = 0;
        this.salles = new ArrayList<>();; //Initialiser les 9 salles
        this.des = new ArrayList<>();; //Initialiser les 4 dés

        // Initialisation des salles
        this.salles.add(new Salle(0,0));
        this.salles.add(new Salle(0,2));
        this.salles.add(new Salle(0,1));
        this.salles.add(new Salle(2,0));
        this.salles.add(new Salle(2,2));
        this.salles.add(new Salle(1,0));
        this.salles.add(new Salle(1,2));
        this.salles.add(new Salle(1,2));
        this.salles.add(new Salle(1,1));
        Collections.shuffle(this.salles);

        // Ici on va aléatoirement décider pour chaque salle si on la retourne ou non
        Random rand = new Random();
        for (Salle instance_salle : this.salles){
            int rand_retourner = rand.nextInt(2);
            if (rand_retourner == 1){
                instance_salle.retourner();
                // les prints en dessous servent pour le debug si besoin de verifier quelle salle est retournée
                //System.out.println("Verso : " + instance_salle.getSecteur());
            } else {
                // System.out.println("Recto : " + instance_salle.getSecteur());
            }
        }

        // Initialisation des dés
        for (int i = 1; i < 3 + 1; i++) {
            this.des.add(new De(true));
        }
        // Dé noir
        this.des.add(new De(false));
    }

    public void nouveauTour(){
        if (numeroTour != 0) {
            //Permet de retourner la salle avec le dé noir
            int place = 0;
            for (int i = 0; i < 4; i++) {
                if (des.get(i).isTransparent()) {
                    place = i;
                }
            }

            //Change l'ordre des salles
            if (numeroTour % 2 == 1) {
                salles.get(place + 1).retourner();
            } else {
                salles.get(place + 5).retourner();
            }
            salles.addLast(salles.getFirst());
            salles.remove(salles.getFirst());
        }

        if (numeroTour < 16) {
            numeroTour++;
        }
        for(int i = 0; i < 4; i++){
            this.des.get(i).lancer();
        }
        ordonnerDes();

    }

    private void ordonnerDes() {
        List<De> tmp = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            De min = this.des.getFirst();
            for (De instance_de : this.des){
                if (min.getValeur() > instance_de.getValeur()){
                    min = instance_de;
                } else if (instance_de.getValeur() == min.getValeur() && !instance_de.isTransparent()){
                    min = instance_de;
                }
            }
            tmp.add(min);
            this.des.remove(min);
            //System.out.println("De : " + min.getValeur());
        }
        this.des.addAll(tmp);
    }

    public int getTour(){
        return numeroTour;
    }

    public int getSecteur(De de){
        int indexSalle = 0;
        if(numeroTour%2 == 1){
            indexSalle = des.indexOf(de) + 1;
        }else{
            indexSalle = des.indexOf(de) + 5;
        }
        /// La rotation de la roue est effectué à la fin du tour de jeux
        //int indexSalle = (des.indexOf(de)+(numeroTour-1)/2)%9;
        //(numeroTour-1)/2 pour avoir la rotation de la roue en fonction du tour, %9 pour retourner au bon index lors du dépassement
        return salles.get(indexSalle).getSecteur();
    }

    public De getDe(int n){
        return des.get(n);
    }

    public Salle getSalle(int i){
        return salles.get(i);
    }
}
