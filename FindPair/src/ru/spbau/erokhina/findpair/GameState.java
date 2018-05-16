package ru.spbau.erokhina.findpair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that stores game states and statistics.
 */
class GameState {
    private static int fieldSize = 2;
    private static String GAME_STATE = "first_step";
    private static int nowConsideredI;
    private static int nowConsideredJ;

    private static int[][] fieldContent;
    private static boolean[][] isOpen;

    public static int getNowConsideredI() {
        return nowConsideredI;
    }

    public static int getNowConsideredJ() {
        return nowConsideredJ;
    }

    public static void setNowConsideredI(int nowConsideredI) {
        GameState.nowConsideredI = nowConsideredI;
    }

    public static void setNowConsideredJ(int nowConsideredJ) {
        GameState.nowConsideredJ = nowConsideredJ;
    }

    static void fieldInitialize() {
        fieldContent = new int[fieldSize][fieldSize];
        List<Cell> freeCells = new ArrayList<>();

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Cell cell = new Cell(i, j);
                freeCells.add(cell);
            }
        }

        for (int i = 0; i < fieldSize * fieldSize / 2; i++) {
            Cell a = freeCells.get(ThreadLocalRandom.current().nextInt(0, freeCells.size()));
            freeCells.remove(a);

            Cell b = freeCells.get(ThreadLocalRandom.current().nextInt(0, freeCells.size()));;
            freeCells.remove(b);

            fieldContent[a.getI()][a.getJ()] = i;
            fieldContent[b.getI()][b.getJ()] = i;
        }
    }

    static void isOpenInitialize() {
        isOpen = new boolean[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                isOpen[i][j] = false;
            }
        }
    }

    static void checkAllOpening() {
        boolean flag = true;

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (!isOpen[i][j]) {
                    flag = false;
                }
            }
        }

        if (flag) {
            GAME_STATE = "finished";
        }
    }

    static void setIsOpen(int i, int j) {
        isOpen[i][j] = true;
    }

    static boolean ifIsOpen(int i, int j) {
        return isOpen[i][j];
    }

    static int numberOnField(int i, int j) {
        return fieldContent[i][j];
    }

    static private class Cell {
        private int i, j;

        public Cell(int a, int b) {
            i = a;
            j = b;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }

    static void setFieldSize(int size) {
        fieldSize = size;
    }

    static int getFieldSize() {
        return fieldSize;
    }

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
}
