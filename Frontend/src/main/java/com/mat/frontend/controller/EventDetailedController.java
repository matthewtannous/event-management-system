package com.mat.frontend.controller;

import com.mat.frontend.SceneManager;
import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.AnnouncementResponse;
import com.mat.frontend.api.dto.AnnouncementResponseDetailed;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.api.dto.ParticipantResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class EventDetailedController extends BaseController{
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
    private VBox announcementsContainer;

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
            actionButton.setText("Register");
            actionButton.setDisable(true);
            informationLabel.setText("You are participating in this event!");
            fillAnnouncements(event.id(), true); // get public announcements

        } else if (organizes) {
            actionButton.setOnAction(e -> handleAnnouncementCreation());
            actionButton.setText("Create announcement");
            informationLabel.setText("You are organizing this event!");
            fillAnnouncements(event.id(), false); // get public and private announcements
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

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void fillAnnouncements(Long eventId, boolean onlyPublic) {
        List<AnnouncementResponseDetailed> announcements = ApiClient.getAnnouncementsForEvent(eventId, onlyPublic);
        if (announcements.isEmpty()) {
            announcementsContainer.setVisible(false);
            announcementsContainer.setManaged(false);
            return;
        }

        announcementsContainer.setVisible(true);
        announcementsContainer.setManaged(true);

        for (AnnouncementResponseDetailed announcement : announcements) {
            // Add announcer
            HBox announcerBox = new HBox();
            announcerBox.setAlignment(Pos.CENTER);

            Label fromLabel = new Label("Announcement from: ");
            Label announcerLabel = new Label(announcement.username());

            announcerBox.getChildren().addAll(fromLabel, announcerLabel);

            // Add message
            HBox messageBox = new HBox();
            messageBox.setAlignment(Pos.CENTER);

            Label announcementLabel = new Label(announcement.message());
//            announcementLabel.setPrefSize(200, 400);
            announcementLabel.setWrapText(true);

            messageBox.getChildren().add(announcerBox);

            VBox announcementBox = new VBox();
            announcementBox.setAlignment(Pos.CENTER);
            announcementBox.getChildren().addAll(announcerBox, announcementLabel);

            announcementsContainer.getChildren().add(announcementBox);
        }
    }

    @FXML
    public void handleAnnouncementCreation() {
        try {
            SceneManager.switchScene("create-announcement.fxml");
        } catch (IOException exception) {
            informationLabel.setText("Error: " + exception.getMessage());
        }
    }
}
