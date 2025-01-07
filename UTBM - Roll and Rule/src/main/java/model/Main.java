package main.java.model;

import main.java.controller.JeuController;


public class Main {
    public static void main(String[] args) {
        JeuController controller = new JeuController();
        controller.lancerPartie();
        while (controller.relance() == true) {
            controller.lancerPartie();
        }
    }
}
