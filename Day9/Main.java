package Day9;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

// 3366475738068
class Solver {
    private String inp;

    public Solver (String inp) {
        this.inp = inp;
    }

    private Long calculateSum (int idx, int begin, int cap) {
        // for (int i = 0; i < cap; i++) {
        //     System.out.print(idx);
        // }
        return (idx * (begin + cap - 1L + begin) * (cap)) / 2L;
    }

    public Long solve() {
        Map<Integer, Integer> mapCap = new  HashMap<>();
        int idx = 0;
        for (int i = 0; i < inp.length(); i += 2) {
            mapCap.put(idx, Integer.parseInt(""+inp.charAt(i)));
            idx += 1;
        }
        idx = idx-1;
        Long sum = 0L;
        int last = 0;
        for (int i = 0; i < inp.length(); i++) {
            if(i%2 == 0) {
                int curIdx = i/2;
                int cap = mapCap.get(curIdx);
                if (cap != 0) {
                    sum += calculateSum(curIdx, last, cap);
                    last = last+cap;
                    mapCap.put(curIdx, 0);
                }
            }else {
                int cap = Integer.parseInt("" + inp.charAt(i));
                while (cap > 0 && idx >= i/2) {
                    int idxCap = mapCap.get(idx);
                    int minCap;
                    if (cap > idxCap) {
                        cap = cap - idxCap;
                        minCap = idxCap;
                        idxCap = 0;
                    } else {
                        idxCap = idxCap - cap;
                        minCap = cap;
                        cap = 0;
                    }
                    sum  += calculateSum(idx, last, minCap);
                    last = last+minCap;
                    mapCap.put(idx, idxCap);
                    if(idxCap == 0) {
                        idx = idx-1;
                    }
                }
            }
        }
        System.out.println();
        System.out.println(inp.length()/2);
        return sum;
    }

    public Long solve2() {
        // Map<Integer, Integer> mapCap = new  HashMap<>();
        int n = this.inp.length()/2;
        int[] capF = new int[n+1];
        int[] startF = new int[n+1];
        int[] capS = new int[n+1];
        int[] startS = new int[n+1];
        int lastIdx = 0;
        Long sum = 0L;
        int lastPos = 0;
        for (int i = 0; i < inp.length(); i++) {
            if (i%2 == 0) {
                capF[i/2] =  Integer.parseInt(""+inp.charAt(i));
                startF[i/2] = lastPos;
                sum += calculateSum(i/2, lastPos, capF[i/2]);
                lastPos += capF[i/2];
                // mapCap.put(i/2,);
                lastIdx = i/2;
            }else {
                capS[i/2] = Integer.parseInt(""+inp.charAt(i));
                startS[i/2] = lastPos;
                lastPos += capS[i/2];
            }
        }

        for (int i = n; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (capS[j] > 0 && capS[j] >= capF[i]) {
                    // System.out.println("file " + i + " move to space " + j);
                    sum -= calculateSum(i, startF[i], capF[i]);
                    capS[j] -= capF[i];
                    startF[i] = startS[j];
                    startS[j] += capF[i];
                    sum += calculateSum(i, startF[i], capF[i]);
                    break;
                }
            }
        }

        // int last = 0;
        
        System.out.println();
        System.out.println(n);
        // System.out.println(last);
        return sum;
    }
    
    
}

public class Main {
    public static void main(String[] args) {
        try {
            // Read the input from a file
            String input = Files.readString(Paths.get("Day9/input.txt")).trim();

            // Print the input to verify
            System.out.println("Input read from file: " + input);
            Solver solver = new Solver(input);
            System.out.println(solver.solve2());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
