package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.Session;
import com.mat.frontend.api.dto.LoginResponse;
import com.mat.frontend.api.dto.RegistrationResponse;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() throws IOException {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill all the fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            return;
        }

        Task<RegistrationResponse> task = new Task<RegistrationResponse>() {
            @Override
            protected RegistrationResponse call() throws Exception {
                return ApiClient.register(username, email, firstName, lastName, password);
            }
        };

        task.setOnSucceeded(event -> {
            RegistrationResponse response = task.getValue();
            Session.setCurrentUser(new LoginResponse(response.id(), response.username(), response.email(), response.firstName(), response.lastName()));
            try {
                SceneManager.switchScene("home.fxml");
            } catch (IOException e) {
                messageLabel.setText("Could not open Home page :(");
            }
        });

        task.setOnFailed(event -> {
            messageLabel.setText(task.getException().getMessage());
        });

        task.run();
    }
}
