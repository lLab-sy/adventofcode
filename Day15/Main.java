package Day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Solver {
    private char[][] grid;
    private String moves;

    public Solver(char[][] grid, String moves){
        this.grid = grid;
        this.moves = moves;
    }

    private void moveTo(int sr, int sc, int nr, int nc) {
        char temp = grid[sr][sc];
        grid[sr][sc] = grid[nr][nc];
        grid[nr][nc] = temp;
    }

    private boolean goLeft(int r, int c){
        switch (grid[r][c-1]) {
            case '.' -> moveTo(r, c, r, c-1);
            case 'O' -> {
                if (goLeft(r, c-1)) {
                    moveTo(r, c, r, c-1);
                } else {
                    return false;
                }
                // int tc = c-1;
                // while (grid[r][tc] == 'O') {
                //     tc = tc-1;
                // }

                // if (grid[r][tc] == '.') {
                //     for (int i = tc; i < c; i++) {
                //         moveTo(r, i+1, r, i);
                //     }
                //     return true;
                // }
                // return false;
            }
            default -> {return false;}
        }
        return true;
    }

    private boolean goRight(int r, int c){
        switch (grid[r][c+1]) {
            case '.' -> moveTo(r, c, r, c+1);
            case 'O' -> {
                if (goRight(r, c+1)) {
                    moveTo(r, c, r, c+1);
                } else {
                    return false;
                }
                // int tc = c+1;
                // while (grid[r][tc] == 'O') {
                //     tc = tc+1;
                // }

                // if (grid[r][tc] == '.') {
                //     for (int i = tc; i > c; i--) {
                //         moveTo(r, i-1, r, i);
                //     }
                //     return true;
                // }
                // return false;
            }
            default -> {return false;}
        }
        return true;
    }
    private boolean goUp(int r, int c){
        switch (grid[r-1][c]) {
            case '.' -> moveTo(r, c, r-1, c);
            case 'O' -> {
                if (goUp(r-1, c)) {
                    moveTo(r, c, r-1, c);
                } else {
                    return false;
                }
                // int tr = r-1;
                // while (grid[tr][c] == 'O') {
                //     tr = tr-1;
                // }

                // if (grid[tr][c] == '.') {
                //     for (int i = tr; i < r; i++) {
                //         moveTo(i+1, c, i, c);
                //     }
                //     return true;
                // }
                // return false;
            }
            default -> {return false;}
        }
        return true;
    }
    private boolean goDown(int r, int c){
        switch (grid[r+1][c]) {
            case '.' -> moveTo(r, c, r+1, c);
            case 'O' -> {
                if (goDown(r+1, c)) {
                    moveTo(r, c, r+1, c);
                } else {
                    return false;
                }
                // int tr = r+1;
                // while (grid[tr][c] == 'O') {
                //     tr = tr+1;
                // }

                // if (grid[tr][c] == '.') {
                //     for (int i = tr; i > r; i--) {
                //         moveTo(i-1, c, i, c);
                //     }
                //     return true;
                // }
                // return false;
            }
            default -> {return false;}
        }
        return true;
    }

    private void printGrid(){
        int n = grid.length, m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public int sovler1(int sr, int sc) {
        int cr = sr, cc = sc;
        for (char move : moves.toCharArray()) {
            switch (move) {
                case '<' ->{
                    // left
                    if (goLeft(cr, cc)) {
                        cc--;
                    }
                }
                case '>' -> {
                    // right
                    if (goRight(cr, cc)) {
                        cc++;
                    }
                }
                case '^' -> {
                    // up
                    if (goUp(cr, cc)) {
                        cr--;
                    }
                }
                case 'v' -> {
                    // down
                    if (goDown(cr, cc)) {
                        cr++;
                    }
                }
            }
            // System.out.println(move);
            // printGrid();
        }
        int res = 0, n = grid.length, m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'O'){
                    res += 100*i+j;
                }
            }
        }
        return res;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            // Read all lines from the input file
            List<String> lines = Files.readAllLines(Paths.get("Day15/input.txt"));

            // Separate the grid and movement instructions
            int separatorIndex = lines.indexOf(""); // Find the empty line separator
            List<String> gridLines = lines.subList(0, separatorIndex); // Grid part
            List<String> instructionLines = lines.subList(separatorIndex + 1, lines.size()); // Instruction lines

            // String moves = lines.get(separatorIndex + 1); // Movement instructions part

            // Convert grid to 2D character array
            char[][] grid = new char[gridLines.size()][];
            int sr=0, sc=0;
            for (int i = 0; i < gridLines.size(); i++) {
                grid[i] = gridLines.get(i).toCharArray();
                if (gridLines.get(i).indexOf('@') != -1){
                    sr = i;
                    sc = gridLines.get(i).indexOf('@');
                }    
            }

            String moves = "";

            for (String ins : instructionLines) {
                moves += ins;
            }

            // Print the grid
            System.out.println("Grid:");
            for (char[] row : grid) {
                System.out.println(row);
            }

            // Print the movement instructions
            System.out.println("Movement instructions: " + moves);

            Solver solver = new Solver(grid, moves);
            System.out.println(solver.sovler1(sr, sc));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
        }
    }
}
