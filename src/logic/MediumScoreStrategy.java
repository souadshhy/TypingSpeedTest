package logic;

public class MediumScoreStrategy extends ScoreStrategy {

    @Override
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        double score = (wpm * 0.5) + (accuracy * 0.5) - (mistakes * 0.8);
        return Math.max(0, Math.min(100, score));
    }
}