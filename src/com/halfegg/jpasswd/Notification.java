package com.halfegg.jpasswd;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {

    private final Stage stage;

    public Notification(String message) {
        stage = new Stage();
        var text = new Text(message);
        text.setFont(Font.font("System", 14.0));
        text.setFill(Color.valueOf("#ffff00"));
        var vBox = new VBox(text);
        vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#3e3e3e"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setPadding(new Insets(20.0, 15.0, 20.0, 15.0));
        vBox.setBorder(new Border(new BorderStroke(Color.valueOf("#ffff00"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2), new Insets(2))));
        stage.setScene(new Scene(vBox));
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void show() {
        stage.show();
        var pauseTransition = new PauseTransition(Duration.seconds(1.8));
        pauseTransition.setOnFinished(e -> stage.hide());
        pauseTransition.play();
    }
}
