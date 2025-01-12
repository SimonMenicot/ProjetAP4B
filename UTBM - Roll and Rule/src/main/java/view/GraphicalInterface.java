package main.java.view;

import main.java.model.FeuilleDeJoueur;
import main.java.model.PlateauDeJeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.FeuilleDeJoueur;

public class GraphicalInterface implements UI {
    private BufferedImage image;
    private JFrame frame;
    private JLabel selectedDieInfo;
    private JLabel playerNameLabel;
    private JTextArea infoArea;
    private JTextArea dicePositionArea;
    private JPanel topPanel4;
    private JLabel[][] cellsSection4;
    private JPanel[] lPanelsAct;
    private JPanel[] lPanelsRess;


    public GraphicalInterface() {
        lPanelsAct = new JPanel[3];
        lPanelsRess = new JPanel[3];

        // Création de la fenêtre principale
        frame = new JFrame("Interface de Jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);
        frame.setLayout(new BorderLayout());

        // Afficher la fenêtre
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Mettre la fenêtre en plein écran

    }

    public void affichageNomJoueur(FeuilleDeJoueur f) {
        frame.getContentPane().removeAll();

        // Texte en haut avec le nom du joueur
        playerNameLabel = new JLabel("Feuille de " + f.getName(), JLabel.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(playerNameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 30)); // 4 sections horizontales

        for (int i = 0; i < 3; i++) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BorderLayout(10, 10));

            if (i == 0) {
                sectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }

            String sectionTitle;
            switch (i) {
                case 0:
                    sectionTitle = "Secteur du personnel";
                    break;
                case 1:
                    sectionTitle = "Secteur des étudiants";
                    break;
                case 2:
                default:
                    sectionTitle = "Secteur des enseignants-chercheurs";
                    break;
            }

            JLabel titleLabel = new JLabel(sectionTitle, JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            sectionPanel.add(titleLabel, BorderLayout.NORTH);

            // Panneau pour les 3 premières lignes de 11 cases
            JPanel topPanel = new JPanel(new GridLayout(3, 11, 50, 10));
            Color sectionColor = (i == 0) ? new Color(255, 69, 0) : (i == 1) ? Color.BLUE : Color.WHITE;
            topPanel.setBorder(BorderFactory.createLineBorder(sectionColor, 2));
            topPanel.setBackground(sectionColor); // Couleur de fond pour les 3 premières sections
            lPanelsAct[i] = topPanel;

            // Remplir les 3 premières lignes
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 11; col++) {
                    if (col % 2 != 0) {
                        // Positions paires carré vert bonus
                        if ((i == 0 && row == 1 && (col == 1 || col == 5 || col == 9)) ||
                                (i == 0 && row == 2 && (col == 1 || col == 9)) ||
                                (i == 1 && row == 2 && (col == 1 || col == 5)) ||
                                (i == 2 && row == 2 && (col == 5 || col == 9))) {
                            JLabel squareLabel = new JLabel("", JLabel.CENTER);
                            squareLabel.setOpaque(true);
                            squareLabel.setBackground(Color.GREEN);
                            squareLabel.setPreferredSize(new Dimension(40, 40));
                            topPanel.add(squareLabel);
                        } else {
                            topPanel.add(new JLabel()); // Case vide
                        }
                    } else {
                        boolean valeursSecteur;
                        if (row == 1) {
                            valeursSecteur = f.getSecteur(i).isDonePrestige(col/2);
                        }
                        else if (row == 2) {
                            valeursSecteur = f.getSecteur(i).isDoneFonction(col/2);
                        }
                        else {
                            valeursSecteur = false;
                        }
                        // Positions impaires : Carrés normaux
                        JLabel cellLabel = new JLabel("", JLabel.CENTER);
                        cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        cellLabel.setOpaque(true);
                        if (valeursSecteur) {
                            cellLabel.setBackground(Color.GREEN);
                            cellLabel.setForeground(Color.BLACK);
                        }
                        else if (row == 0){
                            int text = col/2;
                            cellLabel.setText(String.valueOf(text+1));
                            cellLabel.setForeground(Color.BLACK);
                            cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            cellLabel.setVerticalAlignment(SwingConstants.CENTER);
                        }
                        else{
                            cellLabel.setBackground(Color.LIGHT_GRAY);
                        }
                        cellLabel.setPreferredSize(new Dimension(40, 40));
                        topPanel.add(cellLabel);
                    }
                }
            }

