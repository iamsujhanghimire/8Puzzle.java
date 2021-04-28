import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private int n;
    private int[] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length; //dimension of board
        board = new int[n * n]; //board of (n square)

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i * n + j] = tiles[i][j]; //assigning elements to a flat board
            }
        }
    }

    private Board(int[] board) {
        n = (int) Math.sqrt(board.length);
        this.board = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i];
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < board.length; i++) {
            s.append(String.format("%2d ", board[i]));
            if (i % n == 0) s.append("\n");
        }
        return s.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row < 0 || row > n) {
            throw new IllegalArgumentException("Beyond range");
        }
        if (col < 0 || col > n) {
            throw new IllegalArgumentException("Beyond range");
        }

        int pos = row * n + col;
        return board[pos];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int c = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != i + 1 && board[i] != 0) c++;

        }
        return c;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhat = 0;
        int r, c;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != i + 1 && board[i] != 0) {
                r = Math.abs((board[i] - 1) / n - (i / n));
                c = Math.abs((board[i] - 1) % n - (i % n));
                manhat += (r + c);
            }
        }
        return manhat;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n * n - 1; i++) {
            if (board[i] != i + 1) return false;
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board other = (Board) y;
        return Arrays.equals(this.board, other.board);
    }

    private Board exchange(Board brd, int a, int b) { //exchanges elements of a board
        int tmp = brd.board[a];
        brd.board[a] = brd.board[b];
        brd.board[b] = tmp;
        return brd;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int index = 0;
        boolean search = false; //if empty block is found
        Board neighbor;

        Queue<Board> q = new LinkedList<>();

        for (int i = 0; i < board.length; i++) {   // search for empty block
            if (board[i] == 0) {
                index = i;
                search = true;
                break;
            }
        }
        if (!search) return null;

        if (index / n != 0) {                      // if not first row
            neighbor = new Board(board);
            exchange(neighbor, index, index - n);
            q.add(neighbor);
        }

        if (index / n != (n - 1)) {               // if not last row
            neighbor = new Board(board);
            exchange(neighbor, index, index + n);
            q.add(neighbor);
        }

        if ((index % n) != 0) {                        // if not leftmost column
            neighbor = new Board(board);
            exchange(neighbor, index, index - 1);
            q.add(neighbor);
        }

        if ((index % n) != n - 1) {                          // if not rightmost column
            neighbor = new Board(board);
            exchange(neighbor, index, index + 1);
            q.add(neighbor);
        }

        return q;

    }

    // is this board solvable?
    public boolean isSolvable() {
        int inv = 0;
        int zerow = 0;

        if (n % 2 != 0) { //for odd board
            for (int i = 0; i < n * n; i++) {
                if (board[i] == 0) continue;
                for (int j = i + 1; j < n * n; j++) {
                    if (board[i] > board[j] && board[j] != 0) {
                        inv++;
                    }
                }
            }
        }

        if (n % 2 == 0) {  //for even board
            for (int i = 0; i < n * n; i++) {
                if (board[i] == 0) continue;
                for (int j = i + 1; j < n * n; j++) {
                    if (board[i] > board[j] && board[j] != 0) {
                        inv++;
                    }
                }
            }
            for (int i = 0; i < n; i++) { //finding blank row
                for (int j = 0; j < n; j++) {
                    if (board[i * n + j] == 0) {
                        zerow = i;
                        break;
                    }
                }
            }
            inv = inv + zerow - 1;
        }
        if (inv % 2 == 0) return true;
        else return false;
    }

    // unit testing (required)
    public static void main(String[] args) {
        /**  In in = new In(args[0]);
         int n = in.readInt();
         int[][] testBoard = new int[n][n];
         for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
         testBoard[i][j] = in.readInt();
         }
         }

         Board test = new Board(testBoard);
         System.out.println(test.toString());
         System.out.println("Tile At: " + test.tileAt(1, 0));
         System.out.println("Size of board: " + test.size());
         System.out.println("Hamming: " + test.hamming());
         System.out.println("Manhattan: " + test.manhattan());
         System.out.println("Goal: " + test.isGoal());
         System.out.println("Is the board solvable: " + test.isSolvable());
         //System.out.println(test.equals());
         System.out.println(test.neighbors().toString());
         System.out.println(test.toString());
         */
    }

}
