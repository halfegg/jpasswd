package com.halfegg.jpasswd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final Connection connection;
    private final Statement statement;

    private final String ACCOUNTS_TABLE = "accounts";
    private final String USERID_TABLE = "userId";
    private final String ACCOUNT_NAME_COLUMN = "accountName";
    private final String USER_NAME_COLUMN = "userName";
    private final String EMAIL_ADD_COLUMN = "emailAdd";
    private final String PASSWORD_COLUMN = "password";
    private final String ID_COLUMN = "id";

    public DatabaseManager() throws SQLException {
        var db = Paths.get("database");
        if (Files.notExists(db)) try {
            Files.createDirectory(db);
        } catch (IOException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "DatabaseManager()", ex);
            ex.printStackTrace();
        }

        connection = DriverManager.getConnection("jdbc:sqlite:database/jpasswd.db");
        statement = connection.createStatement();
        statement.execute("create table if not exists " + ACCOUNTS_TABLE + " (" +
                ACCOUNT_NAME_COLUMN + " text, " + USER_NAME_COLUMN + " text, " + EMAIL_ADD_COLUMN + " text, " + PASSWORD_COLUMN + " text)");
        statement.execute("create table if not exists " + USERID_TABLE + " (" + ID_COLUMN + " text)");
        createPrimaryUserId();
    }

    private void createPrimaryUserId() {
        try {
            var resultSet = statement.executeQuery("select count(" + ID_COLUMN + ") from " + USERID_TABLE);
            if (resultSet.getInt(1) == 0) {
                statement.execute("insert into " + USERID_TABLE + " (" + ID_COLUMN + ") values ('" +
                        new String(Crypto.encrypt("admin".getBytes())) + "')");
            }
        } catch (SQLException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "createPrimaryUserId()", ex);
            ex.printStackTrace();
        }
    }

    public boolean changeUserId(String userId) {
        try {
            statement.execute("update "  + USERID_TABLE + " set " + ID_COLUMN + "='" +
                    new String(Crypto.encrypt(userId.getBytes())) + "'");
            return true;
        } catch (SQLException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "changeUserId(String)", ex);
            ex.printStackTrace();
            return false;
        }
    }

    public String getUserId() throws SQLException {
        String result = null;
        var resultSet = statement.executeQuery("select " + ID_COLUMN + " from " + USERID_TABLE);
        while (resultSet.next()) {
            result = new String(Crypto.decrypt(resultSet.getString(1).getBytes()));
        }
        return result;
    }

    public boolean add(Account acc) {
        try {
            statement.execute("insert into " + ACCOUNTS_TABLE + " (" +
                    ACCOUNT_NAME_COLUMN + ", " + USER_NAME_COLUMN + ", " + EMAIL_ADD_COLUMN + ", " + PASSWORD_COLUMN + ") " +
                    "values ('" + acc.getAccountName() + "', '" + acc.getUserName() + "', '" + acc.getEmailAdd() + "', '"
                    + new String(Crypto.encrypt(acc.getPassword().getBytes())) + "')");
            return true;
        } catch (SQLException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "add(Account)", ex);
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remove(String password) {
        try {
            statement.execute("delete from " + ACCOUNTS_TABLE + " where " + PASSWORD_COLUMN + " like '%" + password +"%'");
            return true;
        } catch (SQLException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "remove(String)", ex);
            ex.printStackTrace();
            return false;
        }
    }

    public ObservableList<Account> getAccountData() {
        ObservableList<Account> accountsList = FXCollections.observableArrayList();
        try {
            var resultSet = statement.executeQuery("select * from " + ACCOUNTS_TABLE + "");
            while (resultSet.next()) {
                accountsList.add(new Account(resultSet.getString(ACCOUNT_NAME_COLUMN), resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getString(EMAIL_ADD_COLUMN), resultSet.getString(PASSWORD_COLUMN)));
            }
            return accountsList;
        } catch (SQLException ex) {
            ExceptionLogger.log(DatabaseManager.class.getName(), "getAccountData()", ex);
            ex.printStackTrace();
            return accountsList;
        }
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}
