package com.emailclient.view;

import java.io.IOException;
import java.util.ArrayList;

import com.emailclient.EmailManager;
import com.emailclient.controller.BaseController;
import com.emailclient.controller.ComposeMessageController;
import com.emailclient.controller.EmailDetailsController;
import com.emailclient.controller.LoginWindowController;
import com.emailclient.controller.MainWindowController;
import com.emailclient.controller.OptionsWindowController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {

    private EmailManager emailManager;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;
    // Constructor
    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    public boolean isMainViewInitialized() {
        return mainViewInitialized;
    }
    
    // View options handling
    private ColorTheme colorTheme = ColorTheme.DARK;
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
        mainViewInitialized = true;
    }

    public void showOptionsWindow() {
        System.out.println("Options Window called");
    
        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");

        initializeStage(controller);
    }

    public void showComposeMessageWindow() {
        System.out.println("Compose Message Window called");
    
        BaseController controller = new ComposeMessageController(emailManager, this, "ComposeMessageWindow.fxml");

        initializeStage(controller);
    }

    public void showEmailDetailsWindow() {
        System.out.println("Email Details Window called");
    
        BaseController controller = new EmailDetailsController(emailManager, this, "EmailDetailsWindow.fxml");

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
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        for(Stage stage: activeStages) {
            Scene scene = stage.getScene();
            // Handle the CSS
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
