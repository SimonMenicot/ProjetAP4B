package main.java.model;

public class RoueDesSemestres {
    protected int numeroTour;

    private Salle[] salles;
    private De[] des;

    public RoueDesSemestres() {
        this.numeroTour = 0;

        this.salles = new Salle[9]; //Initialiser les 9 salles
        this.des = new De[4]; //Initialiser les 4 dés
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
