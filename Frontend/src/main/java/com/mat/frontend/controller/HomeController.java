package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.Session;
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
}
