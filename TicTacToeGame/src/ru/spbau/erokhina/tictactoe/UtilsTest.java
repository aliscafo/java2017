package ru.spbau.erokhina.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UtilsTest {
    @org.junit.Test
    public void testMakeMoveEasyBotSimpleCase() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', 'o', '-',
                                                            'o',  '-', '-',
                                                            'x', '-', '-'));
        Integer nextMove = gameField.makeMoveEasyBot();
        assertTrue(gameField.getAt(nextMove).equals('-'));
    }

    @org.junit.Test
    public void testMakeMoveEasyBotCanWin() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', 'x', 'o',
                                                            '-',  'o', '-',
                                                            '-', '-', 'x'));
        Integer nextMove = gameField.makeMoveEasyBot();
        assertTrue(nextMove.equals(6));
    }
    
    @org.junit.Test
    public void testMakeMoveSmartBotChooseCenter() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', '-', '-',
                                                                '-',  '-', '-',
                                                                '-', '-', '-'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(4));
    }

    
    @org.junit.Test
    public void testMakeMoveSmartBotChooseFirstCorner() throws Exception {
        GameField gameField = new GameField(Arrays.asList('-', '-', '-',
                                                                '-',  'x', '-',
                                                                '-', '-', '-'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(0));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseFreeCorner() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', 'x',
                                                                'x',  'o', 'o',
                                                                '-', '-', 'x'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(6));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotChooseBorder() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', 'o',
                                                                '-',  'x', '-',
                                                                'x', 'o', 'x'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(3));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotCanBlock() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', '-',
                                                                '-',  'x', '-',
                                                                '-', '-', '-'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(7));
    }

    @org.junit.Test
    public void testMakeMoveSmartBotCanWin() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', '-',
                                                                '-',  'o', 'x',
                                                                'x', '-', '-'));

        Integer nextMove = gameField.makeMoveSmartBot();
        assertTrue(nextMove.equals(8));
    }

    @org.junit.Test
    public void testCheckWinZeroDiagonal() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', '-',
                                                                '-',  'o', 'x',
                                                                'x', '-', 'o'));
        String result = gameField.checkWin();
        assertTrue(result.equals("o"));
    }

    @org.junit.Test
    public void testCheckWinZeroVertical() throws Exception {
        GameField gameField = new GameField(Arrays.asList('o', 'x', '-',
                                                                'o',  'x', 'x',
                                                                'o', '-', '-'));
        String result = gameField.checkWin();
        assertTrue(result.equals("o"));
    }

    @org.junit.Test
    public void testCheckWinCrossHorizontal() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', 'x', 'x',
                                                                'o',  'o', 'x',
                                                                'o', '-', '-'));
        String result = gameField.checkWin();
        assertTrue(result.equals("x"));
    }

    @org.junit.Test
    public void testCheckWinTie() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', 'x', 'o',
                                                                'o',  'o', 'x',
                                                                'x', 'o', 'x'));
        String result = gameField.checkWin();
        assertTrue(result.equals("tie"));
    }

    @org.junit.Test
    public void testCheckWinNothing() throws Exception {
        GameField gameField = new GameField(Arrays.asList('x', '-', 'o',
                                                                '-',  '-', '-',
                                                                '-', '-', 'x'));
        String result = gameField.checkWin();
        assertTrue(result.equals(""));
    }
}