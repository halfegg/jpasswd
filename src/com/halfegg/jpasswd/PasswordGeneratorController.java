package com.halfegg.jpasswd;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PasswordGeneratorController {

    private final char[] SPECIAL_CHAR =
            {'@', '#', '$', '^', '(', ')', '_', '{', '}', '[', ']', '?', ';', '"', '\'', '\\', '|', ':', '<', '>', '&', '!', '~'};

    @FXML
    private VBox root;
    @FXML
    private ChoiceBox<Integer> passwordCountBox;
    @FXML
    private RadioButton upperCaseButton;
    @FXML
    private RadioButton specialCharButton;
    @FXML
    private RadioButton numberButton;
    @FXML
    private TextField passwordField;

    public void initialize() {
        passwordCountBox.getItems().addAll(6, 8, 12, 16, 24, 36, 48, 64);
        passwordCountBox.getSelectionModel().select(1);
    }

    @FXML
    private void generatePassword() {
        passwordField.setText(generate(
                passwordCountBox.getValue(), upperCaseButton.isSelected(), specialCharButton.isSelected(), numberButton.isSelected()));
    }

    @FXML
    private void confirmGeneratedPassword() {
        if (!passwordField.getText().isEmpty()) {
            getAccountPasswordField().setText(passwordField.getText());
            ((Stage) root.getScene().getWindow()).close();
        }
    }

    private String generate(int count, boolean upper, boolean spec, boolean num) {
        var stringBuilder = new StringBuilder();
        if (upper && spec && num) {
            int parts = count / 4, rem = count % 4;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getUpperCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getDigit()); //number
            for (int i = 0; i < parts; ++i) stringBuilder.append(SPECIAL_CHAR[(int) (Math.random() * SPECIAL_CHAR.length)]);
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (upper && spec && !num) {
            int parts = count / 3, rem = count % 3;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getUpperCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(SPECIAL_CHAR[(int) (Math.random() * SPECIAL_CHAR.length)]);
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (upper && num && !spec) {
            int parts = count / 3, rem = count % 3;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getUpperCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getDigit());
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (spec && num && !upper) {
            int parts = count / 3, rem = count % 3;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getDigit());
            for (int i = 0; i < parts; ++i) stringBuilder.append(SPECIAL_CHAR[(int) (Math.random() * SPECIAL_CHAR.length)]);
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (!upper && !spec && !num) {
            for (int i = 0; i < count; ++i) stringBuilder.append(getLowerCaseChar());
        }
        if (upper && !spec && !num) {
            int parts = count / 2, rem = count % 2;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getUpperCaseChar());
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (spec && !upper && !num) {
            int parts = count / 2, rem = count % 2;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(SPECIAL_CHAR[(int) (Math.random() * SPECIAL_CHAR.length)]);
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        if (num && !upper && !spec) {
            int parts = count / 2, rem = count % 2;
            for (int i = 0; i < parts; ++i) stringBuilder.append(getLowerCaseChar());
            for (int i = 0; i < parts; ++i) stringBuilder.append(getDigit());
            if (rem > 0) {
                for (int i = 0; i < rem; ++i) stringBuilder.append(getLowerCaseChar());
            }
        }
        return shuffle(stringBuilder.toString());
    }

    private String shuffle(String input){
        var chars = new ArrayList<>();
        for(char c : input.toCharArray()){
            chars.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(!chars.isEmpty()){
            int rand = (int) (Math.random() * chars.size());
            output.append(chars.remove(rand));
        }
        return output.toString();
    }

    private char getLowerCaseChar() {
        return (char)((int) (Math.random() * 26) + 97);
    }

    private char getUpperCaseChar() {
        return (char)((int) (Math.random() * 26) + 65);
    }

    private int getDigit() {
        return (int) (Math.random() * 10);
    }

    private TextField getAccountPasswordField() {
//        var stage = (Stage) root.getScene().getWindow();
//        var owner = (Stage) stage.getOwner();
//        var anchorPane = (AnchorPane) owner.getScene().getRoot();
//        var vBox = (VBox) anchorPane.getChildren().get(0);
//        var vBoxChild = (VBox) vBox.getChildren().get(3);
//        return (TextField) vBoxChild.getChildren().get(1);
        return (TextField) ((VBox) ((VBox) ((AnchorPane) (((Stage) root.getScene().getWindow())
                .getOwner()).getScene().getRoot()).getChildren().get(0)).getChildren().get(3)).getChildren().get(1);
    }

}

