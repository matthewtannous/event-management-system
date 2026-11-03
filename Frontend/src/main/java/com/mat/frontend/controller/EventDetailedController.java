package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.api.dto.ParticipantResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class EventDetailedController {
    @FXML
    private Label informationLabel;

    @FXML
    private Label nameField;

    @FXML
    private Label descriptionField;

    @FXML
    private Label fromTimeField;

    @FXML
    private Label toTimeField;

    @FXML
    private Label ticketPriceField;

    @FXML
    private Label locationField;

    @FXML
    private Label capacityField;

    @FXML
    private Label registeredField;

    @FXML
    public void initialize() {
        if (!(SceneData.getData() instanceof EventResponse)) {
            informationLabel.setText("An error occurred");
            return;
        }

        EventResponse event = (EventResponse) SceneData.getData();
        nameField.setText(event.name());
        descriptionField.setText(event.description());
        fromTimeField.setText(String.valueOf(event.fromTime()));
        toTimeField.setText(String.valueOf(event.toTime()));
        ticketPriceField.setText(String.valueOf(event.ticketPrice()));
        locationField.setText(String.valueOf(event.location()));
        capacityField.setText(String.valueOf(event.capacity()));
        registeredField.setText(String.valueOf(event.registered()));
    }

    @FXML
    public void handleRegisterForEvent() {
        Task<ParticipantResponse> task = new Task<ParticipantResponse>() {
            @Override
            protected ParticipantResponse call() throws Exception {
                return ApiClient.createParticipant(Session.getCurrentUser().id(), SceneData.getId());
            }
        };
        task.setOnSucceeded(event -> {
            informationLabel.setText("Registration successful!");
        });

        task.setOnFailed(event -> {
            informationLabel.setText(task.getException().getMessage());
        });

        new Thread(task).start();
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
