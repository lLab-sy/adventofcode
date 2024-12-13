package Day13;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

class InputSection {
    Map<String, Long> coordinatesA, coordinatesB, coordinatesP;

    public InputSection(Map<String, Long> coordA, Map<String, Long> coordB, Map<String, Long> coordP) {
        // this.coordinatesA = new HashMap<>();
        // this.coordinatesB = new HashMap<>();
        // this.coordinatesP = new HashMap<>();
        this.coordinatesA = coordA;
        this.coordinatesB = coordB;
        this.coordinatesP = coordP;
    }

    public Long solver1() {
        // xp = Nxa + Mxb -> 1 
        // from 1 -> M = (xp - Nxa)/xb -> 2
        // yp = Nya + Myp -> 3
        //  2 in 3 -> yp = Nya +((xp - Nxa)/xb)*yb -> 4
        // from 4 -> yp = Nya + (xp/xb)*yb - N(xa/xb)*yb
        //  -> yp - (xp*yb)/xb = N * (ya - (xa*yb)/xb)
        //  -> N = (yp*xb - xp*yb) / (ya*xb - xa*yb)
        // find min cost = 3*N + M
        // problem 2
        Long xa = coordinatesA.get("X"), xb = coordinatesB.get("X"), xp = coordinatesP.get("X");
        Long ya = coordinatesA.get("Y"), yb = coordinatesB.get("Y"), yp = coordinatesP.get("Y");
        
        Long upN = yp*xb - xp*yb;
        Long downN = ya*xb - xa*yb;
        if (upN % downN != 0) {
            return 0L;
        }
        Long n = upN / downN;
        Long upM = xp - n*xa;
        if (upM % xb != 0) {
            return 0L;
        }
        Long m = upM / xb;
        return 3*n + m;
        
        // problem 1
        // boolean hasMinCost = false;
        // Long minCost = 0L;

        // Long n = Long.max(xp/xa, yp/ya);
        // // int m  = (int)Long.max(xp/xb, yp/yb);
        // for (Long i = 0L; i <= n; i++) {
        //     Long rx = xp-xa*i, ry = yp-ya*i;
        //     if (rx%xb == 0 && ry%yb == 0 && rx/xb == ry/yb) {
        //         Long m = (rx/xb);
        //         if (hasMinCost == false || i*3 + m < minCost) {
        //             minCost = i*3 + m;
        //             break;
        //         }
        //     }
        //     // for (int j = 0; j <= m; j++) {
        //     //     Long xpp = i*xa + j*xb;
        //     //     Long ypp = i*ya + j*yb;
        //     //     if (xpp == xp && ypp == yp) {
        //     //         if (hasMinCost == false || i*3 + j < minCost) {
        //     //             hasMinCost = true;
        //     //             minCost = i*3 + j;
        //     //         }
        //     //     }
        //     // }
        // }

        // return minCost;
    }
    
}

public class Main {
    public static void main(String[] args) {
        Long sum = 0L;
        try {
            String input = Files.readString(Paths.get("Day13/input.txt"));
            String[] blocks = input.trim().split("\n\n");

            for (String block : blocks) {
                System.out.println("Processing block:");
                String[] lines = block.split("\n");
                Map<String, Long> coordA, coordB, coordP;
                coordA = new HashMap<>();
                coordB = new HashMap<>();
                coordP = new HashMap<>();

                for (String line : lines) {
                    if (line.startsWith("Button A:")) {
                        coordA = parseCoordinates(line);
                        System.out.println("Button A: " + coordA);
                    } else if (line.startsWith("Button B:")) {
                        coordB = parseCoordinates(line);
                        System.out.println("Button B: " + coordB);
                    } else if (line.startsWith("Prize:")) {
                        coordP = parseCoordinates(line);
                        System.out.println("Prize: " + coordP);
                        coordP.put("X", coordP.get("X")+10000000000000L);
                        coordP.put("Y", coordP.get("Y")+10000000000000L);
                    }
                }
                
                InputSection inputSolver = new InputSection(coordA, coordB, coordP);
                sum += inputSolver.solver1();

                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        System.out.println("Answer is => " + sum);
    }

    private static Map<String, Long> parseCoordinates(String line) {
        Map<String, Long> coordinates = new HashMap<>();
        Pattern pattern = Pattern.compile("X[+=](\\d+), Y[+=](\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            coordinates.put("X", Long.parseLong(matcher.group(1)));
            coordinates.put("Y", Long.parseLong(matcher.group(2)));
        }
        return coordinates;
    }
}
