import java.io.Serializable;

public class Board implements Serializable {
    private boolean[][] board;
    public Board (boolean[][] board){
        this.board=board;
    }

    public boolean[][] getBoard() {
        return board;
    }
}
