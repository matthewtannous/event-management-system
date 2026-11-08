package com.mat.frontend.controller;

import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.EventResponse;
import com.mat.frontend.api.dto.OrganizerResponse;
import com.mat.frontend.api.dto.UserResponse;
import com.mat.frontend.session.SceneData;
import com.mat.frontend.session.Session;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class CreateOrganizerController extends BaseController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    // User table
    @FXML
    private TableView<UserResponse> allUsersTable;

//    @FXML
//    private TableColumn<UserResponse, Long> idColumn;

    @FXML
    private TableColumn<UserResponse, String> usernameColumn;

    @FXML
    private TableColumn<UserResponse, String> firstNameColumn;

    @FXML
    private TableColumn<UserResponse, String> lastNameColumn;

    @FXML
    private TableColumn<UserResponse, String> emailColumn;

    // Button
    @FXML
    private TableColumn<UserResponse, Void> actionColumn;

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {

            // data binding
//            idColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().id()));
            usernameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().username()));
            firstNameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().firstName()));
            lastNameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().lastName()));
            emailColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().email()));

            // Button
            actionColumn.setCellFactory(c -> new TableCell<UserResponse, Void>() {
                private final Button button = new Button("Add");

                {
                    button.setOnAction(event -> {
                        UserResponse selectedUser = (UserResponse) getTableView().getItems().get(getIndex());

                        // Add user as organizer
                        addOrganizer(selectedUser, ((EventResponse) SceneData.getData()).id());
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

            loadUsers();
        } else {
            messageLabel.setText("What?!?!?!");
        }
    }

    @FXML
    private void loadUsers() {
        informationLabel.setText("Loading users...");
        Task<List<UserResponse>> task = new Task<List<UserResponse>>() {
            @Override
            protected List<UserResponse> call() throws Exception {
                return ApiClient.getUsersNotInEvent(((EventResponse) SceneData.getData()).id());
            }
        };

        task.setOnSucceeded(event -> {
            allUsersTable.getItems().setAll(task.getValue());
            informationLabel.setText("");
        });

        task.setOnFailed(event -> {
            informationLabel.setText("Failed to load users: " + task.getException().getMessage());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void addOrganizer(UserResponse user, Long eventId) {
        Task<OrganizerResponse> task = new Task<OrganizerResponse>() {
            @Override
            protected OrganizerResponse call() throws Exception {
                return ApiClient.createOrganizer(user.id(), eventId);
            }
        };

        task.setOnSucceeded(event -> {
            informationLabel.setText("Added " + user.username() + " as an organizer!");

            // Delete row from table ????
        });

        task.setOnFailed(event -> {
            informationLabel.setText("Failed to add " + user.username() + ": " + task.getException().getMessage());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
