package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.AnnouncementResponse;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateAnnouncementController {
    @FXML
    private Label messageLabel;

    @FXML
    private TextArea announcementMessageField;

    @FXML
    private CheckBox visibilityCheckbox;

    @FXML
    private void handleClear() {
        announcementMessageField.clear();
        visibilityCheckbox.setSelected(false);
        messageLabel.setText("");
    }

    @FXML
    private void handleCreateAnnouncement() throws IOException {
        String message = announcementMessageField.getText();
        boolean isPublic = visibilityCheckbox.isSelected();

        if (message.isEmpty()) {
            messageLabel.setText("Please fill all the fields");
            return;
        }

        Task<AnnouncementResponse> task = new Task<AnnouncementResponse>() {
            @Override
            protected AnnouncementResponse call() throws Exception {
                return ApiClient.createAnnouncement(message, isPublic, Session.getCurrentUser().id(), ((EventResponse) SceneData.getData()).id());
            }
        };

        task.setOnSucceeded(announcement -> {
            AnnouncementResponse response = task.getValue();
            handleClear();
            messageLabel.setText("Announcement created successfully");

        });

        task.setOnFailed(announcement -> {
            messageLabel.setText(task.getException().getMessage());
        });

        new Thread(task).start();
    }


    @FXML
    private void handleLogout() {
        Session.clear();
        try {
            SceneManager.switchScene("login.fxml");
        } catch (IOException exception) {
            messageLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleHome() {
        try {
            SceneManager.switchScene("home.fxml");
        } catch (IOException exception) {
            messageLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleEventsNavigator() {
        try {
            SceneManager.switchScene("events-navigator.fxml");
        } catch (IOException exception) {
            messageLabel.setText("Error: " + exception.getMessage());
        }
    }
}
