package logic;

public class MediumScoreStrategy extends ScoreStrategy {

    @Override
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        return (wpm * 0.5)
                + (accuracy * 0.5)
                - (mistakes * 2);
    }
}