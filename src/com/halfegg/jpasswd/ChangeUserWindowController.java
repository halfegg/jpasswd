package com.halfegg.jpasswd;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeUserWindowController {

    @FXML
    private AnchorPane root;
    @FXML
    private PasswordField userId;
    @FXML
    private PasswordField newUserId;

    public void initialize() {

    }

    @FXML
    private void changeUserId() {
        try {
            var databaseManager = new DatabaseManager();
            if (userId.getText().equals(databaseManager.getUserId())) {
                if (databaseManager.changeUserId(newUserId.getText())) {
                    databaseManager.close();
                    userId.clear();
                    newUserId.clear();
                    var stage = (Stage) root.getScene().getWindow();
                    var notification = new Notification(stage, "User ID updated.");
                    notification.show();
                }
            }
        } catch (SQLException ex) {
            ExceptionLogger.log(ChangeUserWindowController.class.getName(), "changeUserId()", ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void backToList() {
        try {
            JPasswd.setRoot("resources/main-window.fxml").setTitle("jpasswd");
        } catch (IOException ex) {
            ExceptionLogger.log(ChangeUserWindowController.class.getName(), "backToList()", ex);
            ex.printStackTrace();
        }
    }

}
