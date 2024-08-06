package com.emailclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.MessageRendererService;
import com.emailclient.model.EmailMessage;
import com.emailclient.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class EmailDetailsController extends BaseController implements Initializable {

    @FXML
    private Label attachmentLabel;

    @FXML
    private HBox hBoxDownloads;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
        
    }

    @Override
    public void initialize(URL location, ResourceBundle arg1) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();

        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }
    
}
