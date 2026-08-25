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
        SwingUtilities.invokeLater(SurvivalGameGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        Color darkBackground  = new Color(30, 33, 36);
        Color panelBackground = new Color(44, 47, 51);
        Color textPrimary     = new Color(220, 221, 222);
        Color accentGreen     = new Color(46, 117, 89);
        Color accentOrange    = new Color(194, 107, 39);

        frame = new JFrame("Forest Survival Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(darkBackground);
        frame.setLayout(new BorderLayout(12, 12));

        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));

        // Stats Bar
        statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setOpaque(true);
        statsLabel.setBackground(panelBackground);
        statsLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 64, 69), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        frame.add(statsLabel, BorderLayout.NORTH);

        // Game Log
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

        // Controls
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        restButton = createCustomButton("Rest (+15 Energy)", accentGreen, textPrimary);
        exploreButton = createCustomButton("Explore (-25 Energy)", accentOrange, textPrimary);

        buttonPanel.add(restButton);
        buttonPanel.add(exploreButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        restButton.addActionListener(e -> performRest());
        exploreButton.addActionListener(e -> performExplore());

        displayArea.setText("You got lost deep in the forest...\n"
                + "With your phone, you sent a distress call for help.\n"
                + "Before night falls, you managed to build a shelter.\n"
                + "Rescue is on the way (Estimated arrival: 7 days)...\n\n"
                + "Choose an action to start Day 1!");

        updateStatsDisplay();
        frame.setVisible(true);
    }

    private static JButton createCustomButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
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
        energy += 15; // Reduced energy gain
        if (energy > 100) energy = 100;

        displayArea.setText("DAY " + dayCount + " RESULTS\n"
                + "------------------------------\n"
                + "You rested at camp.\n"
                + "Energy restored (+15).\n");

        endTurn();
    }

    private static void performExplore() {
        if (energy < 25) { // Increased energy cost requirement
            displayArea.setText("DAY " + dayCount + " RESULTS\n"
                    + "------------------------------\n"
                    + "You are too exhausted to explore!\n"
                    + "You need at least 25 energy.\n");
            return;
        }

        energy -= 25;
        StringBuilder log = new StringBuilder("DAY " + dayCount + " RESULTS\n------------------------------\n");
        log.append("You ventured into the wild (-25 Energy)...\n");

        // 30% Chance of Danger / Bad Event
        int outcome = (int) (Math.random() * 100);

        if (outcome < 30) {
            // Threat Event
            int damage = 10 + (int) (Math.random() * 11); // 10-20 DMG
            health -= damage;
            log.append("DANGER! You tripped down a ravine and took ").append(damage).append(" damage!\n");
        } else if (outcome < 65) {
            // Found Food
            int foundFood = 10 + (int) (Math.random() * 16); // 10-25 Food
            food = Math.min(100, food + foundFood);
            log.append("SUCCESS! You found +").append(foundFood).append(" food.\n");
        } else {
            // Found Water
            int foundWater = 10 + (int) (Math.random() * 16); // 10-25 Water
            water = Math.min(100, water + foundWater);
            log.append("SUCCESS! You found +").append(foundWater).append(" water.\n");
        }

        displayArea.setText(log.toString());
        endTurn();
    }

    private static void endTurn() {
        // Higher daily resource consumption
        food -= 12;
        water -= 15;

        if (food < 0) food = 0;
        if (water < 0) water = 0;

        // Health penalties for starvation/dehydration
        if (food == 0 && water == 0) {
            health -= 25;
            displayArea.append("\nCRITICAL: Starving AND Dehydrated! (Health -25)");
        } else if (food == 0) {
            health -= 12;
            displayArea.append("\nWARNING: No food left! (Health -12)");
        } else if (water == 0) {
            health -= 15;
            displayArea.append("\nWARNING: Severe dehydration! (Health -15)");
        }

        // Exhaustion penalty if energy hits zero
        if (energy <= 0) {
            health -= 10;
            displayArea.append("\nEXHAUSTION: Collapsed from lack of energy! (Health -10)");
        }

        dayCount++;

        // Win / Loss Check
        if (health <= 0) {
            health = 0;
            displayArea.append("\n\n==============================\n"
                    + "          GAME OVER\n"
                    + "==============================\n"
                    + "You succumbed to the wilderness on Day " + (dayCount - 1) + ".");
            endGame();
        } else if (dayCount == 8) {
            displayArea.append("\n\n==============================\n"
                    + "           YOU WIN!\n"
                    + "==============================\n"
                    + "Rescue helicopters spotted your camp! You survived 7 days!");
            endGame();
        }

        updateStatsDisplay();
    }

    private static void endGame() {
        restButton.setEnabled(false);
        exploreButton.setEnabled(false);
    }
}