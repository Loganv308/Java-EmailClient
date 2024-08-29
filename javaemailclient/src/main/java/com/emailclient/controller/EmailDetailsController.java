package com.emailclient.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.Desktop;

import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.MessageRendererService;
import com.emailclient.model.EmailMessage;
import com.emailclient.view.ViewFactory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

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

        try {
            loadAttachments(emailMessage);
        } catch (MessagingException e) {
            
            e.printStackTrace();
        }

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage) throws MessagingException {
        if(emailMessage.hasAttachments()){
            for(MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()) {
                try {
                    AttachmentButton button = new AttachmentButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(button);
                } catch(MessagingException e) {
                    e.printStackTrace();
                }
            }
        } else {
            attachmentLabel.setText("");
        }
    }
    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadFilePath;
        
        public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException{
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();
            
            this.setOnAction(e -> downloadAttachment());
        }

        private void downloadAttachment() {
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e -> {
                colorGreen();
                this.setOnAction(e2 -> {
                    File file = new File(downloadFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()) {
                        try {
                            desktop.open(file);
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                    }
                });
            });
        }

        private void colorBlue() {
            this.setStyle("-fx-background-color: Blue");
        }
        private void colorGreen() {
            this.setStyle("-fx-background-color: Green");
        }
    }
}
