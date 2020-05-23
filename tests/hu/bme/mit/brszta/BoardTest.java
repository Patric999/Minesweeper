package hu.bme.mit.brszta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void countMines() {
        Cell[][] cellMatrix = getDiagonalCellMatrix(4);
        Board board = new Board(cellMatrix);
        assertEquals(4, board.countMines());
    }

    @Test
    void countFlags() {
        Cell[][] cellMatrix = getDiagonalCellMatrix(3);
        Board board = new Board(cellMatrix);
        board.getCell(0,0).flag();
        board.getCell(1,1).flag();
        board.getCell(1,0).flag();
        assertEquals(3, board.countFlags());
    }

    @Test
    void countRevealed() {
        Cell[][] cellMatrix = getDiagonalCellMatrix(3);
        Board board = new Board(cellMatrix);
        board.getCell(0,1).reveal();
        board.getCell(1,2).reveal();
        board.getCell(1,0).reveal();
        assertEquals(3, board.countRevealed());
    }

    @Test
    void recursiveReveal() {
        Cell[][] cellMatrix = getUpperLeftCellMatrix(3);
        Board board = new Board(cellMatrix);
        board.getCell(0,2).reveal();
        assertEquals(CellState.REVEALED, board.getCell(0,2).cellState);
        assertEquals(CellState.REVEALED, board.getCell(0,1).cellState);
        assertEquals(CellState.REVEALED, board.getCell(1,0).cellState);
        assertEquals(CellState.REVEALED, board.getCell(1,1).cellState);
        assertEquals(CellState.REVEALED, board.getCell(1,2).cellState);
        assertEquals(CellState.REVEALED, board.getCell(2,2).cellState);
    }

    @Test
    void getCell() {
        Cell[][] cellMatrix = getDiagonalCellMatrix(3);
        Board board = new Board(cellMatrix);
        assertEquals(cellMatrix[0][2], board.getCell(0,2));
        assertEquals(cellMatrix[2][2], board.getCell(2,2));
    }

    @Test
    void initNeighbourCells() {
        Cell[][] cellMatrix = getDiagonalCellMatrix(3);
        Board board = new Board(cellMatrix);
        assertTrue(board.getCell(0,0).neighbourCells.contains(board.getCell(0,1)));
        assertTrue(board.getCell(0,0).neighbourCells.contains(board.getCell(1,1)));
        assertEquals(8, board.getCell(1,1).neighbourCells.size());
    }


    Cell[][] getDiagonalCellMatrix(int size) {
        Cell[][] cellMatrix = new Cell[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i == j) {
                    cellMatrix[i][j] = new MineCell();
                } else {
                    cellMatrix[i][j] = new EmptyCell();
                }
            }
        }
        return cellMatrix;
    }

    Cell[][] getUpperLeftCellMatrix(int size) {
        Cell[][] cellMatrix = new Cell[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i == 0 && j == 0) {
                    cellMatrix[i][j] = new MineCell();
                } else {
                    cellMatrix[i][j] = new EmptyCell();
                }
            }
        }
        return cellMatrix;
    }
}