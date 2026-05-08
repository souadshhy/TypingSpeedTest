package logic;

public class StatsCalculator {

    public double calculateWPM(String typedText, int timeInSeconds) {

        String[] words = typedText.trim().split("\\s+");

        int wordCount = words.length;

        double minutes = timeInSeconds / 60.0;

        double wpm = wordCount / minutes;

        return wpm;

    }
    public double calculateAccuracy(int mistakes, int totalCharacters) {

    int correctCharacters = totalCharacters - mistakes;

    double accuracy =
            ((double) correctCharacters / totalCharacters) * 100;

    return accuracy;

}
}