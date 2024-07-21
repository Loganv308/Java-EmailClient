package com.emailclient.controller;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.LoginService;
import com.emailclient.model.EmailAccount;
import com.emailclient.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController{

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    // Constructor
    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() { 
        System.out.println("loginButtonAction!");
        
        if(fieldsAreValid()){
            System.out.println("Penus and balls");
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch(emailLoginResult) {
                case SUCCESS:
                    System.out.println("Login Successful!" + emailAccount);
                    return;
            }
        }

        System.out.println("LoginButtonAction!");

        viewFactory.showMainWindow();

        Stage stage = (Stage) errorLabel.getScene().getWindow();

        viewFactory.closeStage(stage);
    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        };

        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        };

        return true;
    }
}

