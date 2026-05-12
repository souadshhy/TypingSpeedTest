package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Difficulty;
import model.TestResult;
import model.User;

public class DataManager {

    private final String usersPath = "data/users.txt";
    private final String resultsPath = "data/results.txt";

    public DataManager() {
        createDataFilesIfMissing();
    }

    private void createDataFilesIfMissing() {

        try {
            File dataFolder = new File("data");

            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File usersFile = new File(usersPath);

            if (!usersFile.exists()) {
                usersFile.createNewFile();
            }

            File resultsFile = new File(resultsPath);

            if (!resultsFile.exists()) {
                resultsFile.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Error creating data files.");
        }
    }

    public ArrayList<User> loadUsers() {

        ArrayList<User> users = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(usersPath));

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 2) {
                    continue;
                }

                users.add(new User(parts[0], parts[1]));
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error loading users.");
        }

        return users;
    }

    public void saveUser(User user) {

        try {
            FileWriter writer = new FileWriter(usersPath, true);

            writer.write(
                    user.getUsername() + ","
                    + user.getPassword() + "\n"
            );

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
    }

    public void saveResult(TestResult result) {

        try {
            FileWriter writer = new FileWriter(resultsPath, true);

            writer.write(
                    result.getUsername() + ","
                    + result.getWpm() + ","
                    + result.getAccuracy() + ","
                    + result.getMistakes() + ","
                    + result.getTimeInSeconds() + ","
                    + result.getDifficulty() + ","
                    + result.getFinalScore()
                    + "\n"
            );

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving result.");
        }
    }

    public ArrayList<TestResult> loadResults() {

        ArrayList<TestResult> results = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(resultsPath));

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 5) {
                    continue;
                }

                String username = parts[0];
                double wpm = Double.parseDouble(parts[1]);
                double accuracy = Double.parseDouble(parts[2]);
                int mistakes = Integer.parseInt(parts[3]);
                int time = Integer.parseInt(parts[4]);

                Difficulty difficulty = Difficulty.MEDIUM;
                double finalScore = 0;

                if (parts.length >= 7) {
                    difficulty = Difficulty.valueOf(parts[5]);
                    finalScore = Double.parseDouble(parts[6]);
                }

                TestResult result = new TestResult(
                        username,
                        wpm,
                        accuracy,
                        mistakes,
                        time,
                        difficulty,
                        finalScore
                );

                results.add(result);
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Error loading results.");
        }

        return results;
    }

    public ArrayList<TestResult> getUserResults(String username) {

        ArrayList<TestResult> allResults = loadResults();
        ArrayList<TestResult> userResults = new ArrayList<>();

        for (TestResult result : allResults) {

            if (result.getUsername().equals(username)) {
                userResults.add(result);
            }
        }

        return userResults;
    }
}