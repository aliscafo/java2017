package ru.spbau.erokhina.tictactoe;

/**
 * Class that stores game states and statistics.
 */
class GameState {
    private static String[] turns = {"x", "o"};
    private static String GAME_STATE = "menu";
    private static String PLAY_MODE = "";
    private static int WHOSE_TURN = 0;
    private static String firstPlayer;
    private static String secondPlayer;
    private static Integer scoreFirst = 0;
    private static Integer scoreSecond = 0;
    private static Integer scoreTies = 0;

    /**
     * Sets the state of the game: "play" or "round_ends".
     * @param newState new state ("play" or "round_ends").
     */
    static void setGameState(String newState) {
        GAME_STATE = newState;
    }

    /**
     * Gets the state of the game: "play" or "round_ends".
     * @return the state of the game: "play" or "round_ends".
     */
    static String getGameState() {
        return GAME_STATE;
    }

    /**
     * Sets the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     * @param newMode new mode ("hot_seat", "easy_bot" or "smart_bot")
     */
    static void setPlayMode(String newMode) {
        PLAY_MODE = newMode;
    }

    /**
     * Gets the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     * @return the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     */
    static String getPlayMode() {
        return PLAY_MODE;
    }

    /**
     * Changes next turn.
     */
    static void nextTurn() {
        WHOSE_TURN = 1 - WHOSE_TURN;
    }

    /**
     * Gets next turn.
     * @return next turn ("x" or "o")
     */
    static String getWhoseTurn() {
        return turns[WHOSE_TURN];
    }

    /**
     * Sets the turn.
     * @param turn new next turn ("x" or "o")
     */
    static void setWhoseTurn(String turn) {
        if (turn.equals("x")) {
            WHOSE_TURN = 0;
        } else {
            WHOSE_TURN = 1;
        }
    }

    /**
     * Sets the name of the first player.
     * @param first the name of the first player
     */
    static void setFirstPlayer(String first) {
        firstPlayer = first;
    }

    /**
     * Gets the name of the first player.
     * @return the name of the first player
     */
    static String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Sets the name of the second player.
     * @param second the name of the second player
     */
    static void setSecondPlayer(String second) {
        secondPlayer = second;
    }

    /**
     * Gets the name of the second player.
     * @return the name of the second player
     */
    static String getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * Increments the score of the first player.
     */
    static void incScoreFirst() {
        scoreFirst++;
    }

    /**
     * Returns the score of the first player.
     * @return the score of the first player
     */
    static Integer getScoreFirst() {
        return scoreFirst;
    }

    /**
     * Increments the score of the second player.
     */
    static void incScoreSecond() {
        scoreSecond++;
    }

    /**
     * Returns the score of the second player.
     * @return the score of the second player
     */
    static Integer getScoreSecond() {
        return scoreSecond;
    }

    /**
     * Increments the number of ties.
     */
    static void incScoreTies() {
        scoreTies++;
    }

    /**
     * Returns the number of ties.
     * @return the number of ties
     */
    static Integer getScoreTies() {
        return scoreTies;
    }

    /**
     * Resets all scores.
     */
    static void resetScore() {
        scoreFirst = 0;
        scoreSecond = 0;
        scoreTies = 0;
    }

}
