package logic;

public class HardScoreStrategy extends ScoreStrategy {

    @Override
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        return (wpm * 0.6)
                + (accuracy * 0.4)
                - (mistakes * 3);
    }
}