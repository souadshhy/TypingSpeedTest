package app;

import logic.AuthManager;

public class Main {

    public static void main(String[] args) {

        AuthManager auth = new AuthManager();

        boolean result = auth.login("souad", "1234");

        System.out.println(result);

    }
}