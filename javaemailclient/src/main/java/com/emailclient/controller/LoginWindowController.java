package com.emailclient.controller;

import com.emailclient.EmailManager;
import com.emailclient.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController{

    @FXML
    private TextField emailAddressField;

    @FXML
    private Button errorLabel;

    @FXML
    private PasswordField passwordField;

    // Constructor
    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println("LoginButtonAction!");

        viewFactory.showMainWindow();

        Stage stage = (Stage) errorLabel.getScene().getWindow();

        viewFactory.closeStage(stage);
    }

}

