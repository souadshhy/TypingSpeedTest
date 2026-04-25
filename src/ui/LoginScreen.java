package ui;

import javax.swing.*;

public class LoginScreen {

    public void show() {
        JFrame frame = new JFrame("Login Screen");

        JLabel label = new JLabel("Typing Speed Test Login");
        label.setBounds(50, 30, 200, 30);

        JTextField username = new JTextField();
        username.setBounds(50, 70, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 110, 100, 30);

        frame.add(label);
        frame.add(username);
        frame.add(loginButton);

        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}