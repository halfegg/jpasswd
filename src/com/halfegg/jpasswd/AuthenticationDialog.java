package com.halfegg.jpasswd;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AuthenticationDialog {

    private Stage stage;
    private Stage primaryStage;
    private Parent parent;

    public AuthenticationDialog(Stage primaryStage) {
        try {
            stage = new Stage();
            this.primaryStage = primaryStage;
            parent = FXMLLoader.load(getClass().getResource("resources/authentication-dialog.fxml"));
            var scene = new Scene(parent);
            stage.setScene(scene);
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Authentication Dialog");
        } catch (IOException ex) {
            ExceptionLogger.log(AuthenticationDialog.class.getName(), "AuthenticationDialog(Stage)", ex);
            ex.printStackTrace();
        }
    }

    public PasswordField getPasswordField() {
        return (PasswordField) ((VBox) parent).getChildren().get(2);
    }

    public Button getConfirmButton() {
        return (Button) ((HBox) ((VBox) parent).getChildren().get(3)).getChildren().get(0);
    }

    public void show() {
        stage.show();
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - stage.getWidth() / 2);
        stage.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - stage.getHeight() / 2);
        stage.requestFocus();
    }

    public void close() {
        stage.close();
    }
}
