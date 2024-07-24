package com.emailclient.controller.services;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

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
            for (int i = multipart.getCount() - 1; i>=0; i--){
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if(isSimpleType(bodyPartContentType)){
                    stringBuffer.append(bodyPart.getContent().toString());
                }
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
