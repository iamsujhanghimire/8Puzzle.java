import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private MinPQ<SearchNode> minPQ;
    private SearchNode node; //current node
    private SearchNode next;
    private int count; //number of moves

    private Queue<Board> solution = new LinkedList<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (!initial.isSolvable() || initial == null) {
            throw new NullPointerException("Unsolvable");
        }

        minPQ = new MinPQ<SearchNode>();
        node = new SearchNode(initial, null, 0);
        minPQ.insert(node);
        node = minPQ.delMin();
        count += 1;

        while (!node.board.isGoal()) {
            for (Board neighbor : node.board.neighbors()) {
                if (node.prev == null || (!neighbor.equals(node.prev.board))) {
                    next = new SearchNode(neighbor, node, count);
                    minPQ.insert(next);
                }
            }
            node = minPQ.delMin();
            count = node.mcount;
        }
        while (node != null) {
            solution.add(node.board);
            node = node.prev;
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board; //current board
        SearchNode prev; //previous node

        int mcount; // number of moves
        int manhattan; //manhattan distance
        int priority; // priority


        public SearchNode(Board current, SearchNode p, int moves) {
            board = current;
            prev = p;

            manhattan = board.manhattan();
            mcount = moves + 1;
            //We decide the priority based on the manhattan distance
            priority = mcount + manhattan;
        }

        //We always pick the board with higher priority
        public int compareTo(SearchNode o) {
            int p1 = this.priority;
            int p2 = o.priority;
            return p1 - p2;
        }
    }


    // min number of moves to solve initial board
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return (Iterable<Board>) solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        /**   In in = new In(args[0]);
         int size = in.readInt();
         int[][] tiles = new int[size][size];

         for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
         tiles[i][j] = in.readInt();
         }
         }

         Board initial = new Board(tiles);

         if (initial.isSolvable()) {
         Solver solver = new Solver(initial);
         StdOut.println("Minimum # of moves = " + solver.moves());
         for (Board board : solver.solution()) {
         StdOut.println(board);
         }
         } else throw new IllegalArgumentException();
         **/
    }

}
