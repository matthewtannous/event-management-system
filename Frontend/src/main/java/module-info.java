module com.mat.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mat.frontend to javafx.fxml;
    exports com.mat.frontend;
}