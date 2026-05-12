package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import model.Difficulty;

public class TextSelector {

    private final String folderPath = "texts/";

    public String getRandomText() {
        return getRandomText(Difficulty.MEDIUM);
    }

    public String getRandomText(Difficulty difficulty) {

        File folder = new File(folderPath + difficulty.name().toLowerCase());

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return "No test files available for " + difficulty.name();
        }

        Random random = new Random();

        File selectedFile = files[random.nextInt(files.length)];

        return readFileContent(selectedFile);
    }

    private String readFileContent(File file) {

        StringBuilder content = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append(" ");
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            return "Error reading file.";
        }

        return content.toString().trim();
    }
}