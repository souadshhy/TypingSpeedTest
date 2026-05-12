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

        int mistakes = countMistakes(originalText, typedText);

        double wpm = statsCalculator.calculateWPM(
                typedText,
                timeInSeconds
        );

        double accuracy = statsCalculator.calculateAccuracy(
                mistakes,
                originalText.length()
        );

        ScoreStrategy strategy = getScoreStrategy(difficulty);

        double finalScore = strategy.calculateFinalScore(
                wpm,
                accuracy,
                mistakes
        );

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

        int mistakes = 0;

        int minLength = Math.min(
                originalText.length(),
                typedText.length()
        );

        for (int i = 0; i < minLength; i++) {

            if (originalText.charAt(i) != typedText.charAt(i)) {
                mistakes++;
            }
        }

        mistakes += Math.abs(
                originalText.length() - typedText.length()
        );

        return mistakes;
    }
}