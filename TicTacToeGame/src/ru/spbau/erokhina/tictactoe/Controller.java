package ru.spbau.erokhina.tictactoe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static ru.spbau.erokhina.tictactoe.Utils.checkWin;
import static ru.spbau.erokhina.tictactoe.Utils.makeMoveEasyBot;
import static ru.spbau.erokhina.tictactoe.Utils.makeMoveSmartBot;

/**
 * Class that provides methods which are responsible for UI Controls behavior.
 */
public class Controller {
    /**
     * OnAction method for Start Button.
     * @param actionEvent given action
     */
    public void startButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("choose_mode.fxml"));

        Scene scene = new Scene(panel, 600, 400);
        stage.setScene(scene);
    }

    /**
     * OnAction method for Exit Button.
     */
    public void exitButtonOnAction() {
        Platform.exit();
    }

    /**
     * OnAction method for Menu Button.
     * @param actionEvent given action
     */
    public void menuOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("menu.fxml"));

        GameState.resetScore();

        Scene scene = new Scene(panel, 600, 400);
        stage.setScene(scene);
    }

    /**
     * OnAction method for HotSeat Option.
     * @param actionEvent given action
     */
    public void hotSeatOnAction(ActionEvent actionEvent) throws IOException {
        GameState.setFirstPlayer("Player 1");
        GameState.setSecondPlayer("Player 2");
        GameState.setGameState("play");
        GameState.setPlayMode("hot_seat");
        GameState.setWhoseTurn("x");

        setStartField(actionEvent);
    }

    /**
     * OnAction method for EasyBot Option.
     * @param actionEvent given action
     */
    public void easyBotOnAction(ActionEvent actionEvent) throws IOException {
        GameState.setFirstPlayer("You");
        GameState.setSecondPlayer("Bot");
        GameState.setGameState("play");
        GameState.setPlayMode("easy_bot");
        GameState.setWhoseTurn("x");

        setStartField(actionEvent);
    }

    /**
     * OnAction method for SmartBot Option.
     * @param actionEvent given action
     */
    public void smartBotOnAction(ActionEvent actionEvent) throws IOException {
        GameState.setFirstPlayer("You");
        GameState.setSecondPlayer("Bot");
        GameState.setGameState("play");
        GameState.setPlayMode("smart_bot");
        GameState.setWhoseTurn("x");

        setStartField(actionEvent);
    }

    /**
     * OnAction method for cell Button.
     * @param actionEvent given action
     */
    public void onActionCell(ActionEvent actionEvent) throws InterruptedException {
        if (GameState.getGameState().equals("round_ends")) {
            return;
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        String cellId = ((Button) actionEvent.getSource()).getId();
        Button button = (Button) stage.getScene().lookup("#" + cellId);

        if (button.getText().equals("")) {
            button.setText(GameState.getWhoseTurn());
            GameState.nextTurn();

            if (!GameState.getPlayMode().equals("hot_seat")) {
                if (checkWin(getArrayFromStage(stage)).equals("")) {
                    Integer nextCell = null;

                    switch (GameState.getPlayMode()) {
                        case "easy_bot":
                            nextCell = makeMoveEasyBot(getArrayFromStage(stage));
                            break;
                        case "smart_bot":
                            nextCell = makeMoveSmartBot(getArrayFromStage(stage));
                            break;
                    }

                    Button buttonBot = (Button) stage.getScene().lookup("#cell" + nextCell);
                    String turn = GameState.getWhoseTurn();
                    buttonBot.setText(turn);

                    GameState.nextTurn();
                }
            }

        }

        String curResult = checkWin(getArrayFromStage(stage));

        if (curResult.equals("")) {
            return;
        }

        TextField textField = (TextField) stage.getScene().lookup("#game_result");
        GameState.setGameState("round_ends");

        switch (curResult) {
            case "x":
                textField.setText(GameState.getFirstPlayer() + " win!");
                GameState.incScoreFirst();
                break;
            case "o":
                textField.setText(GameState.getSecondPlayer() + " win!");
                GameState.incScoreSecond();
                break;
            case "tie":
                textField.setText("Tie!");
                GameState.incScoreTies();
                break;
        }

    }

    /**
     * OnAction method for Restart Button.
     * @param mouseEvent given mouseEvent
     */
    public void restartOnAction(MouseEvent mouseEvent) throws IOException {
        GameState.setGameState("play");

        switch (GameState.getPlayMode()) {
            case "hot_seat":
                hotSeatOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
            case "easy_bot":
                easyBotOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
            case "smart_bot":
                smartBotOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
        }
    }

    private void clearField(Scene scene) {
        for (int i = 1; i <= 9; i++) {
            ((Button)scene.lookup("#cell" + i)).setText("");
        }
    }

    private void setStartField(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("game_field.fxml"));
        Scene scene = new Scene(panel, 600, 400);

        TextField playerX = (TextField) scene.lookup("#player_x");
        playerX.setText(GameState.getFirstPlayer());

        TextField playerO = (TextField) scene.lookup("#player_o");
        playerO.setText(GameState.getSecondPlayer());

        TextField ties = (TextField) scene.lookup("#ties");
        ties.setText("Ties");

        TextField scoreX = (TextField) scene.lookup("#score_x");
        scoreX.setText(GameState.getScoreFirst().toString());

        TextField scoreO = (TextField) scene.lookup("#score_o");
        scoreO.setText(GameState.getScoreSecond().toString());

        TextField scoreTies = (TextField) scene.lookup("#score_ties");
        scoreTies.setText(GameState.getScoreTies().toString());

        clearField(scene);
        stage.setScene(scene);
    }

    private ArrayList<String> getArrayFromStage(Stage stage) {
        ArrayList<String> values = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            values.add(((Button) stage.getScene().lookup("#cell" + i)).getText());
        }

        return values;
    }
}
