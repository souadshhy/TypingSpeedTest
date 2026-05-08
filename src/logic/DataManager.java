package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.TestResult;

import model.User;

public class DataManager {

public ArrayList<User> loadUsers() {

    ArrayList<User> users = new ArrayList<>();

    try {

        File file = new File("data/users.txt");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String[] parts = line.split(",");

            String username = parts[0];
            String password = parts[1];

            User user = new User(username, password);

            users.add(user);

        }

        scanner.close();

    } catch (FileNotFoundException e) {

        System.out.println("File not found.");

    }

        return users;
    }
public void saveUser(User user) {

    try {

        FileWriter writer = new FileWriter("data/users.txt", true);

        writer.write(user.getUsername()
                + ","
                + user.getPassword()
                + "\n");

        writer.close();

    } catch (IOException e) {

        System.out.println("Error writing to file.");

    }

}
public void saveResult(String username, TestResult result) {

    try {

        FileWriter writer =
                new FileWriter("data/results.txt", true);

        writer.write(
                result.getUsername() + ","
                + result.getWpm() + ","
                + result.getAccuracy() + ","
                + result.getMistakes() + ","
                + result.getTimeInSeconds()
                + "\n"
        );

        writer.close();

    } catch (IOException e) {

        System.out.println("Error saving result.");

    }

}
public ArrayList<TestResult> loadResults() {

    ArrayList<TestResult> results =
            new ArrayList<>();

    try {

        File file = new File("data/results.txt");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String[] parts = line.split(",");
            if (parts.length < 5) {

                continue;
            }
            String username = parts[0];

            double wpm =
                    Double.parseDouble(parts[1]);

            double accuracy =
                    Double.parseDouble(parts[2]);

            int mistakes =
                    Integer.parseInt(parts[3]);

            int time =
                    Integer.parseInt(parts[4]);

            TestResult result =
                    new TestResult(
                            username,
                            wpm,
                            accuracy,
                            mistakes,
                            time
                    );

            results.add(result);

        }

        scanner.close();

    } catch (FileNotFoundException e) {

        System.out.println("Results file not found.");

    }

    return results;

}

public ArrayList<TestResult> getUserResults(String username) {

    ArrayList<TestResult> allResults =
            loadResults();

    ArrayList<TestResult> userResults =
            new ArrayList<>();

    for (TestResult result : allResults) {

        if (result.getUsername().equals(username)) {

            userResults.add(result);

        }

    }

    return userResults;

}
public int getTotalTests(
        ArrayList<TestResult> results) {

    return results.size();

}
}
