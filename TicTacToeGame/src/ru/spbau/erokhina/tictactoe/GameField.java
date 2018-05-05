package ru.spbau.erokhina.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that provides util function for logic.
 */
class GameField {
    private Character[] field = new Character[9];

    /**
     * Constructor for tests.
     * @param list given values
     */
    GameField(List<Character> list) {
        for (int i = 0; i < 9; i++) {
            field[i] = list.get(i);
        }
    }

    /**
     * Constructor that creates empty field.
     */
    GameField() {
        clear();
    }

    /**
     * Clears field.
     */
    void clear() {
        for (int i = 0; i < 9; i++) {
            field[i] = '-';
        }
    }

    /**
     * Sets value to the cell.
     * @param ind given cell's number
     * @param character given character
     */
    void setAt(int ind, Character character) {
        field[ind] = character;
    }

    /**
     * Gets value of the cell.
     * @param ind given cell's number
     * @return value of the cell
     */
    Character getAt(int ind) {
        return field[ind];
    }

    /**
     * Returns next possible move for easy bot.
     * @return the position of the next move
     */
    Integer makeMoveEasyBot() {
        Integer winCell = canFillLine('o');

        if (winCell == null) {
            ArrayList<Integer> freeCells = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                if (field[i].equals('-')) {
                    freeCells.add(i);
                }
            }

            int randomNum = ThreadLocalRandom.current().nextInt(0, freeCells.size());
            return freeCells.get(randomNum);
        } else {
            return winCell;
        }
    }

    /**
     * Returns next possible move for smart bot.
     * @return the position of the next move
     */
    Integer makeMoveSmartBot() {
        Integer possibleWinCellForO = canFillLine('o');
        if (possibleWinCellForO != null) {
            return possibleWinCellForO;
        }

        Integer possibleWinCellForX = canFillLine('x');
        if (possibleWinCellForX != null) {
            return possibleWinCellForX;
        }

        Integer[] nextMove = {4, 0, 2, 6, 8, 1, 3, 5, 7};

        int i = 0;

        while (!field[nextMove[i]].equals('-')) {
            i++;
        }

        return nextMove[i];
    }

    /**
     * Returns number of cell ID (1-based).
     */
    private Integer canFillLine(Character sign) {
        for (int i = 0; i <= 6; i += 3) {
            Character a = field[i];
            Character b = field[i + 1];
            Character c = field[i + 2];

            int lineNumber = checkLine(a, b, c, sign);

            if (lineNumber == 1) {
                return i;
            } else if (lineNumber == 2) {
                return i + 1;
            } else if (lineNumber == 3) {
                return i + 2;
            }
        }

        for (int i = 0; i <= 2; i++) {
            Character a = field[i];
            Character b = field[i + 3];
            Character c = field[i + 6];

            int lineNumber = checkLine(a, b, c, sign);

            if (lineNumber == 1) {
                return i;
            } else if (lineNumber == 2) {
                return i + 3;
            } else if (lineNumber == 3) {
                return i + 6;
            }
        }

        int lineNumber = checkLine(field[0], field[4], field[8], sign);

        if (lineNumber != 0) {
            if (lineNumber == 1) { return 0; }
            if (lineNumber == 2) { return 4; }
            if (lineNumber == 3) { return 8; }
        }

        lineNumber = checkLine(field[2], field[4], field[6], sign);

        if (lineNumber != 0) {
            if (lineNumber == 1) { return 2; }
            if (lineNumber == 2) { return 4; }
            if (lineNumber == 3) { return 6; }
        }

        return null;
    }

    private static int checkLine(Character a, Character b, Character c, Character sign) {
        if (a.equals('-') && b.equals(sign) && c.equals(sign)) {
            return 1;
        } else if (a.equals(sign) && b.equals('-') && c.equals(sign)) {
            return 2;
        } else if (a.equals(sign) && b.equals(sign) && c.equals('-')) {
            return 3;
        }

        return 0;
    }

    /**
     * Checks who is winner.
     * @return "x" if "x" wins, "o" if "o" wins, "tie" if there is a tie, "" otherwise
     */
    String checkWin() {
        for (int i = 0; i <= 6; i += 3) {
            if (!field[i].equals('-')) {
                Character a = field[i];
                Character b = field[i + 1];
                Character c = field[i + 2];

                if (a.equals(b) && b.equals(c)) {
                    return a.toString();
                }
            }
        }

        for (int i = 0; i <= 2; i++) {
            if (!field[i].equals('-')) {
                Character a = field[i];
                Character b = field[i + 3];
                Character c = field[i + 6];

                if (a.equals(b) && b.equals(c)) {
                    return a.toString();
                }
            }
        }

        if (field[0].equals(field[4]) && field[4].equals(field[8])) {
            return (field[4].equals('-') ? "" : field[4].toString());
        }

        if (field[2].equals(field[4]) && field[4].equals(field[6])) {
            return (field[4].equals('-') ? "" : field[4].toString());
        }

        boolean isFill = true;

        for (int i = 0; i < 9; i++) {
            if (field[i].equals('-')) {
                isFill = false;
            }
        }

        if (isFill) {
            return "tie";
        }

        return "";
    }
}
