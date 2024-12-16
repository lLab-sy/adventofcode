package Day14;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Solver {
    private int[][][] data;

    public Solver(int[][][] data) {
        this.data = data;
    }

    private int calculateQ(int r, int c, int n, int m) {
        if (r == n/2 || c == m/2) {
            return 4;
        }
        int qua = (c > m/2 ? 1: 0)*2 + (r > n/2 ? 1: 0);
        return qua;
    }

    private int normalizePos(int x, int m) {
        int res = x%m;
        if (res < 0) {
            res += m;
        }
        return res;
    }

    public Long solver1(int n, int m, int k) {
        int[] qCount = new int[5];
        
        for (int i = 0; i < 5; i++) {
            qCount[i] = 0;
        }

        for (int[][] robot : data) {
            int c = robot[0][0], r = robot[0][1];
            qCount[calculateQ(normalizePos(r + robot[1][1]*k, n), normalizePos(c + robot[1][0]*k, m), n, m)]++;            
        }
        
        return 1L*qCount[0]*qCount[1]*qCount[2]*qCount[3];        
    }

    public void solver2(int n, int m, int k) {
        boolean[][] robotPos = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                robotPos[i][j] = false;
            }
        }

        for (int[][] robot : data) {
            int c = robot[0][0], r = robot[0][1];
            int nr = normalizePos(r + robot[1][1]*k, n);
            int nc = normalizePos(c + robot[1][0]*k, m);          
            robotPos[nr][nc] = true;
        }   

        try (FileWriter writer = new FileWriter("Day14/output.txt", true)) {
            writer.write("Iteration: " + k + "\n");
            System.out.println("\n" + k + "\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (robotPos[i][j]) {
                        writer.write("*");
                        System.out.print("*");
                    } else {
                        writer.write(" ");
                        System.out.print(" ");
                    }
                }
                writer.write("\n");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // System.out.println("\n" + k + "\n");
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         if (robotPos[i][j]) {
        //             System.out.print("*");
        //         } else {
        //             System.out.print(" ");
        //         }
        //     }
        //     System.out.println();
        // }
             
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            // Read lines from the file
            String filePath = "Day14/input.txt"; // Replace with the actual file path
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            // Initialize the 3D array
            int n = lines.size();
            int[][][] data = new int[n][2][2];
            
            // Parse each line
            for (int i = 0; i < n; i++) {
                String line = lines.get(i).trim();
                // Split on space to separate p and v parts
                String[] parts = line.split(" ");
                
                // Extract p=x,y and v=a,b
                String[] p = parts[0].substring(2).split(",");
                String[] v = parts[1].substring(2).split(",");
                
                // Parse into integers and store in the array
                data[i][0][0] = Integer.parseInt(p[0]); // x from p
                data[i][0][1] = Integer.parseInt(p[1]); // y from p
                data[i][1][0] = Integer.parseInt(v[0]); // a from v
                data[i][1][1] = Integer.parseInt(v[1]); // b from v
            }
            
            // problem 1 
            Solver solver = new Solver(data);
            System.out.println(solver.solver1(103, 101,100));
            
            // problem 2
            for (int i = 8000; i < 8300; i++) {
                solver.solver2(103, 101, i);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
