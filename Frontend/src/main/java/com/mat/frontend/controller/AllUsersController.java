package com.mat.frontend.controller;

import com.mat.frontend.api.ApiClient;
import com.mat.frontend.api.dto.UserResponse;
import com.mat.frontend.session.Session;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class AllUsersController extends BaseController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    // Event table
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

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            messageLabel.setText("All users:");

            // data binding
//            idColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().id()));
            usernameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().username()));
            firstNameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().firstName()));
            lastNameColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().lastName()));
            emailColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().email()));

            loadUsers();
        } else {
            messageLabel.setText("What?!?!?!");
        }
    }

    @FXML
    private void loadUsers() {
        informationLabel.setText("Loading events...");
        Task<List<UserResponse>> task = new Task<List<UserResponse>>() {
            @Override
            protected List<UserResponse> call() throws Exception {
                return ApiClient.getUsers();
            }
        };

        task.setOnSucceeded(event -> {
            allUsersTable.getItems().setAll(task.getValue());
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
