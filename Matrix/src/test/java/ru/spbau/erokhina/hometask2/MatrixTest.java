package ru.spbau.erokhina.hometask2;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {
    /**
     * Checks if constructor throws exception in case of even data matrix.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSize() throws Exception {
        int testData[][] = new int[][]{{3, 2}, {4, 1}};
        Matrix testMatrix = new Matrix(testData);
    }

    /**
     * Checks if constructor throws exception in case of non-square data matrix.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSquare() throws Exception {
        int testData[][] = new int[][]{{3, 2, 6}, {4, 1, 5}, {0, 0}};
        Matrix testMatrix = new Matrix(testData);
    }

    /**
     * Checks correctness of sort method.
     */
    @Test
    public void sortByColumn() throws Exception {
        int testData[][] = new int[][]{{3, 2, 6}, {4, 1, 5}, {7, 8, 9}};
        int[] expectedAns = new int[]{4, 7, 9, 5, 6, 3, 2, 1, 8};

        Matrix testMatrix = new Matrix(testData);
        testMatrix.sortByColumn();

        assertArrayEquals(expectedAns, testMatrix.spiralOutput());
    }

    /**
     * Checks correctness of spiral output method for 1x1 matrix.
     */
    @Test
    public void spiralOutput1x1() throws Exception {
        int testData[][] = new int[][]{{3}};
        int[] expectedAns = new int[]{3};

        Matrix testMatrix = new Matrix(testData);

        assertArrayEquals(expectedAns, testMatrix.spiralOutput());
    }

    /**
     * Checks correctness of spiral output method for 3x3 matrix.
     */
    @Test
    public void spiralOutput3x3() throws Exception {
        int testData[][] = new int[][]{{3, 2, 6}, {4, 1, 5}, {7, 8, 9}};
        int[] expectedAns = new int[]{1, 8, 9, 5, 6, 2, 3, 4, 7};

        Matrix testMatrix = new Matrix(testData);

        assertArrayEquals(expectedAns, testMatrix.spiralOutput());
    }

    /**
     * Checks correctness of spiral output method for 5x5 matrix.
     */
    @Test
    public void spiralOutput5x5() throws Exception {
        int testData[][] = new int[][]{{3, 2, 6, 1, 7}, {4, 1, 5, 8, 5}, {7, 8, 9, 1, 6}, {6, 4, 8, 0, 3}, {3, 8, 1, 3, 9}};
        int[] expectedAns = new int[]{9, 8, 0, 1, 8, 5, 1, 8, 4, 8, 1, 3, 9, 3, 6, 5, 7, 1, 6, 2, 3, 4, 7, 6, 3};

        Matrix testMatrix = new Matrix(testData);

        assertArrayEquals(expectedAns, testMatrix.spiralOutput());
    }
}