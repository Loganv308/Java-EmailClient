package com.emailclient.controller.services;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.emailclient.model.EmailMessage;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

public class MessageRendererService extends Service{
    
    private EmailMessage emailMessage;
    // Relates to JavaFX WebEngine
    private WebEngine webEngine;
    // Holds content from WebEngine
    private StringBuffer stringBuffer;
    
    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(event -> {
            displayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    public void displayMessage() {
        webEngine.loadContent(stringBuffer.toString());
    }

    private void loadMessage() throws MessagingException, IOException {
        // Clears the StringBuffer since this will be called a lot
        stringBuffer.setLength(0);
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimpleType(contentType)) {
            stringBuffer.append(message.getContent().toString());
        } else if(isMultipartType(contentType)) {
            Multipart multipart = (Multipart) message.getContent();
            loadMultipart(multipart, stringBuffer);
        }
    }

    private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for (int i = multipart.getCount() - 1; i>=0; i--){
            BodyPart bodyPart = multipart.getBodyPart(i);
            String contentType = bodyPart.getContentType();
            if(isSimpleType(contentType)){
                stringBuffer.append(bodyPart.getContent().toString());
            } else if (isMultipartType(contentType)) {
                Multipart multipart2 = (Multipart) bodyPart.getContent();
                loadMultipart(multipart2, stringBuffer);
            } else if (!isTextPlain(contentType)) {
                // Here we get the attachments
                MimeBodyPart mbp = (MimeBodyPart) bodyPart;

                emailMessage.addAttachment(mbp);
            }
        }
        
    }

    private boolean isSimpleType(String contentType) {
        if(contentType.contains("TEXT/HTML") ||
        contentType.contains("mixed") ||
        contentType.contains("text")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMultipartType(String contentType) {
        if(contentType.contains("multipart")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTextPlain(String contentType) {
        return contentType.contains("TEXT/PLAIN");
    }
    
    @Override
    protected Task createTask(){
        return new Task(){
            @Override
            protected Object call() throws Exception {
                try {
                    loadMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
