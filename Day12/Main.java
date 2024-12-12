package Day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;



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

class Problem2 {
    private static  int n, m;
    private static int[][] regions;
    private static Map<Integer, Integer> countSide, countReg;
    

    public Problem2(int[][] regions, int n, int m){
        this.regions = regions;
        this.n = n;
        this.m = m;
        Problem2.countSide = new LinkedHashMap<>();
        Problem2.countReg = new LinkedHashMap<>();
    }

    private static void updateCount(int reg) {
        if (countSide.get(reg) == null) {
            countSide.put(reg, 0);
        }
        countSide.put(reg, countSide.get(reg)+1);
    }

    private static void updateCountReg(int reg) {
        if (countReg.get(reg) == null) {
            countReg.put(reg, 0);
        }
        countReg.put(reg, countReg.get(reg)+1);
    }

    public int solve() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int curReg = regions[i][j];
                updateCountReg(curReg);
                // vertical
                // consider previos row
                if (i == 0) {
                    if (j == 0 || regions[i][j-1] != curReg){
                        updateCount(curReg);
                    }
                } else {
                    int prevRowReg = regions[i-1][j];
                    if (prevRowReg != curReg){
                        if (j == 0 || regions[i][j-1] != curReg || (regions[i-1][j-1] == curReg)) {
                            updateCount(curReg);
                        }
                    }
                }

                // consider next row
                if (i == n-1) {
                    if (j == 0 || regions[i][j-1] != curReg){
                        updateCount(curReg);
                    }
                } else {
                    int nextRowReg = regions[i+1][j];
                    if (nextRowReg != curReg) {
                        if (j == 0 || regions[i][j-1] != curReg || (regions[i+1][j-1] == curReg)) {
                            updateCount(curReg);
                        }
                    }
                } 

                // horizontal
                // consider previous column
                if (j == 0) {
                    if (i == 0 || regions[i-1][j] != curReg) {
                        updateCount(curReg);
                    }
                } else {
                    int prevColReg = regions[i][j-1];
                    if (prevColReg != curReg){
                        if (i == 0 || regions[i-1][j] != curReg || (regions[i-1][j-1] == curReg)) {
                            updateCount(curReg);
                        }
                    }
                }

                // consider next column
                if (j == m-1){
                    if (i == 0 || regions[i-1][j] != curReg) {
                        updateCount(curReg);
                    }
                } else {
                    int prevColReg = regions[i][j+1];
                    if (prevColReg != curReg){
                        if (i == 0 || regions[i-1][j] != curReg || (regions[i-1][j+1] == curReg)) {
                            updateCount(curReg);
                        }
                    }
                }
            }
        }

        int res = 0;

        for (int reg : countReg.keySet()) {
            System.out.println(reg + " => count reg is " + countReg.get(reg) + " * " + countSide.get(reg));
            res += countReg.get(reg) * countSide.get(reg);
        }

        return res;
    }

}
public class Main {
    private static int bfs(char[][] lines, int sx, int sy, int n, int m){
        Queue<Pair> q = new  LinkedList<>();

        int cnt = 0;
        int close = 0;
        char base = lines[sx][sy];
        q.add(new Pair(sx, sy));

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            if (lines[cur.x][cur.y] == '-'){
                continue;
            }   
            lines[cur.x][cur.y] = '-';
            cnt += 1;
            for (Pair p: cur.getNeighbour()){
                if (p.x < 0 || p.x >= n || p.y < 0 || p.y >= m) {
                    close += 1;
                } else if (lines[p.x][p.y] == '-') {

                } else if (lines[p.x][p.y] != base) {
                    close += 1;
                } else {
                    q.add(p);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (lines[i][j] == '-') {
                    lines[i][j] = '+';
                }
            }
        }

        return cnt * close;
    }
    
    private static void fillRegion(char[][] lines, int sx, int sy, int n, int m, int[][] regions, int k){
        Queue<Pair> q = new  LinkedList<>();

        char base = lines[sx][sy];
        q.add(new Pair(sx, sy));

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            if (lines[cur.x][cur.y] == '-'){
                continue;
            }   
            lines[cur.x][cur.y] = '-';
            regions[cur.x][cur.y] = k;
            for (Pair p: cur.getNeighbour()){
                if (p.x < 0 || p.x >= n || p.y < 0 || p.y >= m || lines[p.x][p.y] == '-' || lines[p.x][p.y] != base) {
                    continue;
                }
                q.add(p);
            }
        }

    }

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try {
            // Define the path to the file
            Path filePath = Path.of("Day12/input.txt");

            // Read all lines into a List
            lines = Files.readAllLines(filePath);

            // Print the array
            // for (String line : lines) {
            //     System.out.println(line);
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int n = lines.size();
        int m = lines.get(0).length();
        char[][] charArr = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                charArr[i][j] = lines.get(i).charAt(j);
            }
        }
        
        // // begin sol1
        // int sum = 0;
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         if (charArr[i][j] != '+') {
        //             sum += bfs(charArr, i, j, n, m);
        //         }
        //     }
        // }
        // // 1363682
        // System.out.println("Ans of First question is  " + sum);
        // // end sol1

        // begin sol2
        int[][] regions = new int[n][m];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (charArr[i][j] != '-') {
                    fillRegion(charArr, i, j, n, m, regions, k);
                    k += 1;
                }
            }
        }

        Problem2 problem2Solver = new Problem2(regions, n, m);
        System.out.println("And of Second question is " + problem2Solver.solve());

        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         System.out.print(regions[i][j]);
        //     }
        //     System.out.println();
        // }

        // end sol2
    }
}
