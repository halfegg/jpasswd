package com.halfegg.jpasswd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class AccountWindowController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField accountNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailAddField;
    @FXML
    private TextField passwordField;

    public void initialize() {

    }

    @FXML
    private void generatePassword() {
        try {
            var stage = new Stage();
            var scene = new Scene(FXMLLoader.load(getClass().getResource("resources/password-generator-window.fxml")));
            var owner = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.initOwner(owner);
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.setTitle("Password Generator");
            stage.show();
            stage.requestFocus();
            stage.setX((owner.getX() + owner.getWidth() / 2) - (stage.getWidth() / 2));
            stage.setY((owner.getY() + owner.getHeight() / 2) - (stage.getHeight() / 2));
        } catch (IOException ex) {
            ExceptionLogger.log(AccountWindowController.class.getName(), "generatePassword()", ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        accountNameField.clear();
        userNameField.clear();
        emailAddField.clear();
        passwordField.clear();
    }

    @FXML
    private void addAccount() {
        if (!accountNameField.getText().isBlank() && !userNameField.getText().isBlank() &&
                !emailAddField.getText().isBlank() && !passwordField.getText().isBlank()) {
            var account = new Account(accountNameField.getText(), userNameField.getText(), emailAddField.getText(), passwordField.getText());
            try {
                var databaseManager = new DatabaseManager();
                if (databaseManager.add(account)) {
                    databaseManager.close();
                    clearFields();
                    var notification = new Notification(account.getAccountName() + "'s account added.");
                    notification.show();
                }

            } catch (SQLException ex) {
                ExceptionLogger.log(AccountWindowController.class.getName(), "addAccount()", ex);
                ex.printStackTrace();
            }
        }
        accountNameField.requestFocus();
    }

    @FXML
    private void backToList() {
        try {
            JPasswd.setRoot("resources/main-window.fxml").setTitle("jpasswd");
        } catch (IOException ex) {
            ExceptionLogger.log(AccountWindowController.class.getName(), "backToList()", ex);
            ex.printStackTrace();
        }
    }

}
