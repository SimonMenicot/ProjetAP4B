import javax.swing.*;
import java.awt.*;

public class GraphicalInterface {

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

        // Image de fond au centre
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("path/to/your/background/image.jpg")); // Remplacer par le chemin de votre image
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Zone pour valider ou annuler le tour en dessous de l'image
        JPanel validateCancelPanel = new JPanel(new FlowLayout());
        JButton validateTurnButton = new JButton("Valider le tour");
        JButton cancelButton = new JButton("Annuler");
        validateCancelPanel.add(validateTurnButton);
        validateCancelPanel.add(cancelButton);

        centerPanel.add(validateCancelPanel, BorderLayout.SOUTH);
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
        diceSelectionPanel.setLayout(new GridLayout(4, 1, 5, 5));
        diceSelectionPanel.setBorder(BorderFactory.createTitledBorder("Sélection du dé"));

        for (int i = 1; i <= 4; i++) {
            JButton diceButton = new JButton("Dé " + i);
            final int dieIndex = i;
            diceButton.addActionListener(e -> {
                selectedDieInfo.setText("Dé " + dieIndex + " sélectionné: Valeur = X, Couleur = Y"); // Remplacer X et Y par les valeurs réelles
            });
            diceSelectionPanel.add(diceButton);
        }

        leftPanel.add(diceSelectionPanel, BorderLayout.CENTER);
        frame.add(leftPanel, BorderLayout.WEST);

        // Panneau pour les boutons à droite de l'image
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Zone affichage des informations du dé sélectionné
        selectedDieInfo = new JLabel("Aucun dé sélectionné", JLabel.CENTER);
        selectedDieInfo.setBorder(BorderFactory.createTitledBorder("Informations du dé"));
        rightPanel.add(selectedDieInfo);

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

        rightPanel.add(modifyDiePanel);

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

        rightPanel.add(actionPanel);

        frame.add(rightPanel, BorderLayout.EAST);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphicalInterface("Joueur 1"));
    }
}