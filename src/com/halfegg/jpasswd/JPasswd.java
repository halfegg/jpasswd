package com.halfegg.jpasswd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JPasswd extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        setRoot("resources/main-window.fxml");
        stage.setTitle("jpasswd");
        stage.show();
    }

    public static Stage setRoot(String fxml) throws IOException {
        var scene = new Scene(loadFXMl(fxml));
        stage.setScene(scene);
        IconsLoader.load(stage);
        if (fxml.matches("(.*main-window.*)"))
            stage.setResizable(true);
        else
            stage.setResizable(false);
        return stage;
    }

    private static Parent loadFXMl(String fxml) throws IOException {
        return new FXMLLoader(JPasswd.class.getResource(fxml)).load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
