package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.dto.LoginResponse;
import com.mat.frontend.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() == null) {
            messageLabel.setText("What?!?!?!");
            return;
        }

        LoginResponse user = Session.getCurrentUser();
        messageLabel.setText("Hello," + user.username());
        idLabel.setText(user.id().toString());
        usernameLabel.setText(user.username());
        firstNameLabel.setText(user.firstName());
        lastNameLabel.setText(user.lastName());
        emailLabel.setText(user.email());
    }

    @FXML
    private void handleLogout() {
        Session.clear();
        try {
            SceneManager.switchScene("login.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleHome() {
        try {
            SceneManager.switchScene("home.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleEventsNavigator() {
        try {
            SceneManager.switchScene("events-navigator.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }
}
