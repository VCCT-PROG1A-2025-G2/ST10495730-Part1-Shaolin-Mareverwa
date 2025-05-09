package com.mycompany.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class Login extends JFrame {

    public static String registeredFirstName;
    public static String registeredLastName;
    public static String registeredUsername;
    public static String registeredPassword;
    public static String registeredCell;

    public CardLayout cardLayout;
    public JPanel mainPanel;

    public Login() {
        setTitle("Login System");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createLoginPanel(), "login");

        add(mainPanel);
        cardLayout.show(mainPanel, "register");
    }

    public JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // First Name & Last Name
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();

        // Username
        JTextField usernameField = new JTextField();
        JLabel usernameHint = new JLabel("Username must contain an underscore (_) and be no more than 5 characters.");
        usernameHint.setFont(new Font("Arial", Font.ITALIC, 12));
        JLabel usernameFeedback = new JLabel(" ");
        usernameFeedback.setFont(new Font("Arial", Font.ITALIC, 12));

        // Password
        JPasswordField passwordField = new JPasswordField();
        JLabel passwordHint = new JLabel("Password must be 8+ characters with 1 uppercase, 1 number, 1 special char.");
        passwordHint.setFont(new Font("Arial", Font.ITALIC, 12));
        JLabel passwordFeedback = new JLabel(" ");
        passwordFeedback.setFont(new Font("Arial", Font.ITALIC, 12));

        // Cell
        JTextField cellField = new JTextField();
        JLabel cellHint = new JLabel("Format: +27 followed by 9 digits (e.g. +27821234567)");
        cellHint.setFont(new Font("Arial", Font.ITALIC, 12));
        JLabel cellFeedback = new JLabel(" ");
        cellFeedback.setFont(new Font("Arial", Font.ITALIC, 12));

        JButton registerButton = new JButton("Register");
        JLabel formFeedback = new JLabel(" ");
        formFeedback.setFont(new Font("Arial", Font.BOLD, 12));

        // Live Validation Listeners
        usernameField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            String username = usernameField.getText().trim();
            if (checkUserName(username)) {
                usernameFeedback.setText("Username successfully captured.");
                usernameFeedback.setForeground(new Color(0, 128, 0));
            } else {
                usernameFeedback.setText("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
                usernameFeedback.setForeground(Color.RED);
            }
        });

        passwordField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            String password = new String(passwordField.getPassword());
            if (checkPasswordComplexity(password)) {
                passwordFeedback.setText("Password successfully captured");
                passwordFeedback.setForeground(new Color(0, 128, 0));
            } else {
                passwordFeedback.setText("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character");
                passwordFeedback.setForeground(Color.RED);
            }
        });

        cellField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            String cell = cellField.getText().trim();
            if (checkCellPhoneNumber(cell)) {
                cellFeedback.setText("Cell phone number successfully added.");
                cellFeedback.setForeground(new Color(0, 128, 0));
            } else {
                cellFeedback.setText("Cell phone number incorrectly formatted or does not contain international code.");
                cellFeedback.setForeground(Color.RED);
            }
        });

        // Register Button Logic
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String cell = cellField.getText().trim();

            if (firstName.isEmpty()) {
                formFeedback.setText("First Name is required.");
                formFeedback.setForeground(Color.RED);
            } else if (lastName.isEmpty()) {
                formFeedback.setText("Last Name is required.");
                formFeedback.setForeground(Color.RED);
            } else if (!checkUserName(username)) {
                formFeedback.setText("Fix username before registering.");
                formFeedback.setForeground(Color.RED);
            } else if (!checkPasswordComplexity(password)) {
                formFeedback.setText("Fix password before registering.");
                formFeedback.setForeground(Color.RED);
            } else if (!checkCellPhoneNumber(cell)) {
                formFeedback.setText("Fix cell number before registering.");
                formFeedback.setForeground(Color.RED);
            } else {
                registeredFirstName = firstName;
                registeredLastName = lastName;
                registeredUsername = username;
                registeredPassword = password;
                registeredCell = cell;
                JOptionPane.showMessageDialog(this, "User successfully registered.");
                cardLayout.show(mainPanel, "login");
            }
        });

        // Layout
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(usernameHint);
        panel.add(usernameFeedback);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(passwordHint);
        panel.add(passwordFeedback);

        panel.add(new JLabel("Cell Number:"));
        panel.add(cellField);
        panel.add(cellHint);
        panel.add(cellFeedback);

        panel.add(registerButton);
        panel.add(formFeedback);

        return panel;
    }

    public JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel("");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(messageLabel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Welcome " + registeredFirstName + ", " + registeredLastName + " it is great to see you again.");
                dispose();
            } else {
                messageLabel.setText("❌ Username or password incorrect, please try again.");
                messageLabel.setForeground(Color.RED);
            }
        });

        return panel;
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(regex, password);
    }

    public boolean checkCellPhoneNumber(String cell) {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cell);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }

    // Helper interface for cleaner real-time validation
    public interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update(javax.swing.event.DocumentEvent e);

        @Override
        default void insertUpdate(javax.swing.event.DocumentEvent e) { update(e); }

        @Override
        default void removeUpdate(javax.swing.event.DocumentEvent e) { update(e); }

        @Override
        default void changedUpdate(javax.swing.event.DocumentEvent e) { update(e); }
    }
}
/*
 * This code was developed with the assistance of ChatGPT, a language model by OpenAI.
 * ChatGPT was used to help create a regular expression based cell phone checker.
 * Citation: OpenAI. (2025). ChatGPT (May 2 version) [Large language model]. https://chat.openai.com
 */