package logic;
import logic.TextSelector;

public class TypingEngine {
 
    public String getNewTestText() {
    TextSelector selector = new TextSelector();
    return selector.getRandomText();
}
    public int countMistakes(String originalText, String typedText) {

        int mistakes = 0;

        int minLength = Math.min(originalText.length(), typedText.length());

        for (int i = 0; i < minLength; i++) {

            if (originalText.charAt(i) != typedText.charAt(i)) {

                mistakes++;

            }

        }

        mistakes += Math.abs(originalText.length() - typedText.length());

        return mistakes;

    }
    
}