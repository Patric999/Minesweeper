package hu.bme.mit.brszta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardBuilderTest {

    @Test
    void getRandomBoardReturnsBoard() {
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getRandomBoard(9, 9, 10);
        assertTrue(board instanceof Board);
    }

    @Test
    void getRandomBoardReturnsCorrectSize() {
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getRandomBoard(10, 15, 3);
        assertEquals(10, board.getSizeX());
        assertEquals(15, board.getSizeY());
    }

    @Test
    void getRandomBoardReturnsCorrectNoOfMines() {
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getRandomBoard(9, 9, 10);
        assertEquals(10, board.countMines());
    }

    @Test
    void getBoardFromBooleanMatrixReturnsBoard() {
        boolean[][] boolMatrix = {
                {false, false, false},
                {false, false, false},
                {false, true, false}
        };
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getBoardFromBooleanMatrix(boolMatrix);
        assertTrue(board instanceof Board);
    }

    @Test
    void getBoardFromBooleanMatrixReturnsCorrectMines() {
        boolean[][] boolMatrix = {
                {false, false, false},
                {false, false, false},
                {false, true, false}
        };
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getBoardFromBooleanMatrix(boolMatrix);
        assertTrue(board.getCell(2, 1) instanceof MineCell);
        assertTrue(board.getCell(0, 0) instanceof EmptyCell);
        assertTrue(board.getCell(0, 1) instanceof EmptyCell);
        assertTrue(board.getCell(0, 2) instanceof EmptyCell);
        assertTrue(board.getCell(1, 0) instanceof EmptyCell);
        assertTrue(board.getCell(2, 0) instanceof EmptyCell);
    }

    @Test
    void getBoardFromBooleanMatrixReturnsCorrectNoOfMines() {
        boolean[][] boolMatrix = {
                {false, false, false},
                {true, false, true},
                {false, true, false}
        };
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getBoardFromBooleanMatrix(boolMatrix);
        assertEquals(3, board.countMines());
    }

}