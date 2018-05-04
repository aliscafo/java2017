package ru.spbau.erokhina.findpair;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that provides methods which are responsible for UI Controls behavior.
 */
public class Controller {
    private Button[][] buttons = new Button[50][50];

    /**
     * OnAction method for Start Button.
     * @param actionEvent given action
     */
    public void startButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("game_field.fxml"));

        int n = GameState.getFieldSize();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Button tmp = new Button();

                tmp.setMaxHeight(100);
                tmp.setId(Integer.toString(i) + " " + Integer.toString(j));

                tmp.setOnMouseClicked(event -> {
                    Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();

                    if (GameState.getGameState().equals("finished")) {
                        return;
                    }

                    String cellId = ((Button) event.getSource()).getId();

                    String[] coordinates = cellId.split(" ");
                    int i_coordinate = Integer.parseInt(coordinates[0]);
                    int j_coordinate = Integer.parseInt(coordinates[1]);

                    if (GameState.getGameState().equals("first_step")) {
                        for (int ii = 0; ii < GameState.getFieldSize(); ii++) {
                            for (int jj = 0; jj < GameState.getFieldSize(); jj++) {
                                if (!GameState.ifIsOpen(ii, jj)) {
                                    buttons[ii][jj].setText("");
                                }
                            }
                        }

                        GameState.setNowConsideredI(i_coordinate);
                        GameState.setNowConsideredJ(j_coordinate);

                        GameState.setGameState("second_step");
                    } else if (GameState.getGameState().equals("second_step")) {
                        int considered_i = GameState.getNowConsideredI();
                        int considered_j = GameState.getNowConsideredJ();

                        if (i_coordinate == considered_i && j_coordinate == considered_j) {
                            return;
                        }

                        if (GameState.numberOnField(considered_i, considered_j) ==
                                GameState.numberOnField(i_coordinate, j_coordinate)) {
                            GameState.setIsOpen(i_coordinate, j_coordinate);
                            GameState.setIsOpen(considered_i, considered_j);
                        }

                        GameState.setGameState("first_step");
                    }

                    int hiddenNumber = GameState.numberOnField(i_coordinate, j_coordinate);
                    tmp.setText(Integer.toString(hiddenNumber));

                    GameState.checkAllOpening();

                    if (GameState.getGameState().equals("finished")) {
                        TextField result = (TextField) stage1.getScene().lookup("#game_result");
                        result.setText("Congratulations!");
                    }
                });

                buttons[i][j] = tmp;
            }
        }

        HBox[] field = new HBox[n];

        for (int i = 0; i < n; i++) {
            field[i] = new HBox();

            for (int j = 0; j < n; j++) {
                HBox.setHgrow(buttons[i][j], Priority.ALWAYS);
                buttons[i][j].prefWidthProperty().bind(field[i].widthProperty().divide(n));

                field[i].getChildren().add(buttons[i][j]);
            }
        }

        VBox vBox = (VBox) panel.lookup("#field");

        for (HBox hBox : field) {
            VBox.setVgrow(hBox, Priority.ALWAYS);
            vBox.getChildren().add(hBox);
        }

        GameState.fieldInitialize();
        GameState.isOpenInitialize();

        Scene scene = new Scene(panel, 600, 400);
        stage.setScene(scene);
    }

    private void gameProcess() {

    }

    /**
     * OnAction method for Exit Button.
     */
    public void exitButtonOnAction() {
        Platform.exit();
    }


    public void menuOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("start_window.fxml"));

        Scene scene = new Scene(panel, 600, 400);
        stage.setScene(scene);
    }
}
