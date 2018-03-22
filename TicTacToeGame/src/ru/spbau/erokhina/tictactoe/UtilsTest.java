package ru.spbau.erokhina.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UtilsTest {
    @org.junit.Test
    public void testMakeMoveEasyBotSimpleCase() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "o", "",
                                                                "o",  "", "",
                                                                "x", "", ""));
        Integer nextMove = Utils.makeMoveEasyBot(field);
        assertTrue(field.get(nextMove - 1).equals(""));
    }

    @org.junit.Test
    public void testMakeMoveEasyBotCanWin() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "x", "o",
                                                                "",  "o", "",
                                                                "", "", "x"));
        Integer nextMove = Utils.makeMoveEasyBot(field);
        assertTrue(nextMove.equals(7));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseCenter() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "", "",
                                                                "",  "", "",
                                                                "", "", ""));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(5));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseFirstCorner() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("", "", "",
                                                                "",  "x", "",
                                                                "", "", ""));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(1));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseFreeCorner() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "x",
                                                                "x",  "o", "o",
                                                                "", "", "x"));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(7));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseBorder() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "o",
                                                                "",  "x", "",
                                                                "x", "o", "x"));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(4));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotCanBlock() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "",
                                                                "",  "x", "",
                                                                "", "", ""));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(8));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotCanWin() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "",
                                                                "",  "o", "x",
                                                                "x", "", ""));

        Integer nextMove = Utils.makeMoveSmartBot(field);
        assertTrue(nextMove.equals(9));
    }

    @org.junit.Test
    public void testCheckWinZeroDiagonal() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "",
                                                                "",  "o", "x",
                                                                "x", "", "o"));
        String result = Utils.checkWin(field);
        assertTrue(result.equals("o"));
    }

    @org.junit.Test
    public void testCheckWinZeroVertical() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("o", "x", "",
                                                                "o",  "x", "x",
                                                                "o", "", ""));
        String result = Utils.checkWin(field);
        assertTrue(result.equals("o"));
    }

    @org.junit.Test
    public void testCheckWinCrossHorizontal() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "x", "x",
                                                                "o",  "o", "x",
                                                                "o", "", ""));
        String result = Utils.checkWin(field);
        assertTrue(result.equals("x"));
    }

    @org.junit.Test
    public void testCheckWinTie() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "x", "o",
                                                                "o",  "o", "x",
                                                                "x", "o", "x"));
        String result = Utils.checkWin(field);
        assertTrue(result.equals("tie"));
    }

    @org.junit.Test
    public void testCheckWinNothing() throws Exception {
        ArrayList<String> field = new ArrayList<>(Arrays.asList("x", "", "o",
                                                                "",  "", "",
                                                                "", "", "x"));
        String result = Utils.checkWin(field);
        assertTrue(result.equals(""));
    }
}