package com.emailclient.view;

import java.io.IOException;

import com.emailclient.EmailManager;
import com.emailclient.controller.BaseController;
import com.emailclient.controller.LoginWindowController;
import com.emailclient.controller.MainWindowController;
import com.emailclient.controller.OptionsWindowController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {

    private EmailManager emailManager;

    // Constructor
    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }
    
    // View options handling
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    // Method to generate Login Stage instead of using the Stage & Scene code originally.
    public void showLoginWindow(){
        System.out.println("Show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");

        initializeStage(controller);
    }

    public void showMainWindow() {
        System.out.println("Main Window called");
    
        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");

        initializeStage(controller);
    }

    public void showOptionsWindow() {
        System.out.println("Main Window called");
    
        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");

        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try {
            parent = fxmlLoader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }
}
