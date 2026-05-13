package ui;

import javax.swing.*;
import java.awt.*;
import model.TestResult;

public class ResultScreen extends JFrame {
    private String username;
    private TestResult result;

    public ResultScreen(String username, TestResult result) {
        this.username = username;
        this.result = result;
        initUI();
    }

    private void initUI() {
        setTitle("Test Results - " + username);
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Test Results");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(70, 130, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Results Panel
        JPanel resultsPanel = new JPanel(new GridLayout(7, 2, 15, 15));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        addResultRow(resultsPanel, "WPM:", String.format("%.1f", result.getWpm()));
        addResultRow(resultsPanel, "Accuracy:", String.format("%.1f%%", result.getAccuracy()));
        addResultRow(resultsPanel, "Mistakes:", String.valueOf(result.getMistakes()));
        addResultRow(resultsPanel, "Time:", result.getTimeInSeconds() + " seconds");
        addResultRow(resultsPanel, "Difficulty:", result.getDifficulty().toString());
        addResultRow(resultsPanel, "Final Score:", String.format("%.1f", result.getFinalScore()));

        // Performance message
        String performanceMsg = getPerformanceMessage();
        JLabel performanceLabel = new JLabel(performanceMsg);
        performanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        performanceLabel.setForeground(getPerformanceColor());
        performanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(resultsPanel, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(performanceLabel, gbc);

        // Buttons
        JButton newTestBtn = createResultButton("New Test", new Color(60, 179, 113));
        JButton menuBtn = createResultButton("Main Menu", new Color(70, 130, 200));
        JButton exitBtn = createResultButton("Exit", new Color(220, 20, 60));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(newTestBtn);
        buttonPanel.add(menuBtn);
        buttonPanel.add(exitBtn);

        gbc.gridy = 4;
        mainPanel.add(buttonPanel, gbc);

        newTestBtn.addActionListener(e -> {
            new TypingScreen(username);
            dispose();
        });

        menuBtn.addActionListener(e -> {
            new MenuScreen(username);
            dispose();
        });

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        add(mainPanel);
        setVisible(true);
    }

    private void addResultRow(JPanel panel, String label, String value) {
        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        valueLabel.setForeground(new Color(70, 130, 200));
        
        panel.add(labelLabel);
        panel.add(valueLabel);
    }

    private String getPerformanceMessage() {
        double score = result.getFinalScore();
        double wpm = result.getWpm();
        
        if (score >= 80 && wpm >= 60) {
            return "Excellent! You're a typing master!";
        } else if (score >= 70 && wpm >= 45) {
            return "Great job! Keep practicing!";
        } else if (score >= 60 && wpm >= 35) {
            return "Good effort! You can improve!";
        } else if (score >= 50) {
            return "Not bad! Practice more to get better!";
        } else {
            return "Keep practicing! Focus on accuracy first!";
        }
    }

    private Color getPerformanceColor() {
        double score = result.getFinalScore();
        if (score >= 80) return new Color(60, 179, 113);  // Green
        if (score >= 60) return new Color(70, 130, 200);  // Blue
        if (score >= 40) return new Color(255, 140, 0);   // Orange
        return new Color(220, 20, 60);                    // Red
    }

    private JButton createResultButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
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