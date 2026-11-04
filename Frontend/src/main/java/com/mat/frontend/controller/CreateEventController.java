package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.session.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateEventController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker fromDateField;

    @FXML
    private TextField fromTimeField;

    @FXML
    private DatePicker toDateField;

    @FXML
    private TextField toTimeField;

    @FXML
    private TextField ticketPriceField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField capacityField;

    @FXML
    private Label messageLabel;


    @FXML
    public void initialize() {
        // Allow only integers for capacity
        capacityField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));

        // Allow only doubles for the price
        ticketPriceField.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null));
    }

    @FXML
    private void handleClear() {
        nameField.clear();
        descriptionField.clear();
        fromDateField.setValue(null);
        fromTimeField.clear();
        toDateField.setValue(null);
        toTimeField.clear();
        ticketPriceField.clear();
        locationField.clear();
        capacityField.clear();
        messageLabel.setText("");
    }

    @FXML
    private void handleCreateEvent() throws IOException {
        String name = nameField.getText();
        String description = descriptionField.getText();
        LocalDateTime fromTime = getFromTime();
        LocalDateTime toTime = getToTime();
        String ticketPrice = ticketPriceField.getText();
        String location = locationField.getText();
        String capacity = capacityField.getText();

        if (name.isEmpty() || fromTime == null || toTime == null || ticketPrice.isEmpty() || location.isEmpty() || capacity.isEmpty()) {
            messageLabel.setText("Please fill all the fields");
            return;
        }

        Task<EventResponse> task = new Task<EventResponse>() {
            @Override
            protected EventResponse call() throws Exception {
                return ApiClient.createEvent(name, description, fromTime, toTime, Double.parseDouble(ticketPrice), location, Integer.parseInt(capacity), 0);
            }
        };

        task.setOnSucceeded(event -> {
            EventResponse response = task.getValue();
            handleClear();
            messageLabel.setText("Event created successfully");

            // Add the user as organizer
            ApiClient.createOrganizer(Session.getCurrentUser().id(), response.id());
        });

        task.setOnFailed(event -> {
            messageLabel.setText(task.getException().getMessage());
        });

        new Thread(task).start();
    }

    /**
     * Helper methods
     */

    private LocalDateTime getFromTime() {
        if (fromDateField.getValue() == null || fromTimeField.getText() == null) {
            return null;
        }
        LocalDate date = fromDateField.getValue();
        LocalTime time = LocalTime.parse(fromTimeField.getText());
        return LocalDateTime.of(date, time);
    }

    private LocalDateTime getToTime() {
        if (toDateField.getValue() == null || toTimeField.getText() == null) {
            return null;
        }
        LocalDate date = toDateField.getValue();
        LocalTime time = LocalTime.parse(toTimeField.getText());
        return LocalDateTime.of(date, time);
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
