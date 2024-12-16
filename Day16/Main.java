package Day16;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Tuple implements Comparable<Tuple> {
    public int priority; // For priority queue
    public int x, y, w;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tuple(int priority, int x, int y, int w) {
        this.priority = priority;
        this.x = x;
        this.y = y;
        this.w = w;
    }

    @Override
    public int compareTo(Tuple other) {
        return Integer.compare(this.priority, other.priority); // Min-Heap behavior
    }

    @Override
    public String toString() {
        return "(" + priority + ", " + x + ", " + y + " <" + w + "> )";
    }
}

class Solver {
    private char[][] grid;
    private int sx, sy, ex, ey;
    private int[][] visit, visito;

    public Solver(char[][] grid, int sx, int sy, int ex, int ey) {
        this.grid = grid;
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.visit = new int[grid.length][grid[0].length];
        this.visito = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                visit[i][j] = -1;
                visito[i][j] = -1;
            }
        }
    }
    
    private Tuple nextMove(int x, int y, int w) {
        return new Tuple(0, x + (w%2==0?1:0) * (w==0?1:-1), y + (w%2==1?1:0) * (w==1?1:-1), w);
    }

    public int solver1() {
        int n = grid.length, m = grid[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        // w 0 1 2 3 -> N E S W
        pq.add(new Tuple(0, sx, sy, 1));
        visit[sx][sy] = 0;

        int minP = -1;
        while (!pq.isEmpty()) { 
            Tuple ct = pq.poll();
            int cx = ct.x, cy = ct.y, cp = ct.priority, cw = ct.w;
            if (visit[cx][cy] != -1 && visit[cx][cy] < cp) {
                continue;
            }
            if (cx == ex && cy == ey) {
                // part 1
                // return cp;
                if (minP == -1 || minP > cp) {
                    minP = cp;
                }
            }
            for (int i = -1; i <= 1; i++) {
                int nw = (cw+i+4)%4;
                Tuple nextState = nextMove(cx, cy, nw);
                nextState.priority = cp+1;
                if (i != 0) {
                    nextState.priority += 1000;
                }
                if (nextState.x < 0 || nextState.x >= n || nextState.y < 0 || nextState.y >= m || grid[nextState.x][nextState.y] == '#' || (visit[nextState.x][nextState.y] != -1 && visit[nextState.x][nextState.y] <= nextState.priority)) {
                    continue;
                }
                visit[nextState.x][nextState.y] = nextState.priority;
                pq.add(nextState);
            }
        }

        return minP;
    }

    public int solver1o() {
        int n = grid.length, m = grid[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        // w 0 1 2 3 -> N E S W
        pq.add(new Tuple(0, ex, ey, 0));
        pq.add(new Tuple(0, ex, ey, 1));
        pq.add(new Tuple(0, ex, ey, 2));
        pq.add(new Tuple(0, ex, ey, 3));
        visito[sx][sy] = 0;

        int minP = -1;
        while (!pq.isEmpty()) { 
            Tuple ct = pq.poll();
            int cx = ct.x, cy = ct.y, cp = ct.priority, cw = ct.w;
            if (visito[cx][cy] != -1 && visito[cx][cy] < cp) {
                continue;
            }
            if (cx == sx && cy == sy) {
                if (minP == -1 || minP > cp) {
                    minP = cp;
                }
            }
            if (cp + visit[cx][cy] == visit[ex][ey]) {
                grid[cx][cy] = 'O';
            }
            for (int i = -1; i <= 1; i++) {
                int nw = (cw+i+4)%4;
                Tuple nextState = nextMove(cx, cy, nw);
                nextState.priority = cp+1;
                if (i != 0) {
                    nextState.priority += 1000;
                }
                if (nextState.x < 0 || nextState.x >= n || nextState.y < 0 || nextState.y >= m || grid[nextState.x][nextState.y] == '#' || (visito[nextState.x][nextState.y] != -1 && visito[nextState.x][nextState.y] <= nextState.priority)) {
                    continue;
                }
                if (nextState.priority + visit[nextState.x][nextState.y] <= visit[ex][ey]) {
                    grid[cx][cy] = 'O';
                    pq.add(nextState);
                }
                visito[nextState.x][nextState.y] = nextState.priority;
            }
        }

        return minP;
    }

    public int solver2() {
        int n = grid.length, m = grid[0].length;
        solver1();
        solver1o();
        grid[sx][sy] = 'O';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if ((visit[i][j] + visito[i][j] == visit[ex][ey] || visit[i][j] + visito[i][j] + 1000 == visit[ex][ey]) && visit[i][j] != -1 && visito[i][j] != -1) {
                    //     cnt++;
                    //     System.out.print("O");
                    // }else {
                //     System.out.print(grid[i][j]);
                // }
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        Queue<Tuple> q = new LinkedList<>();
        q.add(new Tuple(sx, sy));
        int cnt = 0;
        while (!q.isEmpty()) {
            Tuple cur = q.poll();
            if (grid[cur.x][cur.y] != 'O') {
                continue;
            }
            cnt ++;
            grid[cur.x][cur.y] = '-';
            for (int i=0;i<4;++i){
                Tuple nextPos = nextMove(cur.x, cur.y, i);
                if (nextPos.x < 0 || nextPos.x >= n || nextPos.y < 0 || nextPos.y >= m || grid[nextPos.x][nextPos.y] != 'O') {
                    continue;
                }
                q.add(nextPos);
            }
        }

        return cnt;
    }

}

public class Main {
    public static void main(String[] args) {
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Paths.get("Day16/input.txt"));

            // Get the dimensions of the grid
            int rows = lines.size();
            int cols = lines.get(0).length();

            // Create a 2D character array to store the grid
            char[][] grid = new char[rows][cols];

            // Populate the grid
            for (int i = 0; i < rows; i++) {
                grid[i] = lines.get(i).toCharArray();
            }

            // Print the grid to verify
            System.out.println("Grid:");
            for (char[] row : grid) {
                System.out.println(new String(row));
            }

            // Example: Find the start (S) and end (E) positions
            int startX = -1, startY = -1, endX = -1, endY = -1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 'S') {
                        startX = i;
                        startY = j;
                    } else if (grid[i][j] == 'E') {
                        endX = i;
                        endY = j;
                    }
                }
            }

            System.out.println("Start Position: (" + startX + ", " + startY + ")");
            System.out.println("End Position: (" + endX + ", " + endY + ")");

            Solver solver = new Solver(grid, startX, startY, endX, endY);
            System.out.println(solver.solver2());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file.");
        }
    }
}
