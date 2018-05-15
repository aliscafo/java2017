package ru.spbau.erokhina.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that is responsible for launching TicTacToe Application.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        setMenu(primaryStage);

        primaryStage.setTitle("TicTacToe Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setMenu(Stage primaryStage) throws IOException {
        Parent panel = FXMLLoader.load(getClass().getResource("menu.fxml"));

        Scene scene = new Scene(panel, 600, 400);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
