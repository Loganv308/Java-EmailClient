module com.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    opens com.emailclient to javafx.fxml;
    exports com.emailclient;
    opens com.emailclient.view;
    opens com.emailclient.controller;
}
