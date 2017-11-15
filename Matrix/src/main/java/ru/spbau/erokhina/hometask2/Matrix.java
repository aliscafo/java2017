package ru.spbau.erokhina.hometask2;

import java.util.Comparator;

import static java.util.Arrays.sort;

public class Matrix {
    private int data[][];
    private int size;

    /**
     * Constructs new Matrix with given data.
     * @param newData is given matrix with data.
     */
    public Matrix(int newData[][]) {
        size = newData.length;
        if ((size % 2) == 0)
            throw new IllegalArgumentException("Length of newData must be odd.");
        for (int[] oneRow : newData) {
            if (oneRow.length != size)
                throw new IllegalArgumentException("The size of at least one row doesn't correspond to the size of the given matrix.");
        }
        data = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = newData[j][i];
            }
        }
    }

    /**
     * Sorts Matrix by the first element of column.
     */
    public void sortByColumn() {
        sort(data, Comparator.comparingInt(a -> a[0]));
    }

    private void rightSincePos(int x, int y, int len, int start, int[] ans) {
        int i = x, j = y + 1;

        for (; j < y + len; j++)
            ans[start++] = data[i][j];
    }

    private void downSincePos(int x, int y, int len, int start, int[] ans) {
        int i = x + 1, j = y;

        for (; i < x + len; i++)
            ans[start++] = data[i][j];
    }

    private void leftSincePos(int x, int y, int len, int start, int[] ans) {
        int i = x, j = y - 1;

        for (; j > y - len; j--)
            ans[start++] = data[i][j];
    }

    private void topSincePos(int x, int y, int len, int start, int[] ans) {
        int i = x - 1, j = y;

        for (; i > x - len; i--)
            ans[start++] = data[i][j];
    }

    private boolean inField(int x, int y) {
        return (0 <= x && x < size && 0 <= y && y < size);
    }

    /**
     * Outputs Matrix in a spiral order.
     * @return array with output.
     */
    public int[] spiralOutput() {
        int[] ans = new int[size * size];
        int xStart = size / 2, yStart = size / 2;
        int xDir = 1, yDir = 0, len = 2, tPos = 1;

        ans[0] = data[xStart][yStart];

        while (true) {
            if (xStart != 0) {
                rightSincePos(xStart, yStart, len, tPos, ans);
                yStart += len - 1;
                tPos += len - 1;
            } else {
                len--;
                rightSincePos(xStart, yStart, len, tPos, ans);
                return ans;
            }
            downSincePos(xStart, yStart, len, tPos, ans);
            xStart += len - 1;
            tPos += len - 1;
            len++;

            leftSincePos(xStart, yStart, len, tPos, ans);
            yStart -= len - 1;
            tPos += len - 1;

            topSincePos(xStart, yStart, len, tPos, ans);
            xStart -= len - 1;
            tPos += len - 1;
            len++;
        }
    }
}
