package main.java.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoueDesSemestres {
    protected int numeroTour;
    private List<Salle> salles;
    private List<De> des;

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
    public void nouveauTour() {
        if (numeroTour < 16) {
            numeroTour++;
        }
    }
    public void ordonnerDes() {
        // à implémenter
    }
}
