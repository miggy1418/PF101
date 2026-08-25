import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SurvivalGameGUI {

    // Player stats
    private static int health = 50;
    private static int food = 50;
    private static int water = 50;
    private static int energy = 50;
    private static int dayCount = 1;

    // UI Components
    private static JFrame frame;
    private static JTextArea displayArea;
    private static JLabel statsLabel;
    private static JButton restButton;
    private static JButton exploreButton;

    public static void main(String[] args) {
        // Run UI creation on the Event Dispatch Thread
        SwingUtilities.invokeLater(SurvivalGameGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // 1. Palette Definitions
        Color darkBackground  = new Color(30, 33, 36);    // Dark Charcoal
        Color panelBackground = new Color(44, 47, 51);    // Lighter Gray
        Color textPrimary     = new Color(220, 221, 222);  // Soft White
        Color accentGreen     = new Color(46, 117, 89);    // Survival Forest Green
        Color accentOrange    = new Color(194, 107, 39);   // Explorer Warm Orange

        // 2. Main Window (JFrame) Setup
        frame = new JFrame("Forest Survival Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null); // Center window on screen
        frame.getContentPane().setBackground(darkBackground);
        frame.setLayout(new BorderLayout(12, 12));

        // Add padding around window edges
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));

        // 3. Stats Bar (Top)
        statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        statsLabel.setForeground(textPrimary);
        statsLabel.setOpaque(true);
        statsLabel.setBackground(panelBackground);
        statsLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 64, 69), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        frame.add(statsLabel, BorderLayout.NORTH);

        // 4. Game Log Window (Center Text Area)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setBackground(darkBackground);
        displayArea.setForeground(textPrimary);
        displayArea.setCaretColor(textPrimary);
        displayArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 64, 69), 1));
        frame.add(scrollPane, BorderLayout.CENTER);

        // 5. Controls Panel (Bottom Buttons)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        restButton = createCustomButton("Rest (+20 Energy)", accentGreen, textPrimary);
        exploreButton = createCustomButton("Explore (-20 Energy)", accentOrange, textPrimary);

        buttonPanel.add(restButton);
        buttonPanel.add(exploreButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Action Listeners
        restButton.addActionListener(e -> performRest());
        exploreButton.addActionListener(e -> performExplore());

        // Intro message
        displayArea.setText("You got lost deep in the forest...\n"
                + "With your phone, you sent a distress call for help.\n"
                + "Before night falls, you managed to build a shelter.\n"
                + "Rescue is on the way (Estimated arrival: 7 days)...\n\n"
                + "Choose an action to start Day 1!");

        updateStatsDisplay();
        frame.setVisible(true);
    }

    // Helper method to style buttons
    private static JButton createCustomButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false); // Removes default outline box when clicked
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static void updateStatsDisplay() {
        String statsHtml = String.format(
            "<html><body style='font-family: Monospaced; font-weight: bold; font-size: 15pt;'>" +
            "<span style='color: #ffffff;'>Day: %d</span> &nbsp;|&nbsp; " +
            "<span style='color: #E06C75;'>Health: %d</span> &nbsp;|&nbsp; " +
            "<span style='color: #D19A66;'>Food: %d</span> &nbsp;|&nbsp; " +
            "<span style='color: #61AFEF;'>Water: %d</span> &nbsp;|&nbsp; " +
            "<span style='color: #98C379;'>Energy: %d</span>" +
            "</body></html>",
            dayCount, health, food, water, energy
    );

    statsLabel.setText(statsHtml);
}

    private static void performRest() {
        energy += 10;
        if (energy > 100) energy = 100;

        displayArea.setText("DAY " + dayCount + " RESULTS\n"
                + "------------------------------\n"
                + "You rested.\n"
                + "Your energy increased by 10.\n");

        endTurn();
    }

    private static void performExplore() {
        if (energy < 20) {
            displayArea.setText("DAY " + dayCount + " RESULTS\n"
                    + "------------------------------\n"
                    + "You don't have enough energy to explore!\n"
                    + "You need at least 20 energy.\n");
            return; // Turn does not pass if energy is insufficient
        }

        StringBuilder log = new StringBuilder("DAY " + dayCount + " RESULTS\n------------------------------\n");
        log.append("You explored the forest...\n");

        int find = (int) (Math.random() * 2);
        if (find == 0) {
            int foundFood = ((int) (Math.random() * 4 + 1)) * 2;
            food = Math.min(100, food + foundFood);
            log.append("You found +").append(foundFood).append(" food!\n");
        } else {
            int foundWater = ((int) (Math.random() * 4 + 1)) * 2;
            water = Math.min(100, water + foundWater);
            log.append("You found +").append(foundWater).append(" water!\n");
        }

        energy -= 20;
        log.append("Exploring used -20 energy.\n");
        displayArea.setText(log.toString());

        endTurn();
    }

    private static void endTurn() {
        // Daily depletion
        food -= 10;
        water -= 10;

        if (food < 0) food = 0;
        if (water < 0) water = 0;

        // Health penalties
        if (food == 0 && water == 0) {
            health -= 20;
            displayArea.append("\nYou have no food AND no water! (Health -20)");
        } else if (food == 0) {
            health -= 10;
            displayArea.append("\nYou have no food! (Health -10)");
        } else if (water == 0) {
            health -= 10;
            displayArea.append("\nYou have no water! (Health -10)");
        }

        dayCount++;

        // Win / Loss Condition Checks
        if (health <= 0) {
            displayArea.append("\n\n==============================\n"
                    + "          GAME OVER\n"
                    + "==============================\n"
                    + "You died in the forest...");
            endGame();
        } else if (dayCount == 8) {
            displayArea.append("\n\n==============================\n"
                    + "           YOU WIN!\n"
                    + "==============================\n"
                    + "Rescue has arrived! You survived 7 days!");
            endGame();
        }

        updateStatsDisplay();
    }

    private static void endGame() {
        restButton.setEnabled(false);
        exploreButton.setEnabled(false);
    }
}