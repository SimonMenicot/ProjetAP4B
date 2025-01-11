package main.java.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicalInterface implements UI {
    private BufferedImage image;
    private JFrame frame;
    private JLabel selectedDieInfo;
    private JLabel playerNameLabel;

    public GraphicalInterface(String playerName) {
        // Création de la fenêtre principale
        frame = new JFrame("Interface de Jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);
        frame.setLayout(new BorderLayout());

        // Texte en haut avec le nom du joueur
        playerNameLabel = new JLabel("Feuille de " + playerName, JLabel.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(playerNameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 sections horizontales

        for (int i = 0; i < 3; i++) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BorderLayout());

            // Panneau pour les 3 premières lignes de 6 cases
            JPanel topPanel = new JPanel(new GridLayout(3, 6, 140, 10)); // Espacement de 10px entre les cases
            Color sectionColor = (i == 0) ? new Color(255, 69, 0) : (i == 1) ? Color.BLUE : Color.WHITE;
            topPanel.setBorder(BorderFactory.createLineBorder(sectionColor, 2));
            topPanel.setBackground(sectionColor); // Couleur de fond pour les 3 premières sections

            // Remplir les 3 premières lignes de 6 cases
            for (int j = 0; j < 18; j++) {
                JLabel cellLabel = new JLabel("", JLabel.CENTER);
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellLabel.setOpaque(true);
                cellLabel.setBackground(Color.LIGHT_GRAY);
                cellLabel.setPreferredSize(new Dimension(40, 40)); // Taille ajustée
                topPanel.add(cellLabel);
            }

            // Panneau pour la 4ème ligne de 18 cases
            JPanel bottomPanel = new JPanel(new GridLayout(1, 18, 10, 10)); // Espacement de 10px entre les cases
            bottomPanel.setBorder(BorderFactory.createLineBorder(sectionColor, 2));
            bottomPanel.setBackground(sectionColor); // Couleur de fond pour la ligne de 18 cases
            for (int j = 0; j < 18; j++) {
                JLabel cellLabel = new JLabel("", JLabel.CENTER);
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellLabel.setOpaque(true);
                cellLabel.setBackground(Color.LIGHT_GRAY);
                cellLabel.setPreferredSize(new Dimension(40, 40)); // Taille ajustée
                bottomPanel.add(cellLabel);
            }

            sectionPanel.add(topPanel, BorderLayout.NORTH);
            sectionPanel.add(bottomPanel, BorderLayout.SOUTH);
            centerPanel.add(sectionPanel);
        }

        // Quatrième section (3 lignes de 20 cases)
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());

        // Panneau pour les 3 lignes de 20 cases
        JPanel topPanel4 = new JPanel(new GridLayout(3, 20, 10, 10)); // Espacement de 10px entre les cases
        topPanel4.setBorder(BorderFactory.createLineBorder(Color.decode("#00008B"), 2)); // Bleu foncé autour de la dernière section

        for (int j = 0; j < 60; j++) {
            JLabel cellLabel = new JLabel("", JLabel.CENTER);
            cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cellLabel.setOpaque(true);
            cellLabel.setBackground(Color.LIGHT_GRAY);
            cellLabel.setPreferredSize(new Dimension(30, 15)); // Hauteur réduite à 15 pour la dernière section
            topPanel4.add(cellLabel);
        }
        sectionPanel.add(topPanel4, BorderLayout.CENTER);

        // Ajouter un espacement entre la quatrième section et les autres
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0)); // Espacement de 10 pixels en haut et en bas

        // Ajouter la quatrième section à la fenêtre
        centerPanel.add(sectionPanel);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Zone texte à gauche pour indiquer la position des dés sur la roue des semestres
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JTextArea dicePositionArea = new JTextArea();
        dicePositionArea.setEditable(false);
        dicePositionArea.setText("Position des dés sur la roue des semestres:\nDé 1 : \nDé 2 : \nDé 3 : \nDé 4 : ");
        dicePositionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        leftPanel.add(new JScrollPane(dicePositionArea), BorderLayout.NORTH);

        // Zone sélection du dé sous la zone de texte
        JPanel diceSelectionPanel = new JPanel();
        diceSelectionPanel.setLayout(new GridLayout(2, 2, 5, 5));
        diceSelectionPanel.setBorder(BorderFactory.createTitledBorder("Sélection du dé"));

        for (int i = 1; i <= 4; i++) {
            JButton diceButton = new JButton("Dé " + i);
            final int dieIndex = i;
            diceButton.addActionListener(e -> {
                // Utiliser HTML pour le texte du JLabel
                selectedDieInfo.setText("<html>Dé " + dieIndex + " sélectionné:<br>Valeur = X <br>Couleur = Y</html>");
            });
            diceSelectionPanel.add(diceButton);
        }

        leftPanel.add(diceSelectionPanel, BorderLayout.CENTER);

        // Nouvelle section pour afficher des informations, avec de l'espacement en dessous
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText("Informations du jeu:\n");
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 300, 0)); // Espacement en dessous

        leftPanel.add(infoPanel, BorderLayout.SOUTH);  // Ajout dans le bas du panneau gauche

        frame.add(leftPanel, BorderLayout.WEST);

        // Panneau pour les boutons à droite de l'image
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Zone affichage des informations du dé sélectionné (au centre de la colonne droite)
        selectedDieInfo = new JLabel("Aucun dé sélectionné", JLabel.CENTER);
        selectedDieInfo.setBorder(BorderFactory.createTitledBorder("Informations du dé"));
        rightPanel.add(selectedDieInfo, BorderLayout.NORTH);

        // Zone modification du dé
        JPanel modifyDiePanel = new JPanel();
        modifyDiePanel.setLayout(new GridLayout(1, 2));
        modifyDiePanel.setBorder(BorderFactory.createTitledBorder("Modification du dé"));

        // Sous-zone pour changer la valeur
        JPanel valuePanel = new JPanel(new GridLayout(2, 1));
        JButton increaseValueButton = new JButton("+1");
        JButton decreaseValueButton = new JButton("-1");
        valuePanel.add(increaseValueButton);
        valuePanel.add(decreaseValueButton);

        modifyDiePanel.add(valuePanel);

        // Sous-zone pour changer la couleur
        JPanel colorPanel = new JPanel(new GridLayout(3, 1));
        JButton whiteButton = new JButton("Blanc");
        JButton blueButton = new JButton("Bleu");
        JButton greenButton = new JButton("Vert");
        colorPanel.add(whiteButton);
        colorPanel.add(blueButton);
        colorPanel.add(greenButton);

        modifyDiePanel.add(colorPanel);

        rightPanel.add(modifyDiePanel, BorderLayout.CENTER);

        // Zone choix action
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
    }

    private void loadImage(String imagePath, JLabel backgroundLabel) {
        try {
            // Charge l'image à partir du classpath
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphicalInterface("Joueur 1"));
    }
}