            // Panneau pour la 4ème ligne de 18 cases
            JPanel bottomPanel = new JPanel(new GridLayout(1, 18, 10, 10));
            lPanelsRess[i] = bottomPanel;
            bottomPanel.setBorder(BorderFactory.createLineBorder(sectionColor, 2));
            bottomPanel.setBackground(sectionColor); // Couleur de fond pour la ligne de 18 cases

            int valeursRess;
            valeursRess = f.getSecteur(i).getRessources();
            for (int j = 0; j < 18; j++) {
                JLabel cellLabel = new JLabel("", JLabel.CENTER);
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellLabel.setOpaque(true);
                if (valeursRess > j){
                    cellLabel.setBackground(Color.GREEN);
                }
                else if (j == 5 || j == 11 || j == 17){
                    cellLabel.setBackground(sectionColor);
                }
                else{
                    cellLabel.setBackground(Color.LIGHT_GRAY);
                }
                cellLabel.setPreferredSize(new Dimension(40, 40));
                bottomPanel.add(cellLabel);
            }

            sectionPanel.add(topPanel, BorderLayout.NORTH);
            sectionPanel.add(bottomPanel, BorderLayout.SOUTH);


            centerPanel.add(sectionPanel);
        }

        // Quatrième section (3 lignes de 20 cases)
        JPanel sectionPanel4 = new JPanel();
        sectionPanel4.setLayout(new BorderLayout());

        JLabel titleLabel4 = new JLabel("Piste des membres", JLabel.CENTER);
        titleLabel4.setFont(new Font("Arial", Font.BOLD, 18));
        sectionPanel4.add(titleLabel4, BorderLayout.NORTH);

        // Panneau pour les 3 lignes de 20 cases
        topPanel4 = new JPanel(new GridLayout(3, 20, 10, 10));
        topPanel4.setBorder(BorderFactory.createLineBorder(Color.decode("#00008B"), 2)); // Bleu foncé
        cellsSection4 = new JLabel[3][20];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 20; col++) {
                cellsSection4[row][col] = new JLabel("", JLabel.CENTER);
                cellsSection4[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cellsSection4[row][col].setOpaque(true);
                if (col == 2 || col == 5 || col == 10 || col == 14 || col == 19){
                    cellsSection4[row][col].setBackground(Color.GREEN);
                }
                else{
                    cellsSection4[row][col].setBackground(Color.LIGHT_GRAY);
                }
                cellsSection4[row][col].setPreferredSize(new Dimension(30, 15));
                topPanel4.add(cellsSection4[row][col]);
            }
        }
        sectionPanel4.add(topPanel4, BorderLayout.CENTER);

        // Ajouter un espacement entre la quatrième section et les autres
        sectionPanel4.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        // Ajouter la quatrième section à la fenêtre
        centerPanel.add(sectionPanel4);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Zone de texte à gauche pour indiquer la position des dés sur la roue des semestres
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        dicePositionArea = new JTextArea();
        dicePositionArea.setEditable(false);
        dicePositionArea.setText("Position des dés sur la roue des semestres:\nDé 1 : \nDé 2 : \nDé 3 : \nDé 4 : ");
        dicePositionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        leftPanel.add(new JScrollPane(dicePositionArea), BorderLayout.NORTH);

        // Zone de sélection du dé sous la zone de texte
        JPanel diceSelectionPanel = new JPanel();
        diceSelectionPanel.setLayout(new GridLayout(2, 2, 5, 5));
        diceSelectionPanel.setBorder(BorderFactory.createTitledBorder("Sélection du dé"));

        for (int i = 1; i <= 4; i++) {
            JButton diceButton = new JButton("Dé " + i);
            final int dieIndex = i;
            diceButton.addActionListener(e -> {
                selectedDieInfo.setText("<html>Dé " + dieIndex + " sélectionné:<br>Valeur = X <br>Couleur = Y</html>");
            });
            diceSelectionPanel.add(diceButton);
        }

        leftPanel.add(diceSelectionPanel, BorderLayout.CENTER);

        // Nouvelle section pour afficher les informations du jeu, avec de l'espacement en dessous
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText("Informations du jeu:\n");
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 300, 0));

        leftPanel.add(infoPanel, BorderLayout.SOUTH);  // Ajout dans le bas du panneau gauche
        frame.add(leftPanel, BorderLayout.WEST);

        // Panneau pour les boutons à droite de l'image
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Zone d'affichage des informations du dé sélectionné (au centre de la colonne droite)
        selectedDieInfo = new JLabel("Aucun dé sélectionné", JLabel.CENTER);
        selectedDieInfo.setBorder(BorderFactory.createTitledBorder("Informations du dé"));
        rightPanel.add(selectedDieInfo, BorderLayout.NORTH);

        // Zone de modification du dé
        JPanel modifyDiePanel = new JPanel();
        modifyDiePanel.setLayout(new GridLayout(1, 2));
        modifyDiePanel.setBorder(BorderFactory.createTitledBorder("Modification du dé"));

        // Sous-zone pour changer la valeur du dé
        JPanel valuePanel = new JPanel(new GridLayout(2, 1));
        JButton increaseValueButton = new JButton("+1");
        JButton decreaseValueButton = new JButton("-1");
        valuePanel.add(increaseValueButton);
        valuePanel.add(decreaseValueButton);

        modifyDiePanel.add(valuePanel);

        // Sous-zone pour changer la couleur du dé
        JPanel colorPanel = new JPanel(new GridLayout(3, 1));
        JButton whiteButton = new JButton("Blanc");
        JButton blueButton = new JButton("Bleu");
        JButton greenButton = new JButton("Vert");
        colorPanel.add(whiteButton);
        colorPanel.add(blueButton);
        colorPanel.add(greenButton);

        modifyDiePanel.add(colorPanel);

        rightPanel.add(modifyDiePanel, BorderLayout.CENTER);

        // Zone de choix d'action
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(3, 1, 5, 5));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Choix action"));

        JButton gatherResourcesButton = new JButton("Récupérer des ressources");
        JButton buildFunctionButton = new JButton("Construire un bâtiment de fonction");
        JButton prestigeActionButton = new JButton("Réaliser une action de prestige");

        actionPanel.add(gatherResourcesButton);
        actionPanel.add(buildFunctionButton);
        actionPanel.add(prestigeActionButton);

        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        // Ajouter de l'espacement sous la zone "Choix action"
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 0)); // Espacement de 10px en dessous

        frame.add(rightPanel, BorderLayout.EAST);

        // Afficher la fenêtre
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Mettre la fenêtre en plein écran
        this.remplirFeuilleJoueur(f);

    }

    public void remplirFeuilleJoueur(FeuilleDeJoueur feuille) {
        // Partie Secteurs
        for (int i = 0; i < 3; i++){
            int valeurRessource = feuille.getSecteur(i).getRessourcesUtilisees();
            Component[] resourceComponents = lPanelsRess[i].getComponents();
            for (int k = 0; k < valeurRessource; k++) {
                if (k < resourceComponents.length && resourceComponents[k] instanceof JLabel) {
                    JLabel cell = (JLabel) resourceComponents[k];
                    cell.setText("X");
                    cell.setForeground(Color.BLACK);
                    cell.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setVerticalAlignment(SwingConstants.BOTTOM);
                }
            }
        }

        // Partie Piste des membres
        int[] valeursMembres = {feuille.getNbPersonnels(), feuille.getNbEtudiants(), feuille.getNbEnseignants()};

        Component[] components = topPanel4.getComponents();
        for (int row = 0; row < 3; row++) {
            int nbCroix = valeursMembres[row];
            for (int col = 0; col < nbCroix; col++) {
                int index = row * 20 + col;
                if (index < components.length && components[index] instanceof JLabel) {
                    JLabel cell = (JLabel) components[index];
                    cell.setText("X");
                    cell.setForeground(Color.RED);
                }
            }
        }

        //Partie Destruction
        for (int i = 0; i < 3; i++){
            Component[] resourceComponents = lPanelsAct[i].getComponents();
            for (int j = 1; j < 3; j++) {
                for (int col = 0; col < 11; col++){
                    int index = j * 11 + col;
                    if (col % 2 == 0) {
                        boolean destructionFlags = feuille.getSecteur(i).isConcevable(col/2);
                        if (index < resourceComponents.length && resourceComponents[index] != null && resourceComponents[index] instanceof JLabel) {
                            JLabel cellLabel = (JLabel) resourceComponents[index];
                            if (!destructionFlags && !cellLabel.getBackground().equals(Color.GREEN)) {
                                cellLabel.setBackground(Color.RED);
                                System.out.println(cellLabel.getBackground());
                            }
                        }
                    } else {
                        System.out.println("Index out of bounds: " + index + "  ressourceComponent.lenght : " + resourceComponents.length);
                    }
                }
            }

        }


        frame.revalidate();
        frame.repaint();
    }

    private void loadImage(String imagePath, JLabel backgroundLabel) {
        try {
            image = ImageIO.read(new File(imagePath));
            if (image != null) {
                backgroundLabel.setIcon(new ImageIcon(image));
            } else {
                throw new IOException("L'image n'a pas pu être lue.");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
    }

    public void affichageDe(PlateauDeJeu plateau) {
        dicePositionArea.setText("");
        for (int j = 0; j < 4; j++) {
            String deInfo;
            if (plateau.getRoue().getTour() % 2 == 1) {
                if (plateau.getRoue().getDe(j).isTransparent()) {
                    deInfo = "De" + (j + 1) + " : " + plateau.couleurSalle(plateau.getRoue().getSalle(j + 1).getSecteur()) + ", Valeur : " +
                            plateau.getRoue().getDe(j).getValeur();
                } else {
                    deInfo = "De" + (j + 1) + " : noir, Valeur : " +
                            plateau.getRoue().getDe(j).getValeur();
                }
            } else {
                if (plateau.getRoue().getDe(j).isTransparent()) {
                    deInfo = "De" + (j + 1) + " : " + plateau.couleurSalle(plateau.getRoue().getSalle(j + 5).getSecteur()) + ", Valeur : " +
                            plateau.getRoue().getDe(j).getValeur();
                } else {
                    deInfo = "De" + (j + 1) + " : Noir, Valeur : " +
                            plateau.getRoue().getDe(j).getValeur();
                }
            }

            dicePositionArea.append(deInfo + "\n");
        }
    }

    public void affichageRessourceInsuffisante() {
        infoArea.append("Erreur : Ressources insuffisantes pour effectuer cette action.\n");
    }

    public int MenuTour(PlateauDeJeu plateau, int valeurFinal, int couleurFinal) {
        infoArea.append("C'est votre tour ! Sélectionnez une action à effectuer.\n");
        return 0;
    }

    public void affichageFondInsuffisant() {
        infoArea.append("Erreur : Fonds insuffisants pour cette action.\n");
    }

    public void affichageScore(PlateauDeJeu plateau) {
        if (plateau.getNbJoueur() == 1) {
            infoArea.append("Vous avez obtenu un score de " + plateau.getFeuillesJoueurs(0).calculScore() + " points. Félicitation !\n");
        } else {
            List<Integer> winner = new ArrayList<>();
            int best = 0;
            for (int i = 0; i < plateau.getNbJoueur(); i++) {
                infoArea.append("\n Le joueur " + (i + 1) + " a obtenu un score de " + plateau.getFeuillesJoueurs(i).calculScore() + " points\n");
                if (best == 0 || best < plateau.getFeuillesJoueurs(i).calculScore()) {
                    winner.clear();
                    best = plateau.getFeuillesJoueurs(i).calculScore();
                    winner.add(i + 1);
                } else if (best == plateau.getFeuillesJoueurs(i).calculScore()) {
                    winner.add(i + 1);
                }
            }
            if (winner.size() == 1) {
                infoArea.append(plateau.getFeuillesJoueurs(winner.getFirst()).getName() + " remporte la partie !\n");
            } else if (winner.size() > 1 && winner.size() < plateau.getNbJoueur()) {
                infoArea.append("Les joueurs suivant sont à égalité et remportent la victoire :\n");
                for (Integer integer : winner) {
                    infoArea.append(plateau.getFeuillesJoueurs(integer).getName() + " remporte la partie !\n");
                }
            } else {
                infoArea.append("Tout les joueurs sont à égalité et remportent la victoire :\n");
            }
        }
    }

    /* Fonction pour tester l'interface graphique */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicalInterface gui = new GraphicalInterface();

            FeuilleDeJoueur feuille = new FeuilleDeJoueur("Joueur Test");
            feuille.addPersonnel(5);
            feuille.addEtudiant(10);
            feuille.addEnseignant(8);
            feuille.getSecteur(0).ajouterRessource(4);
            feuille.getSecteur(1).doPrestige(2);
            feuille.getSecteur(2).utiliserRessource(2);
            feuille.getSecteur(1).coupureBudget(4);
            feuille.getSecteur(2).doPrestige(1);
            feuille.getSecteur(2).coupureBudget(1);
            feuille.getSecteur(0).doPrestige(0);
            feuille.coupureBudget(2, 0); //La colonne est bien protegée

            gui.affichageNomJoueur(feuille);

        });
    }
}