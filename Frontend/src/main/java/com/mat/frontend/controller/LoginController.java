package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.Session;
import com.mat.frontend.api.dto.LoginResponse;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password");
            return;
        }

        messageLabel.setText("Logging innnnnnn......");

        Task<LoginResponse> task = new Task<LoginResponse>() {
            @Override
            protected LoginResponse call() throws Exception {
                return ApiClient.login(username, password);
            }
        };

        task.setOnSucceeded(event -> {
            Session.setCurrentUser(task.getValue());
            try {
                SceneManager.switchScene("home.fxml");
            } catch (IOException e) {
                messageLabel.setText("Could not open Home page :( \n" + e.getMessage());
            }
        });

        task.setOnFailed(event -> {
            System.out.println(task.getException().getMessage());
            messageLabel.setText(task.getException().getMessage());
        });

         task.run();
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
    }

    @FXML
    private void handleRegister() throws IOException {
        SceneManager.switchScene("registration.fxml");
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }
}
