package com.emailclient;

import com.emailclient.view.ViewFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Needs to extend the JavaFX Application class to start the entire program
public class Launcher extends Application{
    // public static void Main is the main starting method of this whole class
    public static void main(String[] args){
        Application.launch(args);
    }

    // Override is needed here because this method is present in another class this one is using.
    // Therefore, having @Override will set it to work with just this class
    @Override
    // start method starts the entire program
    public void start(Stage stage) throws Exception {
        
        ViewFactory viewFactory = new ViewFactory(new EmailManager());

        viewFactory.showLoginWindow();

    }
}
