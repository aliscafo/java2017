package ru.spbau.erokhina.findpair;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that is responsible for launching Application.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        setMenu(primaryStage);

        primaryStage.setTitle("FindPair Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setMenu(Stage primaryStage) throws IOException {
        Parent panel = FXMLLoader.load(getClass().getResource("start_window.fxml"));

        Scene scene = new Scene(panel, 600, 400);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        if (args.length != 0 && (Integer.parseInt(args[0]) > 30 || Integer.parseInt(args[0]) % 2 != 0)) {
            System.out.println("Field size may be from 2x2 to 30x30 and should be even.");
            return;
        }

        if (args.length != 0) {
            GameState.setFieldSize(Integer.parseInt(args[0]));
        }

        launch(args);
    }
}
