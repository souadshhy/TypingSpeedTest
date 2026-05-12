package logic;

public abstract class ScoreStrategy {

    public abstract double calculateFinalScore(
            double wpm,
            double accuracy,
            int mistakes
    );
}