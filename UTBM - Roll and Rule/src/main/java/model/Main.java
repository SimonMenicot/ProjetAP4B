package main.java.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        // Choix du nombre de joueurs
        Scanner myObj = new Scanner(System.in);
        System.out.println("Entrez le nombre de joueurs : ");
        String n_input = myObj.nextLine();
        System.out.println("Nombre de joueurs : " + n_input);
        int n_players = Integer.valueOf(n_input);

        // Création plateau de jeu et de la roue des semestres
        PlateauDeJeu plateau = new PlateauDeJeu(n_players);
        RoueDesSemestres roue = new RoueDesSemestres();

        // Suite
    }
}
