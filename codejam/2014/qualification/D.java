import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class D {
  static BufferedReader stdin = new BufferedReader(new
      InputStreamReader(System.in));
  static StringTokenizer st = new StringTokenizer("");
  public static void main(String[] args) throws Exception {
    final int T = readInt();
    for (int t = 1; t <= T; t++) {
      final int N = readInt();
      TreeSet<Double> naomi = new TreeSet<Double>();
      TreeSet<Double> ken = new TreeSet<Double>();
      for (int i = 0; i < N; i++) {
        naomi.add(readDouble());
      }
      for (int i = 0; i < N; i++) {
        ken.add(readDouble());
      }
      int deceitful = 0;
      int sincere = 0;
      // begin with sincere
      TreeSet<Double> naomiSincere = new TreeSet<Double>(naomi);
      TreeSet<Double> kenSincere = new TreeSet<Double>(ken);
      while (!naomiSincere.isEmpty()) {
        double n = naomiSincere.first();
        naomiSincere.remove(n);
        Double k = kenSincere.higher(n);
        if (k == null) { // Ken loses here, so he picks his lowest one
          k = kenSincere.first();
        }
        kenSincere.remove(k);
        if (n > k) {
          sincere++;
        }
      }
      // deceitful
      while (!naomi.isEmpty()) {
        double k = ken.first();
        Double n = naomi.higher(k);
        if (n == null) {
          // Naomi doesn't have any higher than this, so terminate
          break;
        }
        ken.remove(k);
        naomi.remove(n);
        deceitful++;
      }
      
      System.out.printf("Case #%d: %d %d\n", t, deceitful, sincere);
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
}
