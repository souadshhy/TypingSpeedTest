package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import model.TestResult;
import logic.TypingTestManager;

public class GraphScreen extends JFrame {
    private String username;
    private TypingTestManager testManager;
    private ArrayList<TestResult> history;

    public GraphScreen(String username) {
        this.username = username;
        this.testManager = new TypingTestManager();
        this.history = testManager.getUserHistory(username);
        initUI();
    }

    private void initUI() {
        setTitle("Analytics - " + username);
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));

        // Title
        JLabel titleLabel = new JLabel("Your Performance Analytics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Stats Panel
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.CENTER);

        // History Panel
        JPanel historyPanel = createHistoryPanel();
        add(historyPanel, BorderLayout.SOUTH);

        // Add padding around everything
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        setVisible(true);
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        double avgWPM = testManager.getAverageWPM(username);
        double bestWPM = testManager.getBestWPM(username);
        double avgAccuracy = testManager.getAverageAccuracy(username);
        int totalTests = testManager.getTotalTests(username);

        panel.add(createStatCard("Average WPM", String.format("%.1f", avgWPM), 
                                 getPerformanceColor(avgWPM, 50, 70)));
        panel.add(createStatCard("Best WPM", String.format("%.1f", bestWPM), 
                                 getPerformanceColor(bestWPM, 60, 80)));
        panel.add(createStatCard("Average Accuracy", String.format("%.1f%%", avgAccuracy), 
                                 getPerformanceColor(avgAccuracy, 70, 85)));
        panel.add(createStatCard("Total Tests", String.valueOf(totalTests), 
                                 new Color(70, 130, 200)));

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    private Color getPerformanceColor(double value, double good, double excellent) {
        if (value >= excellent) return new Color(60, 179, 113);
        if (value >= good) return new Color(70, 130, 200);
        return new Color(255, 140, 0);
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // History label
        JLabel historyLabel = new JLabel("Test History", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyLabel.setForeground(new Color(70, 130, 200));
        panel.add(historyLabel, BorderLayout.NORTH);

        String[] columns = {"#", "Test", "WPM", "Accuracy", "Mistakes", "Time", "Difficulty", "Score"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (history != null && !history.isEmpty()) {
            for (int i = 0; i < history.size(); i++) {
                TestResult result = history.get(i);
                model.addRow(new Object[]{
                    i + 1,
                    "Test " + (i + 1),
                    String.format("%.1f", result.getWpm()),
                    String.format("%.1f%%", result.getAccuracy()),
                    result.getMistakes(),
                    result.getTimeInSeconds() + "s",
                    result.getDifficulty(),
                    String.format("%.1f", result.getFinalScore())
                });
            }
        } else {
            model.addRow(new Object[]{"-", "No tests completed yet", "-", "-", "-", "-", "-", "-"});
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(70, 130, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(100, 30));
        
        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(90);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(70);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 220));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton closeBtn = new JButton("Back to Menu");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setBackground(new Color(70, 130, 200));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.setPreferredSize(new Dimension(150, 40));
        closeBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(closeBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}