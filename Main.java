import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class Main {
    private static Vector<Long> vector = new Vector<>();
    private static Map<String, Long> map = new LinkedHashMap<>();

    private static void solve1(){
        Vector<Long> newVector = new Vector<>();
        
        for (long num: vector){
            if(num == 0){
                // Case: If the stone is engraved with the number 0
                newVector.add(1L); // Add 1 as a long value
            } else {
                int logValue = (int) Math.log10(num); // Logarithm calculation remains the same

                if(num >= 10 && logValue % 2 == 1){
                    // If the stone is engraved with a number that has an even number of digits
                    long pow10 = (long) Math.pow(10, logValue / 2 + 1);
                    newVector.add(num / pow10);
                    newVector.add(num % pow10);
                } else {
                    // Multiply by 2024 for other cases
                    newVector.add(num * 2024);
                }
            }
        }

        vector = newVector;
    }

    private static long recur(long num, int k){
        String key = String.valueOf(num) + "," + String.valueOf(k);
        if (k == 0){
            return 1L;
        }
        if (map.get(key) != null) {
            return map.get(key);
        }

        int logValue = (int) Math.log10(num);
        if (num == 0) {
            long res = recur(1L, k-1);
            map.put(key, res);
            return res;
        } else if (num >= 10 && logValue % 2 == 1){
            long pow10 = (long) Math.pow(10, logValue / 2 + 1);
            long resL = recur(num / pow10, k-1);
            long resR = recur(num % pow10, k-1);
            map.put(key, resL + resR);
            return resL + resR;
        }
        long res3 = recur(num*2024, k-1);
        map.put(key, res3);
        return res3;
    }

    public static void main(String[] args) {
        System.out.println("Start!");

        // begin of Part 1
        // Initialize with larger values (e.g., 2599064, 475449) that go beyond int's range
        Collections.addAll(vector, 475449L, 2599064L, 213L, 0L, 2L, 65L, 5755L, 51149L);
        // Collections.addAll(vector, 125L, 17L);

        // // Perform 25 iterations
        // for (int i = 0; i < 25; i++) {
        //     solve1();
        // }
        
        // System.out.println("Sol1: Final size of vector: " + vector.size());
        // 193269
        long sum2 = 0L;
        int k = 75;
        for (long num: vector) {
            sum2 = sum2 + recur(num, k);
        }
        System.out.println("Sol2: Final size of vector: " + sum2);
        // end of Part 1

    }
}
