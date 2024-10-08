package com.emailclient.controller.services;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import javax.mail.NoSuchProviderException;

import java.security.Provider.Service;

import javax.mail.*;

import com.emailclient.EmailManager;
import com.emailclient.controller.EmailLoginResult;
import com.emailclient.model.EmailAccount;

import javafx.concurrent.Task;

public class LoginService extends javafx.concurrent.Service<EmailLoginResult>{
    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginResult login(){
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };

        try {
            Thread.sleep(6000);
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            emailAccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                        emailAccount.getAddress(),
                        emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;

        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;

        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;

        } catch (Exception e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<EmailLoginResult>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }
}
