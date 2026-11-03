package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.session.Session;
import com.mat.frontend.api.dto.EventResponse;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class OrganizingEventsController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    // Event table
    @FXML
    private TableView<EventResponse> organizingEventsTable;

//    @FXML
//    private TableColumn<EventResponse, Long> idColumn;

    @FXML
    private TableColumn<EventResponse, String> nameColumn;

    @FXML
    private TableColumn<EventResponse, String> descriptionColumn;

    @FXML
    private TableColumn<EventResponse, LocalDateTime> fromTimeColumn;

    @FXML
    private TableColumn<EventResponse, LocalDateTime> toTimeColumn;

    @FXML
    private TableColumn<EventResponse, Double> ticketPriceColumn;

    @FXML
    private TableColumn<EventResponse, String> locationColumn;

    @FXML
    private TableColumn<EventResponse, Integer> capacityColumn;

    @FXML
    private TableColumn<EventResponse, Integer> registeredColumn;


    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            messageLabel.setText("Events you are organizing:");

            // data binding
//            idColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().id()));
            nameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().name()));
            descriptionColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().description()));
            fromTimeColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().fromTime()));
            toTimeColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().toTime()));
            ticketPriceColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().ticketPrice()));
            locationColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().location()));
            capacityColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().capacity()));
            registeredColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().registered()));

            loadEvents();
        } else {
            messageLabel.setText("What?!?!?!");
        }
    }

    @FXML
    private void loadEvents() {
        informationLabel.setText("Loading events...");
        Task<List<EventResponse>> task = new Task<List<EventResponse>>() {
            @Override
            protected List<EventResponse> call() throws Exception {
                return ApiClient.getEventsWithUserOrganizing(Session.getCurrentUser().id());
            }
        };

        task.setOnSucceeded(event -> {
            organizingEventsTable.getItems().setAll(task.getValue());
            informationLabel.setText("");
        });

        task.setOnFailed(event -> {
            informationLabel.setText("Failed to load events: " + task.getException().getMessage());
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
