package main.java.controller;

import main.java.model.PlateauDeJeu;

import java.util.Scanner;

public class JeuController {

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
        PlateauDeJeu plateau = new PlateauDeJeu(n_players);
    }

    public boolean relance(){

        return false;
    }
}