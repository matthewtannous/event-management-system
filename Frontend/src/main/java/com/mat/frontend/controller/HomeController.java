package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
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
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            messageLabel.setText("Hello," + Session.getCurrentUser().firstName() + " " + Session.getCurrentUser().lastName());
        } else {
            messageLabel.setText("What?!?!?!");
        }
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
