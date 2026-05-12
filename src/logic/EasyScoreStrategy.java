package logic;

public class EasyScoreStrategy extends ScoreStrategy {

    @Override
    public double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes) {

        return (wpm * 0.4)
                + (accuracy * 0.6)
                - (mistakes * 1);
    }
}