package com.halfegg.jpasswd;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

    private StringProperty accountName;
    private StringProperty userName;
    private StringProperty emailAdd;
    private StringProperty password;

    public Account() {}

    public Account(String accountName, String userName, String emailAdd, String password) {
        setAccountName(accountName);
        setUserName(userName);
        setEmailAdd(emailAdd);
        setPassword(password);
    }

    public StringProperty accountNameProperty() {
        if (accountName == null) accountName = new SimpleStringProperty(this, "accountName");
        return  accountName;
    }

    public StringProperty userNameProperty() {
        if (userName == null) userName = new SimpleStringProperty(this, "userName");
        return userName;
    }

    public StringProperty emailAddProperty() {
        if (emailAdd == null) emailAdd = new SimpleStringProperty(this, "emailAdd");
        return emailAdd;
    }

    public StringProperty passwordProperty() {
        if (password == null) password = new SimpleStringProperty(this, "password");
        return password;
    }

    public void setAccountName(String accountName) {
        accountNameProperty().set(accountName);
    }

    public void setUserName(String userName) {
        userNameProperty().set(userName);
    }

    public void setEmailAdd(String emailAdd) {
        emailAddProperty().set(emailAdd);
    }

    public void setPassword(String password) {
        passwordProperty().set(password);
    }

    public String getAccountName() {
        return accountNameProperty().get();
    }

    public String getUserName() {
        return userNameProperty().get();
    }

    public String getEmailAdd() {
        return emailAddProperty().get();
    }

    public String getPassword() {
        return passwordProperty().get();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountName=" + accountName.get() +
                ", userName=" + userName.get() +
                ", emailAdd=" + emailAdd.get() +
                ", password=" + password.get() +
                '}';
    }
}
