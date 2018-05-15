package ru.spbau.erokhina.tictactoe;

/**
 * Class that stores game states and statistics.
 */
class GameState {
    private static final GameState instance = new GameState();
    
    private Character[] turns = {'x', 'o'};
    private CurGameState GAME_STATE = CurGameState.MENU;
    private PlayMode PLAY_MODE = PlayMode.EMPTY;
    private int WHOSE_TURN = 0;
    private String firstPlayer;
    private String secondPlayer;
    private Integer scoreFirst = 0;
    private Integer scoreSecond = 0;
    private Integer scoreTies = 0;

    private GameState() {
    }

    /**
     * Returns singleton instance of GameState.
     * @return singleton instance of GameState
     */
    public static GameState getInstance() {
        return instance;
    }

    /**
     * Represents current state of game.
     */
    enum CurGameState {MENU, PLAY, ROUND_ENDS}

    /**
     * Represents current play mode.
     */
    enum PlayMode {EMPTY, HOT_SEAT, EASY_BOT, SMART_BOT}

    /**
     * Sets the state of the game: "play" or "round_ends".
     * @param newState new state ("play" or "round_ends").
     */
    void setGameState(CurGameState newState) {
        GAME_STATE = newState;
    }

    /**
     * Gets the state of the game: "play" or "round_ends".
     * @return the state of the game: "play" or "round_ends".
     */
    CurGameState getGameState() {
        return GAME_STATE;
    }

    /**
     * Sets the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     * @param newMode new mode ("hot_seat", "easy_bot" or "smart_bot")
     */
    void setPlayMode(PlayMode newMode) {
        PLAY_MODE = newMode;
    }

    /**
     * Gets the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     * @return the mode of the game: "hot_seat", "easy_bot" or "smart_bot".
     */
    PlayMode getPlayMode() {
        return PLAY_MODE;
    }

    /**
     * Changes next turn.
     */
    void nextTurn() {
        WHOSE_TURN = 1 - WHOSE_TURN;
    }

    /**
     * Gets next turn.
     * @return next turn ("x" or "o")
     */
    String getWhoseTurn() {
        return turns[WHOSE_TURN].toString();
    }

    /**
     * Sets the turn.
     * @param turn new next turn ("x" or "o")
     */
    void setWhoseTurn(String turn) {
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
    void setFirstPlayer(String first) {
        firstPlayer = first;
    }

    /**
     * Gets the name of the first player.
     * @return the name of the first player
     */
    String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Sets the name of the second player.
     * @param second the name of the second player
     */
    void setSecondPlayer(String second) {
        secondPlayer = second;
    }

    /**
     * Gets the name of the second player.
     * @return the name of the second player
     */
    String getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * Increments the score of the first player.
     */
    void incScoreFirst() {
        scoreFirst++;
    }

    /**
     * Returns the score of the first player.
     * @return the score of the first player
     */
    Integer getScoreFirst() {
        return scoreFirst;
    }

    /**
     * Increments the score of the second player.
     */
    void incScoreSecond() {
        scoreSecond++;
    }

    /**
     * Returns the score of the second player.
     * @return the score of the second player
     */
    Integer getScoreSecond() {
        return scoreSecond;
    }

    /**
     * Increments the number of ties.
     */
    void incScoreTies() {
        scoreTies++;
    }

    /**
     * Returns the number of ties.
     * @return the number of ties
     */
    Integer getScoreTies() {
        return scoreTies;
    }

    /**
     * Resets all scores.
     */
    void resetScore() {
        scoreFirst = 0;
        scoreSecond = 0;
        scoreTies = 0;
        GAME_STATE = CurGameState.MENU;
        PLAY_MODE = PlayMode.EMPTY;
        WHOSE_TURN = 0;
    }

}
