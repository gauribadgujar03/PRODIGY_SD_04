public class SudokuSolver {

    public static void main(String[] args) {
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        if (solveSudoku(board)) {
            System.out.println("Sudoku solved successfully:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }

    // Function to solve Sudoku using backtracking
    public static boolean solveSudoku(int[][] board) {
        int N = board.length;
        int[] rows = new int[N];
        int[] cols = new int[N];
        int[] boxes = new int[N];

        // Initialize numbers already present in the board
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] != 0) {
                    int val = board[r][c];
                    int box_index = (r / 3) * 3 + (c / 3);
                    // Set the bit corresponding to the value in rows, cols, and boxes arrays
                    rows[r] |= (1 << val);
                    cols[c] |= (1 << val);
                    boxes[box_index] |= (1 << val);
                }
            }
        }

        return solveSudokuUtil(board, 0, 0, rows, cols, boxes);
    }

    // Utility function to solve Sudoku recursively
    private static boolean solveSudokuUtil(int[][] board, int row, int col, int[] rows, int[] cols, int[] boxes) {
        int N = board.length;

        // Move to the next row once the last column is reached
        if (col == N) {
            col = 0;
            row++;
            if (row == N) {
                return true; // All rows completed
            }
        }

        // Skip already filled cells
        if (board[row][col] != 0) {
            return solveSudokuUtil(board, row, col + 1, rows, cols, boxes);
        }

        int box_index = (row / 3) * 3 + (col / 3);

        for (int num = 1; num <= 9; num++) {
            if (canPlace(board, row, col, num, rows, cols, boxes)) {
                // Place the number
                board[row][col] = num;
                rows[row] |= (1 << num);
                cols[col] |= (1 << num);
                boxes[box_index] |= (1 << num);

                // Recursively solve for the next cell
                if (solveSudokuUtil(board, row, col + 1, rows, cols, boxes)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
                rows[row] &= ~(1 << num);
                cols[col] &= ~(1 << num);
                boxes[box_index] &= ~(1 << num);
            }
        }

        return false; // No valid number found for this cell, backtrack
    }

    // Function to check if a number can be placed in the Sudoku board
    private static boolean canPlace(int[][] board, int row, int col, int num, int[] rows, int[] cols, int[] boxes) {
        @SuppressWarnings("unused")
        int N = board.length;
        int box_index = (row / 3) * 3 + (col / 3);

        // Check if the number is already present in current row, column or box
        if ((rows[row] & (1 << num)) != 0) return false; // Check row
        if ((cols[col] & (1 << num)) != 0) return false; // Check column
        if ((boxes[box_index] & (1 << num)) != 0) return false; // Check box

        return true;
    }

    // Function to print the Sudoku board
    public static void printBoard(int[][] board) {
        int N = board.length;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }
}
