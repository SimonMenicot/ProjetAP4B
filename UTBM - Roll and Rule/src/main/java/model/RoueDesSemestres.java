package main.java.model;
import java.util.ArrayList;
import java.util.List;

public class RoueDesSemestres {
    protected int numeroTour;
    private List<Salle> salles;
    private List<De> des;

    public RoueDesSemestres() {
        this.numeroTour = 0;
        this.salles = new ArrayList<>();; //Initialiser les 9 salles
        this.des = new ArrayList<>();; //Initialiser les 4 dés
        //Il faut rajouter ici l'initialisation des objets précédents !
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