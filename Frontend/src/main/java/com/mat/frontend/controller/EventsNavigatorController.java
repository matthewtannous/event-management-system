package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class EventsNavigatorController extends BaseController {
    @FXML
    private Label informationLabel;

    @FXML
    public void initialize() {
        if (SceneData.getData() instanceof String) {
            informationLabel.setText((String) SceneData.getData());
        }
    }

    @FXML
    private void handleEventsParticipating() {
        try {
            SceneManager.switchScene("participating-events.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleEventsOrganizing() {
        try {
            SceneManager.switchScene("organizing-events.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleAllEvents() {
        try {
            SceneManager.switchScene("all-events.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }

    @FXML
    private void handleCreateEvent() {
        try {
            SceneManager.switchScene("create-event.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }
}
