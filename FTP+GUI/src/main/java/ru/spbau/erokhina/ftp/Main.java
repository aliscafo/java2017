package ru.spbau.erokhina.ftp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that is responsible for launching FTP Application.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        setMenu(primaryStage);

        primaryStage.setTitle("FTP");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setMenu(Stage primaryStage) throws IOException {
        Parent panel = FXMLLoader.load(Main.class.getResource("/enter_host.fxml"));

        Scene scene = new Scene(panel, 600, 590);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


