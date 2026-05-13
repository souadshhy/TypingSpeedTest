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
        
        // Standard WPM calculation: (words / minutes)
        double minutes = timeInSeconds / 60.0;
        double wpm = wordCount / minutes;
        
        // Cap at reasonable maximum
        return Math.min(wpm, 200);
    }

    public double calculateAccuracy(int mistakes, int totalCharacters) {

        if (totalCharacters <= 0) {
            return 100;
        }

        int correctCharacters = totalCharacters - mistakes;
        if (correctCharacters < 0) {
            correctCharacters = 0;
        }

        double accuracy = ((double) correctCharacters / totalCharacters) * 100;
        return Math.max(0, Math.min(100, accuracy));
    }
}