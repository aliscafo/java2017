package ru.spbau.erokhina.tictactoe;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that provides util function for logic.
 */
public class Utils {
    /**
     * Returns next possible move for easy bot.
     * @param values array that represents a game field
     * @return the position of the next move
     */
    static Integer makeMoveEasyBot(ArrayList<String> values) {
        Integer winCell = canFillLine(values, "o");

        if (winCell == null) {
            ArrayList<Integer> freeCells = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                if (values.get(i).equals("")) {
                    freeCells.add(i + 1);
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
     * @param values array that represents a game field
     * @return the position of the next move
     */
    static Integer makeMoveSmartBot(ArrayList<String> values) {
        Integer possibleWinCellForO = canFillLine(values, "o");
        if (possibleWinCellForO != null) {
            return possibleWinCellForO;
        }

        Integer possibleWinCellForX = canFillLine(values, "x");
        if (possibleWinCellForX != null) {
            return possibleWinCellForX;
        }

        Integer[] nextMove = {4, 0, 2, 6, 8, 1, 3, 5, 7};

        int i = 0;

        while (!values.get(nextMove[i]).equals("")) {
            i++;
        }

        return nextMove[i] + 1;
    }

    /**
     * Prints a game field.
     * @param values array that represents a game field
     */
    @SuppressWarnings("unused")
    public static void printField(ArrayList<String> values) {
        for (int i = 0; i < 3; i++) {
            if (values.get(i).equals(""))
                System.out.print("_");
            else
                System.out.print(values.get(i));
        }
        System.out.print("\n");
        for (int i = 3; i < 6; i++) {
            if (values.get(i).equals(""))
                System.out.print("_");
            else
                System.out.print(values.get(i));
        }
        System.out.print("\n");
        for (int i = 6; i < 9; i++) {
            if (values.get(i).equals(""))
                System.out.print("_");
            else
                System.out.print(values.get(i));
        }
        System.out.print("\n\n");
    }

    /**
     * Returns number of cell ID (1-based).
     */
    private static Integer canFillLine(ArrayList<String> values, String sign) {
        for (int i = 0; i <= 6; i += 3) {
            String value1 = values.get(i);
            String value2 = values.get(i + 1);
            String value3 = values.get(i + 2);

            int lineNumber = checkLine(value1, value2, value3, sign);

            if (lineNumber == 1) {
                return i + 1;
            } else if (lineNumber == 2) {
                return i + 2;
            } else if (lineNumber == 3) {
                return i + 3;
            }
        }

        for (int i = 0; i <= 2; i++) {
            String value1 = values.get(i);
            String value2 = values.get(i + 3);
            String value3 = values.get(i + 6);

            int lineNumber = checkLine(value1, value2, value3, sign);

            if (lineNumber == 1) {
                return i + 1;
            } else if (lineNumber == 2) {
                return i + 4;
            } else if (lineNumber == 3) {
                return i + 7;
            }
        }

        int lineNumber = checkLine(values.get(0), values.get(4), values.get(8), sign);

        if (lineNumber != 0) {
            if (lineNumber == 1) { return 1; }
            if (lineNumber == 2) { return 5; }
            if (lineNumber == 3) { return 9; }
        }

        lineNumber = checkLine(values.get(2), values.get(4), values.get(6), sign);

        if (lineNumber != 0) {
            if (lineNumber == 1) { return 3; }
            if (lineNumber == 2) { return 5; }
            if (lineNumber == 3) { return 7; }
        }

        return null;
    }

    private static int checkLine(String value1, String value2, String value3, String sign) {
        if (value1.equals("") && value2.equals(sign) && value3.equals(sign)) {
            return 1;
        } else if (value1.equals(sign) && value2.equals("") && value3.equals(sign)) {
            return 2;
        } else if (value1.equals(sign) && value2.equals(sign) && value3.equals("")) {
            return 3;
        }

        return 0;
    }

    /**
     * Checks who is winner.
     * @param values array that represents a game field
     * @return "x" if "x" wins, "o" if "o" wins, "tie" if there is a tie, "" otherwise
     */
    static String checkWin(ArrayList<String> values) {
        for (int i = 0; i <= 6; i += 3) {
            if (!values.get(i).equals("")) {
                String value1 = values.get(i);
                String value2 = values.get(i + 1);
                String value3 = values.get(i + 2);

                if (value1.equals(value2) && value2.equals(value3)) {
                    return value1;
                }
            }
        }

        for (int i = 0; i <= 2; i ++) {
            if (!values.get(i).equals("")) {
                String value1 = values.get(i);
                String value2 = values.get(i + 3);
                String value3 = values.get(i + 6);

                if (value1.equals(value2) && value2.equals(value3)) {
                    return value1;
                }
            }
        }

        if (values.get(0).equals(values.get(4)) && values.get(4).equals(values.get(8))) {
            return values.get(4);
        }

        if (values.get(2).equals(values.get(4)) && values.get(4).equals(values.get(6))) {
            return values.get(4);
        }

        boolean isFill = true;

        for (int i = 0; i < 9; i++) {
            if (values.get(i).equals("")) {
                isFill = false;
            }
        }

        if (isFill) {
            return "tie";
        }

        return "";
    }
}
