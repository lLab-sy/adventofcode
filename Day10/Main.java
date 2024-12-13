package Day10;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Pair {
    public final int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Pair> getNeighbour(){
        ArrayList<Pair> res = new ArrayList<>();
        res.add(new Pair(x-1, y));
        res.add(new Pair(x, y-1));
        res.add(new Pair(x+1, y));
        res.add(new Pair(x, y+1));
        return res;
    }
    
}

class Solver {
    private int[][] grid;
    private int n, m;

    public Solver(int[][] grid, int n, int m) {
        this.grid = grid;
        this.n = n;
        this.m = m;
    }

    public int solver1(){
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    res += subSolver1(i, j);
                }
            }
        }
        return res;
    }

    public int subSolver1(int sx, int sy) {
        int res = 0;
        boolean[][] visit = new boolean[n][m];
        int[][] count = new int[n][m];
        Queue<Pair> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                visit[i][j] = false;
                count[i][j] = 0;
                // if (grid[i][j] == 0) {
                //     count[i][j] = 1;
                //     q.add(new Pair(i, j));
                // }
            }
        }
        count[sx][sy] = 1;
        q.add(new Pair(sx, sy));

        while (!q.isEmpty()) { 
            Pair cur = q.poll();
            int r = cur.x, c = cur.y;
            if (visit[r][c]) {
                continue;
            }
            visit[r][c] = true;
            if (grid[r][c] == 9) {
                // // problem 1
                // res ++;
                // problem 2
                res += count[r][c];
            }
            for (Pair nextPos: cur.getNeighbour()) {
                int nr = nextPos.x, nc = nextPos.y;
                if (nr < 0 || nr >= n || nc < 0 || nc >= m || visit[nr][nc] || grid[nr][nc] != grid[r][c]+1) {
                    continue;
                }
                count[nr][nc] += count[r][c];
                q.add(nextPos);
            }
        }

        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         if (grid[i][j] == 9) {
        //             System.out.println("9 -> " + i + ", " + j + " --> " + count[i][j]);
        //         }
        //     }
        // }

        return res;
    }
    
}

public class Main {
    public static void main(String[] args) {
        try {
            // Read all lines from the file
            String filePath = "Day10/input.txt"; // Ensure the file exists in the specified location
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            // Determine the dimensions of the 2D array
            int rows = lines.size();
            int cols = lines.get(0).length();
            
            // Initialize the 2D array
            int[][] grid = new int[rows][cols];
            
            // Populate the 2D array
            for (int i = 0; i < rows; i++) {
                String line = lines.get(i);
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = line.charAt(j) - '0'; // Convert character to integer
                }
            }
            
            // Print the 2D array to verify
            // for (int[] row : grid) {
            //     for (int num : row) {
            //         System.out.print(num + " ");
            //     }
            //     System.out.println();
            // }
            
            Solver solver = new Solver(grid, rows, cols);
            System.out.println(solver.solver1());
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
