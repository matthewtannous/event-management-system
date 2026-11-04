package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.api.dto.ParticipantResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

//    @FXML
//    private Button registrationButton;

    @FXML
    private Button actionButton;

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

        boolean participates = ApiClient.userParticipatesInEvent(Session.getCurrentUser().id(), event.id());
        boolean organizes = ApiClient.userOrganizesEvent(Session.getCurrentUser().id(), event.id());

        if (participates) {
            actionButton.setDisable(true);
            actionButton.setText("Register");
            informationLabel.setText("You are participating in this event!");
        } else if (organizes) {
//            registrationButton.setDisable(true);

            actionButton.setOnAction(e -> handleAnnouncementCreation());
            actionButton.setText("Create announcement");
            informationLabel.setText("You are organizing this event!");
        } else {
            actionButton.setOnAction(e -> handleRegisterForEvent());
            actionButton.setText("Register");
        }
    }

    @FXML
    public void handleRegisterForEvent() {
        Task<ParticipantResponse> task = new Task<ParticipantResponse>() {
            @Override
            protected ParticipantResponse call() throws Exception {
                return ApiClient.createParticipant(Session.getCurrentUser().id(), ((EventResponse) SceneData.getData()).id());
            }
        };
        task.setOnSucceeded(event -> {
            SceneData.setSceneData("You have register for the event: " + ((EventResponse) SceneData.getData()).name());
            // informationLabel.setText("Registration successful!");

            // Go back
            handleEventsNavigator();
        });

        task.setOnFailed(event -> {
            informationLabel.setText(task.getException().getMessage());
        });

        new Thread(task).start();
    }

    @FXML
    public void handleAnnouncementCreation() {
        try {
            SceneManager.switchScene("create-announcement.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
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
