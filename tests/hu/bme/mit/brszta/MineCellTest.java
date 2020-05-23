package hu.bme.mit.brszta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MineCellTest {

    @Test
    void revealSetsCellState() {
        Cell cell = new MineCell();
        cell.reveal();
        assertEquals(CellState.REVEALED, cell.cellState);
    }

    @Test
    void revealReturnsMinusOne() {
        Cell cell = new MineCell();
        assertEquals(-1, cell.reveal());
    }
}