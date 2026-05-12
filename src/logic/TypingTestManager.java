package logic;

import java.util.ArrayList;
import model.Difficulty;
import model.TestResult;

public class TypingTestManager {

    private TextSelector textSelector;
    private TypingEngine typingEngine;
    private DataManager dataManager;
    private AnalyticsManager analyticsManager;

    public TypingTestManager() {
        textSelector = new TextSelector();
        typingEngine = new TypingEngine();
        dataManager = new DataManager();
        analyticsManager = new AnalyticsManager();
    }

    public String generateTestText(Difficulty difficulty) {
        return textSelector.getRandomText(difficulty);
    }

    public TestResult submitTypingTest(
            String username,
            String originalText,
            String typedText,
            int timeInSeconds,
            Difficulty difficulty) {

        TestResult result = typingEngine.evaluateTyping(
                username,
                originalText,
                typedText,
                timeInSeconds,
                difficulty
        );

        dataManager.saveResult(result);

        return result;
    }

    public ArrayList<TestResult> getUserHistory(String username) {
        return dataManager.getUserResults(username);
    }

    public double getAverageWPM(String username) {
        return analyticsManager.getAverageWPM(getUserHistory(username));
    }

    public double getBestWPM(String username) {
        return analyticsManager.getBestWPM(getUserHistory(username));
    }

    public double getAverageAccuracy(String username) {
        return analyticsManager.getAverageAccuracy(getUserHistory(username));
    }

    public int getTotalTests(String username) {
        return analyticsManager.getTotalTests(getUserHistory(username));
    }
}