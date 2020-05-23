package hu.bme.mit.brszta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    @Test
    void cellStateStartsAsDefault() {
        assertEquals(CellState.DEFAULT, new EmptyCell().cellState);
        assertEquals(CellState.DEFAULT, new MineCell().cellState);
    }

    @Test
    void addNeighbourCell() {
        Cell cell1 = new EmptyCell();
        Cell cell2 = new EmptyCell();
        cell1.addNeighbourCell(cell2);
        assertEquals(cell2, cell1.neighbourCells.get(0));
    }

    @Test
    void revealNeighbours() {
        Cell cell1 = new EmptyCell();
        Cell cell2 = new EmptyCell();
        cell1.addNeighbourCell(cell2);
        cell1.reveal();
        assertEquals(CellState.REVEALED, cell2.cellState);
    }

    @Test
    void flagSetsFlag() {
        Cell cell = new MineCell();
        cell.flag();
        assertEquals(CellState.FLAGGED, cell.cellState);
    }

    @Test
    void flagRemovesFlag() {
        Cell cell = new MineCell();
        cell.cellState = CellState.FLAGGED;
        cell.flag();
        assertEquals(CellState.DEFAULT, cell.cellState);
    }

    @Test
    void flagDoesNotFlagRevealedCell() {
        Cell cell = new MineCell();
        cell.reveal();
        cell.flag();
        assertEquals(CellState.REVEALED, cell.cellState);
    }
}