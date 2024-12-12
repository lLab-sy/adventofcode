package Day12;

import java.io.IOException;
import java.lang.reflect.Array;
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
        ArrayList<Pair> res = new ArrayList<Pair>();
        res.add(new Pair(x-1, y));
        res.add(new Pair(x, y-1));
        res.add(new Pair(x+1, y));
        res.add(new Pair(x, y+1));
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

        // System.out.println(base + " => " + cnt + " and " + close);
        return cnt * close;
    }
    

    public static void main(String[] args) {
        List<String> lines = new ArrayList<String>();
        try {
            // Define the path to the file
            Path filePath = Path.of("Day12/input.txt");

            // Read all lines into a List
            lines = Files.readAllLines(filePath);

            // Print the array
            for (String line : lines) {
                System.out.println(line);
            }
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
        
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (charArr[i][j] != '+') {
                    sum += bfs(charArr, i, j, n, m);
                }
            }
        }

        // for (int val : sumChar.values()) {
        //     sum += val;
        // }
        // 1363682
        System.out.println("Ans of First question is  " + sum);

        // solveInRow(lines);
        // lines = listTranspose(lines);
        // solveInRow(lines);
        
        // int sum = 0;
        // for (char c : countChar.keySet()) {
        //     sum = sum + countChar.get(c)*closeChar.get(c)/2;
        //     System.out.println(c + " => " + (countChar.get(c)).toString() + " : " + closeChar.get(c));
        // }

        System.out.println(sum);
    }
}
