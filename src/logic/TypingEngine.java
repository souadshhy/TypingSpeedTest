package logic;

import model.Difficulty;
import model.TestResult;

public class TypingEngine {

    private StatsCalculator statsCalculator;

    public TypingEngine() {
        statsCalculator = new StatsCalculator();
    }

    public TestResult evaluateTyping(
            String username,
            String originalText,
            String typedText,
            int timeInSeconds,
            Difficulty difficulty) {

        // Count mistakes
        int mistakes = countMistakes(originalText, typedText);
        
        // Calculate WPM
        double wpm = statsCalculator.calculateWPM(typedText, timeInSeconds);
        
        // Calculate accuracy
        double accuracy = statsCalculator.calculateAccuracy(mistakes, originalText.length());
        
        // Get scoring strategy
        ScoreStrategy strategy = getScoreStrategy(difficulty);
        
        // Calculate final score (0-100 range)
        double finalScore = strategy.calculateFinalScore(wpm, accuracy, mistakes);
        
        // Ensure score is between 0 and 100
        finalScore = Math.max(0, Math.min(100, finalScore));

        return new TestResult(
                username,
                wpm,
                accuracy,
                mistakes,
                timeInSeconds,
                difficulty,
                finalScore
        );
    }

    private ScoreStrategy getScoreStrategy(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            return new EasyScoreStrategy();
        } else if (difficulty == Difficulty.HARD) {
            return new HardScoreStrategy();
        } else {
            return new MediumScoreStrategy();
        }
    }

    public int countMistakes(String originalText, String typedText) {
        if (originalText == null || typedText == null) {
            return 0;
        }
        
        int mistakes = 0;
        int minLength = Math.min(originalText.length(), typedText.length());

        // Count character mismatches
        for (int i = 0; i < minLength; i++) {
            if (originalText.charAt(i) != typedText.charAt(i)) {
                mistakes++;
            }
        }

        // Add extra characters as mistakes
        if (typedText.length() > originalText.length()) {
            mistakes += (typedText.length() - originalText.length());
        }
        
        // Add missing characters as mistakes
        if (originalText.length() > typedText.length()) {
            mistakes += (originalText.length() - typedText.length());
        }

        return mistakes;
    }
}