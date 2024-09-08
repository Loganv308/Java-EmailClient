package com.emailclient;

import java.util.ArrayList;
import java.util.List;

import com.emailclient.controller.persistance.PersistenceAccess;
import com.emailclient.controller.persistance.ValidAccount;
import com.emailclient.controller.services.LoginService;
import com.emailclient.model.EmailAccount;
import com.emailclient.view.ViewFactory;

import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Needs to extend the JavaFX Application class to start the entire program
public class Launcher extends Application{
    // public static void Main is the main starting method of this whole class
    public static void main(String[] args){
        Application.launch(args);
    }

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    // Override is needed here because this method is present in another class this one is using.
    // Therefore, having @Override will set it to work with just this class
    @Override
    // start method starts the entire program
    public void start(Stage stage) throws Exception {
        
        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistance();
        if(validAccountList.size() > 0) {
            viewFactory.showMainWindow();   
            for (ValidAccount validAccount: validAccountList) {
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }


    // The "stop" function works on program exit. (When you hit the exit button)
    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for(EmailAccount emailAccount: emailManager.getEmailAccounts()) {
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistance(validAccountList);
    }
}
