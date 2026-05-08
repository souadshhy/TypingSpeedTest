package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

}
