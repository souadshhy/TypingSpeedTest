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
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Your Performance Analytics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 248, 255));

        centerPanel.add(createStatsPanel(), BorderLayout.NORTH);
        centerPanel.add(new PerformanceGraphPanel(history), BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(createHistoryPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        double avgWPM = calculateAverageWPM();
        double bestWPM = calculateBestWPM();
        double avgAccuracy = calculateAverageAccuracy();
        int totalTests = history.size();

        panel.add(createStatCard("Average WPM", String.format("%.1f", avgWPM)));
        panel.add(createStatCard("Best WPM", String.format("%.1f", bestWPM)));
        panel.add(createStatCard("Average Accuracy", String.format("%.1f%%", avgAccuracy)));
        panel.add(createStatCard("Total Tests", String.valueOf(totalTests)));

        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(70, 130, 200));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel historyLabel = new JLabel("Test History", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyLabel.setForeground(new Color(70, 130, 200));
        panel.add(historyLabel, BorderLayout.NORTH);

        String[] columns = {"#", "WPM", "Accuracy", "Mistakes", "Time", "Difficulty", "Score"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (history != null && !history.isEmpty()) {
            for (int i = 0; i < history.size(); i++) {
                TestResult result = history.get(i);

                model.addRow(new Object[]{
                        i + 1,
                        String.format("%.1f", result.getWpm()),
                        String.format("%.1f%%", result.getAccuracy()),
                        result.getMistakes(),
                        result.getTimeInSeconds() + "s",
                        result.getDifficulty(),
                        String.format("%.1f", result.getFinalScore())
                });
            }
        } else {
            model.addRow(new Object[]{"-", "-", "-", "-", "-", "-", "-"});
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(70, 130, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 180));

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Back to Menu");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setBackground(new Color(70, 130, 200));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setPreferredSize(new Dimension(150, 40));
        closeBtn.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private double calculateAverageWPM() {
        if (history == null || history.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (TestResult result : history) {
            total += result.getWpm();
        }

        return total / history.size();
    }

    private double calculateBestWPM() {
        if (history == null || history.isEmpty()) {
            return 0;
        }

        double best = history.get(0).getWpm();

        for (TestResult result : history) {
            if (result.getWpm() > best) {
                best = result.getWpm();
            }
        }

        return best;
    }

    private double calculateAverageAccuracy() {
        if (history == null || history.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (TestResult result : history) {
            total += result.getAccuracy();
        }

        return total / history.size();
    }

    private class PerformanceGraphPanel extends JPanel {

        private ArrayList<TestResult> results;

        public PerformanceGraphPanel(ArrayList<TestResult> results) {
            this.results = results;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 200), 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            int leftPadding = 70;
            int rightPadding = 40;
            int topPadding = 50;
            int bottomPadding = 60;

            int graphWidth = width - leftPadding - rightPadding;
            int graphHeight = height - topPadding - bottomPadding;

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString("WPM and Accuracy Progress", width / 2 - 110, 25);

            if (results == null || results.isEmpty()) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                g2.drawString("No test data available yet.", width / 2 - 90, height / 2);
                return;
            }

            g2.setColor(Color.BLACK);

            // Y-axis
            g2.drawLine(leftPadding, topPadding, leftPadding, height - bottomPadding);

            // X-axis
            g2.drawLine(leftPadding, height - bottomPadding, width - rightPadding, height - bottomPadding);

            double maxWPM = 100;

            for (TestResult result : results) {
                if (result.getWpm() > maxWPM) {
                    maxWPM = result.getWpm();
                }
            }

            maxWPM += 10;

            // Y-axis labels
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            for (int i = 0; i <= 5; i++) {
                int y = height - bottomPadding - (i * graphHeight / 5);
                double value = i * maxWPM / 5;

                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(leftPadding, y, width - rightPadding, y);

                g2.setColor(Color.BLACK);
                g2.drawString(String.format("%.0f", value), 25, y + 5);
            }

            g2.drawString("Test Number", width / 2 - 35, height - 20);
            g2.drawString("WPM", 20, topPadding - 15);

            int count = results.size();

            int[] wpmX = new int[count];
            int[] wpmY = new int[count];
            int[] accX = new int[count];
            int[] accY = new int[count];

            for (int i = 0; i < count; i++) {
                TestResult result = results.get(i);

                int x;

                if (count == 1) {
                    x = leftPadding + graphWidth / 2;
                } else {
                    x = leftPadding + (i * graphWidth / (count - 1));
                }

                int wpmYPoint = height - bottomPadding
                        - (int) ((result.getWpm() / maxWPM) * graphHeight);

                int accYPoint = height - bottomPadding
                        - (int) ((result.getAccuracy() / 100.0) * graphHeight);

                wpmX[i] = x;
                wpmY[i] = wpmYPoint;

                accX[i] = x;
                accY[i] = accYPoint;

                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(i + 1), x - 4, height - bottomPadding + 20);
            }

            // Draw WPM line
            g2.setColor(new Color(70, 130, 200));
            g2.setStroke(new BasicStroke(3));

            for (int i = 0; i < count - 1; i++) {
                g2.drawLine(wpmX[i], wpmY[i], wpmX[i + 1], wpmY[i + 1]);
            }

            for (int i = 0; i < count; i++) {
                g2.fillOval(wpmX[i] - 5, wpmY[i] - 5, 10, 10);
            }

            // Draw Accuracy line
            g2.setColor(new Color(60, 179, 113));
            g2.setStroke(new BasicStroke(3));

            for (int i = 0; i < count - 1; i++) {
                g2.drawLine(accX[i], accY[i], accX[i + 1], accY[i + 1]);
            }

            for (int i = 0; i < count; i++) {
                g2.fillOval(accX[i] - 5, accY[i] - 5, 10, 10);
            }

            // Legend
            g2.setStroke(new BasicStroke(2));

            g2.setColor(new Color(70, 130, 200));
            g2.fillRect(width - 190, 40, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("WPM", width - 165, 53);

            g2.setColor(new Color(60, 179, 113));
            g2.fillRect(width - 100, 40, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Accuracy", width - 75, 53);
        }
    }
}