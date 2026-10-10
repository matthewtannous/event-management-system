package com.mat.frontend;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.setStage(stage);
        SceneManager.switchScene("login-view.fxml");
        stage.setTitle("HELLLLOOOOO");
        stage.show();
    }
}
