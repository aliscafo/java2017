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

/**
 * Class that provides methods which are responsible for UI Controls behavior.
 */
public class Controller {
    private GameField gameField = new GameField();

    /**
     * OnAction method for Start Button.
     * @param actionEvent given event
     */
    public void startButtonOnAction(ActionEvent actionEvent) throws IOException {
        newScene(actionEvent, "choose_mode.fxml");
    }

    /**
     * OnAction method for Menu Button.
     * @param actionEvent given event
     */
    public void menuButtonOnAction(ActionEvent actionEvent) throws IOException {
        newScene(actionEvent, "menu.fxml");
    }

    private void newScene(ActionEvent actionEvent, String resource) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource(resource));

        GameState.getInstance().resetScore();

        Scene scene = new Scene(panel, 600, 400);
        stage.setScene(scene);
    }

    /**
     * OnAction method for Exit Button.
     */
    public void exitButtonOnAction() {
        Platform.exit();
    }

    private void setCommonGameOptions(String player1, String player2, GameState.PlayMode playMode) {
        GameState.getInstance().setFirstPlayer(player1);
        GameState.getInstance().setSecondPlayer(player2);
        GameState.getInstance().setGameState(GameState.CurGameState.PLAY);
        GameState.getInstance().setPlayMode(playMode);
        GameState.getInstance().setWhoseTurn("x");
    }

    /**
     * OnAction method for HotSeat Option.
     * @param actionEvent given action
     */
    public void hotSeatOnAction(ActionEvent actionEvent) throws IOException {
        setCommonGameOptions("Player 1", "Player 2", GameState.PlayMode.HOT_SEAT);
        setStartField(actionEvent);
    }

    /**
     * OnAction method for EasyBot Option.
     * @param actionEvent given action
     */
    public void easyBotOnAction(ActionEvent actionEvent) throws IOException {
        setCommonGameOptions("You", "Bot", GameState.PlayMode.EASY_BOT);
        setStartField(actionEvent);
    }

    /**
     * OnAction method for SmartBot Option.
     * @param actionEvent given action
     */
    public void smartBotOnAction(ActionEvent actionEvent) throws IOException {
        setCommonGameOptions("You", "Bot", GameState.PlayMode.SMART_BOT);
        setStartField(actionEvent);
    }

    /**
     * OnAction method for cell Button.
     * @param actionEvent given action
     */
    public void onActionCell(ActionEvent actionEvent) throws Exception {
        if (GameState.getInstance().getGameState().equals(GameState.CurGameState.ROUND_ENDS)) {
            return;
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        String cellId = ((Button) actionEvent.getSource()).getId();
        Integer cellIdInt = Integer.parseInt(Character.toString(cellId.charAt(cellId.length() - 1)));

        Button button = (Button) stage.getScene().lookup("#" + cellId);

        if (button.getText().equals("")) {
            button.setText(GameState.getInstance().getWhoseTurn());
            gameField.setAt(cellIdInt, GameState.getInstance().getWhoseTurn().charAt(0));
            GameState.getInstance().nextTurn();

            if (!GameState.getInstance().getPlayMode().equals(GameState.PlayMode.HOT_SEAT)) {
                if (gameField.checkWin().equals("")) {
                    Integer nextCell;

                    switch (GameState.getInstance().getPlayMode()) {
                        case EASY_BOT:
                            nextCell = gameField.makeMoveEasyBot();
                            break;
                        case SMART_BOT:
                            nextCell = gameField.makeMoveSmartBot();
                            break;
                        default:
                            throw new Exception("Unknown play mode.");
                    }

                    Button buttonBot = (Button) stage.getScene().lookup("#cell" + nextCell);
                    String turn = GameState.getInstance().getWhoseTurn();
                    buttonBot.setText(turn);
                    gameField.setAt(nextCell, turn.charAt(0));

                    GameState.getInstance().nextTurn();
                }
            }

        }

        String curResult = gameField.checkWin();

        if (curResult.equals("")) {
            return;
        }

        TextField textField = (TextField) stage.getScene().lookup("#game_result");
        GameState.getInstance().setGameState(GameState.CurGameState.ROUND_ENDS);

        switch (curResult) {
            case "x":
                textField.setText(GameState.getInstance().getFirstPlayer() + " win!");
                GameState.getInstance().incScoreFirst();
                break;
            case "o":
                textField.setText(GameState.getInstance().getSecondPlayer() + " win!");
                GameState.getInstance().incScoreSecond();
                break;
            case "tie":
                textField.setText("Tie!");
                GameState.getInstance().incScoreTies();
                break;
        }

    }

    /**
     * OnAction method for Restart Button.
     * @param mouseEvent given mouseEvent
     */
    public void restartOnAction(MouseEvent mouseEvent) throws IOException {
        GameState.getInstance().setGameState(GameState.CurGameState.PLAY);

        switch (GameState.getInstance().getPlayMode()) {
            case HOT_SEAT:
                hotSeatOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
            case EASY_BOT:
                easyBotOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
            case SMART_BOT:
                smartBotOnAction(new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget()));
                break;
        }
    }

    private void clearField(Scene scene) {
        gameField.clear();

        for (int i = 0; i <= 8; i++) {
            ((Button)scene.lookup("#cell" + i)).setText("");
        }
    }

    private void setStartField(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent panel = FXMLLoader.load(getClass().getResource("game_field.fxml"));
        Scene scene = new Scene(panel, 600, 400);

        TextField playerX = (TextField) scene.lookup("#player_x");
        playerX.setText(GameState.getInstance().getFirstPlayer());

        TextField playerO = (TextField) scene.lookup("#player_o");
        playerO.setText(GameState.getInstance().getSecondPlayer());

        TextField ties = (TextField) scene.lookup("#ties");
        ties.setText("Ties");

        TextField scoreX = (TextField) scene.lookup("#score_x");
        scoreX.setText(GameState.getInstance().getScoreFirst().toString());

        TextField scoreO = (TextField) scene.lookup("#score_o");
        scoreO.setText(GameState.getInstance().getScoreSecond().toString());

        TextField scoreTies = (TextField) scene.lookup("#score_ties");
        scoreTies.setText(GameState.getInstance().getScoreTies().toString());

        clearField(scene);
        stage.setScene(scene);
    }
}
