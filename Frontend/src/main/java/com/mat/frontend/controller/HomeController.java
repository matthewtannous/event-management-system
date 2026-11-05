package com.mat.frontend.controller;

import com.mat.frontend.api.dto.LoginResponse;
import com.mat.frontend.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController extends BaseController {
    @FXML
    private Label messageLabel;

    @FXML
    private Label informationLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() == null) {
            messageLabel.setText("What?!?!?!");
            return;
        }

        LoginResponse user = Session.getCurrentUser();
        messageLabel.setText("Hello," + user.username());
        idLabel.setText(user.id().toString());
        usernameLabel.setText(user.username());
        firstNameLabel.setText(user.firstName());
        lastNameLabel.setText(user.lastName());
        emailLabel.setText(user.email());
    }
}
