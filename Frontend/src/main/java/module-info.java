module com.mat.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.mat.frontend to javafx.fxml;
    exports com.mat.frontend;
    exports com.mat.frontend.controller;
    opens com.mat.frontend.controller to javafx.fxml;

    exports com.mat.frontend.api.dto to com.fasterxml.jackson.databind;
}