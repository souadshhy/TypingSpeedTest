package logic;

public class EasyScoreStrategy extends ScoreStrategy {

    @Override // overriding parent method
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        // Ensure score doesn't go below 0
        double score = (wpm * 0.4) + (accuracy * 0.6) - (mistakes * 0.5);
        return Math.max(0, Math.min(100, score));
    }
}