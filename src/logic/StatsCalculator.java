package logic;

public class StatsCalculator {

    public double calculateWPM(String typedText, int timeInSeconds) {

        if (typedText == null || typedText.trim().isEmpty()) {
            return 0;
        }

        if (timeInSeconds <= 0) {
            return 0;
        }

        String[] words = typedText.trim().split("\\s+");

        int wordCount = words.length;

        double minutes = timeInSeconds / 60.0;

        return wordCount / minutes;
    }

    public double calculateAccuracy(int mistakes, int totalCharacters) {

        if (totalCharacters <= 0) {
            return 0;
        }

        int correctCharacters = totalCharacters - mistakes;

        if (correctCharacters < 0) {
            correctCharacters = 0;
        }

        return ((double) correctCharacters / totalCharacters) * 100;
    }
}