package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import model.Difficulty;
import logic.TypingTestManager;
import model.TestResult;

public class TypingScreen extends JFrame {
    private String username;
    private TypingTestManager testManager;
    private String currentText;
    private Difficulty currentDifficulty;
    
    private JTextArea textToTypeArea;
    private JTextArea userInputArea;
    private JLabel wpmLabel;
    private JLabel accuracyLabel;
    private JLabel timerLabel;
    private JLabel mistakesLabel;
    private JComboBox<Difficulty> difficultyCombo;
    private JButton startButton;
    private JButton submitButton;
    private Timer timer;
    private int timeRemaining;
    private long startTime;
    private boolean testActive;
    
    public TypingScreen(String username) {
        this.username = username;
        this.testManager = new TypingTestManager();
        initUI();
    }
    
    private void initUI() {
        setTitle("Typing Test - " + username);
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));
        
        // Top Panel
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Text to type
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel - User input
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(70, 130, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        difficultyLabel.setForeground(Color.WHITE);
        
        difficultyCombo = new JComboBox<>(Difficulty.values());
        difficultyCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        startButton = createStyledButton("Start Test", new Color(60, 179, 113));
        submitButton = createStyledButton("Submit Test", new Color(255, 140, 0));
        submitButton.setEnabled(false);
        
        wpmLabel = createInfoLabel("WPM: 0");
        accuracyLabel = createInfoLabel("Accuracy: 0%");
        mistakesLabel = createInfoLabel("Mistakes: 0");
        timerLabel = createInfoLabel("Time: --");
        
        panel.add(difficultyLabel);
        panel.add(difficultyCombo);
        panel.add(startButton);
        panel.add(submitButton);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(wpmLabel);
        panel.add(accuracyLabel);
        panel.add(mistakesLabel);
        panel.add(timerLabel);
        
        startButton.addActionListener(e -> startTest());
        submitButton.addActionListener(e -> {
            if (testActive) submitTest();
        });
        
        return panel;
    }
    
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(0, 0, 0, 0.5f));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 130, 200), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel instructionLabel = new JLabel("Type this text:", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        instructionLabel.setForeground(new Color(70, 130, 200));
        textPanel.add(instructionLabel, BorderLayout.NORTH);
        
        textToTypeArea = new JTextArea(8, 60);
        textToTypeArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textToTypeArea.setEditable(false);
        textToTypeArea.setLineWrap(true);
        textToTypeArea.setWrapStyleWord(true);
        textToTypeArea.setBackground(new Color(250, 250, 250));
        textToTypeArea.setMargin(new Insets(10, 10, 10, 10));
        textPanel.add(new JScrollPane(textToTypeArea), BorderLayout.CENTER);
        
        panel.add(textPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 130, 200), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel inputLabel = new JLabel("Your typing:", SwingConstants.CENTER);
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        inputLabel.setForeground(new Color(70, 130, 200));
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        
        userInputArea = new JTextArea(5, 60);
        userInputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        userInputArea.setLineWrap(true);
        userInputArea.setWrapStyleWord(true);
        userInputArea.setEnabled(false);
        userInputArea.setMargin(new Insets(10, 10, 10, 10));
        
        // Add key listener for real-time stats
        userInputArea.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (testActive) {
                    updateRealTimeStats();
                }
            }
        });
        
        inputPanel.add(new JScrollPane(userInputArea), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 40));
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
    
    private void startTest() {
        currentDifficulty = (Difficulty) difficultyCombo.getSelectedItem();
        currentText = testManager.generateTestText(currentDifficulty);
        textToTypeArea.setText(currentText);
        userInputArea.setText("");
        userInputArea.setEnabled(true);
        userInputArea.requestFocus();
        
        testActive = true;
        timeRemaining = getTimeForDifficulty();
        timerLabel.setText("Time: " + timeRemaining + "s");
        
        startTime = System.currentTimeMillis();
        startButton.setEnabled(false);
        difficultyCombo.setEnabled(false);
        submitButton.setEnabled(true);
        
        if (timer != null) timer.stop();
        
        timer = new Timer(1000, e -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
            
            if (timeRemaining <= 0) {
                timer.stop();
                testActive = false;
                userInputArea.setEnabled(false);
                submitTest();
            }
            
            updateRealTimeStats();
        });
        timer.start();
    }
    
    private int getTimeForDifficulty() {
        switch (currentDifficulty) {
            case EASY: return 60;
            case MEDIUM: return 90;
            case HARD: return 120;
            default: return 60;
        }
    }
    
    private void updateRealTimeStats() {
        String typed = userInputArea.getText();
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        
        if (elapsedSeconds > 0 && !typed.isEmpty()) {
            // Calculate WPM
            String[] words = typed.trim().split("\\s+");
            int wordCount = words.length;
            double minutes = elapsedSeconds / 60.0;
            int wpm = (int) (wordCount / minutes);
            wpmLabel.setText("WPM: " + wpm);
            
            // Calculate accuracy and mistakes
            int mistakes = 0;
            int minLength = Math.min(currentText.length(), typed.length());
            for (int i = 0; i < minLength; i++) {
                if (currentText.charAt(i) != typed.charAt(i)) {
                    mistakes++;
                }
            }
            mistakes += Math.abs(currentText.length() - typed.length());
            
            int correct = minLength - mistakes;
            double accuracy = minLength > 0 ? (correct * 100.0 / currentText.length()) : 0;
            if (accuracy < 0) accuracy = 0;
            
            accuracyLabel.setText(String.format("Accuracy: %.1f%%", accuracy));
            mistakesLabel.setText("Mistakes: " + mistakes);
        }
    }
    
    private void submitTest() {
        if (timer != null) timer.stop();
        testActive = false;
        userInputArea.setEnabled(false);
        
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        int actualTime = (int) Math.min(elapsedSeconds, getTimeForDifficulty());
        
        TestResult result = testManager.submitTypingTest(
            username,
            currentText,
            userInputArea.getText(),
            actualTime,
            currentDifficulty
        );
        
        new ResultScreen(username, result);
        dispose();
    }
}