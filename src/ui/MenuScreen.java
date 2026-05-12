package ui;

import javax.swing.*;
import java.awt.*;
import logic.TypingTestManager;

public class MenuScreen extends JFrame {
    private String username;
    private TypingTestManager testManager;

    public MenuScreen(String username) {
        this.username = username;
        this.testManager = new TypingTestManager();
        initUI();
    }

    private void initUI() {
        setTitle("Typing Speed Test - Menu");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "! 👋");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(70, 130, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        // Stats preview
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 200)),
            "Your Statistics"
        ));

        int totalTests = testManager.getTotalTests(username);
        double avgWPM = testManager.getAverageWPM(username);
        double bestWPM = testManager.getBestWPM(username);
        double avgAccuracy = testManager.getAverageAccuracy(username);

        statsPanel.add(createStatCard("Total Tests", String.valueOf(totalTests)));
        statsPanel.add(createStatCard("Avg WPM", String.format("%.1f", avgWPM)));
        statsPanel.add(createStatCard("Best WPM", String.format("%.1f", bestWPM)));
        statsPanel.add(createStatCard("Avg Accuracy", String.format("%.1f%%", avgAccuracy)));

        gbc.gridy = 1;
        mainPanel.add(statsPanel, gbc);

        // Menu buttons
        JButton startTestBtn = createMenuButton("Start New Test", new Color(70, 130, 200));
        JButton viewAnalyticsBtn = createMenuButton("View Analytics", new Color(60, 179, 113));
        JButton logoutBtn = createMenuButton("Logout", new Color(220, 20, 60));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        mainPanel.add(startTestBtn, gbc);
        gbc.gridx = 1;
        mainPanel.add(viewAnalyticsBtn, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(logoutBtn, gbc);

        // Button actions
        startTestBtn.addActionListener(e -> {
            new TypingScreen(username);
            dispose();
        });

        viewAnalyticsBtn.addActionListener(e -> {
            new GraphScreen(username);
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginScreen();
                dispose();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(new Color(70, 130, 200));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }
}