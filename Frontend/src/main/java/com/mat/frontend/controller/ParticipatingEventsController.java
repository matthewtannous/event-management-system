package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ParticipatingEventsController extends BaseController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    // Event table
    @FXML
    private TableView<EventResponse> participatingEventsTable;

//    @FXML
//    private TableColumn<EventResponse, Long> idColumn;

    @FXML
    private TableColumn<EventResponse, String> nameColumn;

//    @FXML
//    private TableColumn<EventResponse, String> descriptionColumn;

    @FXML
    private TableColumn<EventResponse, LocalDateTime> fromTimeColumn;

    @FXML
    private TableColumn<EventResponse, LocalDateTime> toTimeColumn;

    //    @FXML
//    private TableColumn<EventResponse, Double> ticketPriceColumn;
//
    @FXML
    private TableColumn<EventResponse, String> locationColumn;
//
//    @FXML
//    private TableColumn<EventResponse, Integer> capacityColumn;
//
//    @FXML
//    private TableColumn<EventResponse, Integer> registeredColumn;

    // Button
    @FXML
    private TableColumn<EventResponse, Void> actionColumn;


    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            messageLabel.setText("Events you are participating in:");

            // data binding
//            idColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().id()));
            nameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().name()));
//            descriptionColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().description()));
            fromTimeColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().fromTime()));
            toTimeColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().toTime()));
//            ticketPriceColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().ticketPrice()));
            locationColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().location()));
//            capacityColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().capacity()));
//            registeredColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().registered()));

            // Button
            actionColumn.setCellFactory(c -> new TableCell<EventResponse, Void>() {
                private final Button button = new Button("View");

                {
                    button.setOnAction(event -> {
                        EventResponse selectedEvent = (EventResponse) getTableView().getItems().get(getIndex());

                        SceneData.setSceneData(selectedEvent);
                        try {
                            SceneManager.switchScene("event-detailed.fxml");
                        } catch (IOException exception) {
                            informationLabel.setText("Error: " + exception.getMessage());
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            });

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
                return ApiClient.getEventsWithUserParticipating(Session.getCurrentUser().id());
            }
        };

        task.setOnSucceeded(event -> {
            participatingEventsTable.getItems().setAll(task.getValue());
            informationLabel.setText("");
        });

        task.setOnFailed(event -> {
            informationLabel.setText("Failed to load events: " + task.getException().getMessage());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
