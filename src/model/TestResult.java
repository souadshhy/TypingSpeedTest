package model;

public class TestResult {

    private String username;
    private double wpm;
    private double accuracy;
    private int mistakes;
    private int timeInSeconds;
    private Difficulty difficulty;
    private double finalScore;

    public TestResult(
            String username,
            double wpm,
            double accuracy,
            int mistakes,
            int timeInSeconds,
            Difficulty difficulty,
            double finalScore) {

        this.username = username;
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.mistakes = mistakes;
        this.timeInSeconds = timeInSeconds;
        this.difficulty = difficulty;
        this.finalScore = finalScore;
    }

    public String getUsername() {
        return username;
    }

    public double getWpm() {
        return wpm;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getMistakes() {
        return mistakes;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public double getFinalScore() {
        return finalScore;
    }
}