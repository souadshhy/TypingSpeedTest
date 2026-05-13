package logic;

public class HardScoreStrategy extends ScoreStrategy {

    @Override
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        double score = (wpm * 0.6) + (accuracy * 0.4) - (mistakes * 1);
        return Math.max(0, Math.min(100, score));
    }
}