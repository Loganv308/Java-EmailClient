package com.emailclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.EmailSenderService;
import com.emailclient.controller.services.EmailSendingResult;
import com.emailclient.model.EmailAccount;
import com.emailclient.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;


public class ComposeMessageController extends BaseController implements Initializable {

    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTextField;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void sendButtonAction(ActionEvent event) {
        EmailSenderService emailSenderService = new EmailSenderService(
            emailAccountChoice.getValue(),
            subjectTextField.getText(),
            recipientTextField.getText(),
            htmlEditor.getHtmlText()
            );
            emailSenderService.start();
            emailSenderService.setOnSucceeded(e-> {
                EmailSendingResult emailSendingResult = emailSenderService.getValue();
                switch(emailSendingResult) {
                    case SUCCESS:
                        Stage stage = (Stage) recipientTextField.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        break;
                    case FAILED_BY_PROVIDER:
                        errorLabel.setText("Provider Error");
                        break;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Unexpected Error");
                        break;
                }
            });
    }

    @FXML
    private TextField subjectTextField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }

}


