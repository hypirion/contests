import java.util.*;
import java.io.*;

// Increase stack size if it blows up

public class Autocomplete {
    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = new StringTokenizer("");
    static final int TCOUNT = 'z'-'a'+1;
    public static void main(String[] args) throws Exception {
        int T = readInt();
        for (int t = 1; t <= T; t++) {
            final int N = readInt();
            String[] strs = new String[N];
            for (int i = 0; i < N; i++) {
                strs[i] = readString();
            }
            // short hack
            Trie root = new Trie();
            int presses = 0;
            for (String s : strs) {
                root.insert(s, 0);
                Trie trie = root;
                int i = 0;
                do {
                    presses++;
                    trie = trie.write(s.charAt(i));
                    i++;
                } while (!trie.unambiguous() && i < s.length());
            }
            System.out.printf("Case #%d: %d\n", t, presses);
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

    static class Trie {
        Trie[] children;
        int entries;
        public Trie(){
            children = new Trie[TCOUNT];
            entries = 0;
        }
        public void insert(String s, int i) {
            entries++;
            if (i < s.length()) {
                int c = s.charAt(i) - 'a';
                if (children[c] == null) {
                    children[c] = new Trie();
                }
                children[c].insert(s, i+1);
            }
        }

        public boolean unambiguous() {
            return entries == 1;
        }

        public Trie write(char c) {
            return children[c - 'a'];
        }
    }
}
