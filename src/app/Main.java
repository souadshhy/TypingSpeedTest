package app;

import logic.AuthManager;

public class Main {

    public static void main(String[] args) {

        AuthManager auth = new AuthManager();

        boolean result = auth.signup("", "7777");

        System.out.println(result);

    }
}