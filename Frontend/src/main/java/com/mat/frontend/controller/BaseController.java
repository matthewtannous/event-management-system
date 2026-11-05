package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public abstract class BaseController {
    private Label informationLabel;
    private Label messageLabel;

    @FXML
    protected void handleLogout() {
        Session.clear();
        try {
            SceneManager.switchScene("login.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    protected void handleHome() {
        try {
            SceneManager.switchScene("home.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    protected void handleEventsNavigator() {
        try {
            SceneManager.switchScene("events-navigator.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    protected void handleAllUsers() {
        try {
            SceneManager.switchScene("all-users.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }
}
