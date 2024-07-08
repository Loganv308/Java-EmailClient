package com.emailclient;

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
        // // Button variable necessary to put the button on the frame
        // Button button = new Button("Click!");
        // // Below this is the setOnAction method for the button. When the button is clicked, 
        // // It will do whatever action is set. In this case, System.out.println("button clicked");
        // button.setOnAction(e -> {
        //     System.out.println("Button Clicked!");
        // });

        // // This will hold the controller objects (Like the button above)
        // StackPane stackPane = new StackPane();
        // stackPane.getChildren().add(button);

        Parent parent = FXMLLoader.load(getClass().getResource("view/LoginWindow.fxml"));

        // Scene is needed to set the JavaFX scene with the given stackPane, and width + height args. 
        Scene scene = new Scene(parent, 510, 325);
        stage.setScene(scene);

        // Shows the final stage 
        stage.show();
    }
}
