import java.util.*;
import java.io.*;

public class Sports {
    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = new StringTokenizer("");
    public static void main(String[] args) throws Exception {
        int T = readInt();
        for (int t = 1; t <= T; t++) {
            String[] in = readString().split("-");
            int goalsIn = Integer.parseInt(in[0]);
            int goalsOut = Integer.parseInt(in[1]);

            int winDiff = goalsIn - goalsOut;
            int totGoals = goalsIn + goalsOut;
            if (goalsOut == 0) {
                System.out.printf("Case #%d: 1 0\n", t);
                continue;
            }
            long sfree = stressfree(winDiff, totGoals);
            long sfull = stressfull(winDiff, totGoals);
            System.out.printf("Case #%d: %d %d\n", t, sfree, sfull, goalsIn, goalsOut);
        }
    }

    public static long stressfree(int winDiff, int totGoals) {
        long[][] dp = new long[totGoals][totGoals];
        dp[0][0] = 1;
        for (int i = 1; i < totGoals; i++) {
            dp[i][1] += dp[i-1][0];
            dp[i][1] %= 1000000007;
            for (int c = 1; c < totGoals - 1; c++) {
                dp[i][c+1] += dp[i-1][c];
                dp[i][c+1] %= 1000000007;
                dp[i][c-1] += dp[i-1][c];
                dp[i][c-1] %= 1000000007;
            }
            dp[i][totGoals-2] += dp[i-1][totGoals-1];
            dp[i][totGoals-2] %= 1000000007;
        }
        return dp[totGoals-1][winDiff-1];
    }

    public static long stressfull(int winDiff, int totGoals) {
        int totBeforeRem = totGoals - winDiff; // goalsOut * 2
        long[][] dp = new long[totBeforeRem][totBeforeRem];
        dp[0][1] = 1; // in opponents favour
        for (int i = 1; i < totBeforeRem; i++) {
            dp[i][1] += dp[i-1][0];
            dp[i][1] %= 1000000007;
            for (int c = 1; c < totBeforeRem - 1; c++) {
                dp[i][c+1] += dp[i-1][c];
                dp[i][c+1] %= 1000000007;
                dp[i][c-1] += dp[i-1][c];
                dp[i][c-1] %= 1000000007;
            }
            dp[i][totBeforeRem-2] += dp[i-1][totBeforeRem-1];
            dp[i][totBeforeRem-2] %= 1000000007;
        }
        return dp[totBeforeRem-1][0];
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
}
