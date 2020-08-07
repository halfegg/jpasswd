package com.halfegg.jpasswd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainWindowController {

    @FXML
    private VBox root;
    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, String> accountNameColumn;
    @FXML
    private TableColumn<Account, String> userNameColumn;
    @FXML
    private TableColumn<Account, String> emailAddColumn;
    @FXML
    private TableColumn<Account, String> passwordColumn;

    public void initialize() {
        ExceptionLogger.createFiles();
        populateTableView();
    }

    @FXML
    private void addAccount() {
        try {
            JPasswd.setRoot("resources/account-window.fxml").setTitle("Add Password Account");
        } catch (IOException ex) {
            ExceptionLogger.log(MainWindowController.class.getName(), "addAccount()", ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void changeUserID() {
        try {
            JPasswd.setRoot("resources/change-user-window.fxml").setTitle("Change User ID");
        } catch (IOException ex) {
            ExceptionLogger.log(MainWindowController.class.getName(), "changeUserID()", ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    @FXML
    private void about() {
        try {
            var stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/about-window.fxml"))));
            stage.setResizable(false);
            stage.setTitle("About");
            stage.show();
        } catch (IOException ex) {
            ExceptionLogger.log(MainWindowController.class.getName(), "about()", ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void getPassword() {
        if (tableView.getSelectionModel().getSelectedIndex() >= 0) {
            var stage = (Stage) root.getScene().getWindow();
            var authenticationDialog = new AuthenticationDialog(stage);
            authenticationDialog.show();
            authenticationDialog.getConfirmButton().setOnAction(e -> {
                try {
                    var databaseManager = new DatabaseManager();
                    if (authenticationDialog.getPasswordField().getText().equals(databaseManager.getUserId())) {
                        databaseManager.close();
                        var account = tableView.getSelectionModel().getSelectedItem();
                        var clipboard = Clipboard.getSystemClipboard();
                        var clipboardContent = new ClipboardContent();
                        clipboardContent.putString(new String(Crypto.decrypt(account.getPassword().getBytes())));
                        if (clipboard.setContent(clipboardContent)) {
                            authenticationDialog.close();
                            var notification = new Notification(account.getAccountName() + "'s password copied to clipboard.");
                            notification.show();
                        }
                    }
                } catch (SQLException ex) {
                    ExceptionLogger.log(MainWindowController.class.getName(), "getPassword()", ex);
                    ex.printStackTrace();
                }
            });
        }
    }

    @FXML
    private void removeAccount() {
        if (tableView.getSelectionModel().getSelectedIndex() >= 0) {
            var stage = (Stage) root.getScene().getWindow();
            var authenticationDialog = new AuthenticationDialog(stage);
            authenticationDialog.show();
            authenticationDialog.getConfirmButton().setOnAction(e -> {
                try {
                    var databaseManager = new DatabaseManager();
                    if (authenticationDialog.getPasswordField().getText().equals(databaseManager.getUserId())) {
                        var account = tableView.getSelectionModel().getSelectedItem();
                        if (databaseManager.remove(account.getPassword())) {
                            var accountList = databaseManager.getAccountData();
                            databaseManager.close();
                            if (accountList.isEmpty())
                                tableView.getItems().clear();
                            else
                                populateTableView();
                            authenticationDialog.close();
                            var notification = new Notification(account.getAccountName() + "'s account removed.");
                            notification.show();
                        }
                    }
                } catch (SQLException ex) {
                    ExceptionLogger.log(MainWindowController.class.getName(), "removeAccount()", ex);
                    ex.printStackTrace();
                }
            });
        }
    }

    private void populateTableView() {
        tableView.setPlaceholder(new Text("No accounts in the table."));
        try {
            var databaseManager = new DatabaseManager();
            var accountList = databaseManager.getAccountData();
            databaseManager.close();
            if (!accountList.isEmpty()) {
                tableView.setItems(accountList);
                accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
                userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
                emailAddColumn.setCellValueFactory(new PropertyValueFactory<>("emailAdd"));
                passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            }
        } catch (SQLException ex) {
            ExceptionLogger.log(MainWindowController.class.getName(), "populateTableView()", ex);
            ex.printStackTrace();
        }
    }

}
