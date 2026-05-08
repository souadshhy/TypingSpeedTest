package app;

import logic.TextSelector;

public class Main {

    public static void main(String[] args) {

        TextSelector selector = new TextSelector();

        String text = selector.getRandomText();

        System.out.println("=== RANDOM TEST TEXT ===");
        System.out.println(text);
    }
}