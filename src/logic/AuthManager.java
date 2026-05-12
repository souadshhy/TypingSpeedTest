package logic;

import java.util.ArrayList;
import model.User;

public class AuthManager {

    private DataManager dataManager;

    public AuthManager() {
        dataManager = new DataManager();
    }

    public boolean login(String enteredUsername, String enteredPassword) {

        if (enteredUsername == null || enteredPassword == null) {
            return false;
        }

        ArrayList<User> users = dataManager.loadUsers();

        for (User user : users) {

            if (user.getUsername().equals(enteredUsername.trim())
                    && user.getPassword().equals(enteredPassword)) {

                return true;
            }
        }

        return false;
    }

    public boolean signup(String username, String password) {

        if (username == null || password == null) {
            return false;
        }

        username = username.trim();

        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        ArrayList<User> users = dataManager.loadUsers();

        for (User user : users) {

            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        User newUser = new User(username, password);

        dataManager.saveUser(newUser);

        return true;
    }
}