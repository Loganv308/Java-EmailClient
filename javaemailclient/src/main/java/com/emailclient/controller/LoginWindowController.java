package com.emailclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.LoginService;
import com.emailclient.model.EmailAccount;
import com.emailclient.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController implements Initializable{

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
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                
                EmailLoginResult emailLoginResult = loginService.getValue();
                
                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("Login Successful!" + emailAccount);
                        if (!viewFactory.isMainViewInitialized()) {
                            viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid Credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Unexpected Error!");
                        return;
                    default:
                        return;
                }
            });
        }
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        emailAddressField.setText("loganv308@gmail.com");
        // The password (key) below has been changed. Google makes it impossible to find where this was created to revoke access. 
        passwordField.setText("qvlhvbdcsfzgasln");
    }
}

