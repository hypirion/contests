import java.util.*;
import java.io.*;

public class Homework {
    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = new StringTokenizer("");
    public static void main(String[] args) throws Exception {
        int[] primacities = new int[10000001];
        for (int i = 0; i <= 10000000; i++) {
            primacities[i] = primacity(i);
        }
        int T = readInt();
        for (int t = 1; t <= T; t++) {
            int A = readInt();
            int B = readInt();
            int K = readInt();
            int c = 0;
            for (int i = A; i <= B; i++) {
                if (primacities[i] == K) {
                    c++;
                }
            }
            System.out.printf("Case #%d: %d\n", t, c);
        }
    }
    static String readString() throws Exception {
        while (!st.hasMoreTokens()) {
            st = new StringTokenizer(stdin.readLine());
        }
        return st.nextToken();
    }
    static int readInt() throws Exception {
        return Integer.parseInt(readString());
    }
    static double readDouble() throws Exception {
        return Double.parseDouble(readString());
    }

    public static List<Integer> PRIME = primes((int)Math.sqrt(10000001));

    public static ArrayList<Integer> primes(int n) {
        ArrayList<Integer> result = new ArrayList<Integer>(n/2);
        result.add(2);
        result.add(3);
        for (int l = 5; l <= n; l += 2) {
            boolean isPrime = true;
            int sqrt = (int)Math.sqrt(l);
            for (int m: result) {
                if (m > sqrt)
                    break;
                if (l%m == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime)
                result.add(l);
        }
        return result;
    }
    public static int primacity(long n) {
        int count = 0;
        if (n == 1) {
            return 1;
        }

        int sqrt = (int) Math.sqrt(n);

        for (int prime : PRIME) {
            if (n == 1 || sqrt < prime)
                break;
            else {
                if (n % prime == 0) {
                    count++;
                }
                while (n % prime == 0) {
                    n /= prime;
                }
            }
        }
        if (n != 1) {
            count++;
        }
        return count;
    }
}
