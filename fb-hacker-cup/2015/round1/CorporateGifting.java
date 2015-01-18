import java.util.*;
import java.io.*;

public class CorporateGifting {
    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = new StringTokenizer("");
    public static void main(String[] args) throws Exception {
        int T = readInt();
        for (int t = 1; t <= T; t++) {
            final int N = readInt();
            int[] parents = new int[N];
            Node[] nodes = new Node[N];
            HashSet<Integer> leaves = new HashSet<Integer>();
            for (int i = 0; i < N; i++) {
                nodes[i] = new Node();
                leaves.add(i);
            }
            readInt();

            for (int i = 1; i < N; i++) {
                int pid = readInt() - 1;
                leaves.remove(pid);
                Node parent = nodes[pid];
                parent.children.add(nodes[i]);
                nodes[i].parent = parent;
            }

            ArrayList<Node> current = new ArrayList<Node>();

            for (int i : leaves) {
                Node n = nodes[i];
                Node p = n.parent;
                p.child_done(n);
                if (p.children_computed == p.children.size()) {
                    current.add(p); // it's not "current" really, but oh well
                }
            }

            while (!current.isEmpty()) {
                ArrayList<Node> next_up = new ArrayList<Node>();
                for (Node n : current) {
                    n.compute();
                    Node p = n.parent;
                    if (n.parent != null) {
                        p.child_done(n);
                        if (p.children_computed == p.children.size()) {
                            next_up.add(p);
                        }
                    }
                }
                current = next_up;
            }
            System.out.printf("Case #%d: %d\n", t, nodes[0].min_cost);
        }
    }

    static class Node {
        int children_computed = 0;
        int min_cost = 1;
        int penalty_val = 1; // Read: The penalty is added if parent is 1
        int penalty = 1;
        int child_max_val = 0;
        ArrayList<Node> children;
        Node parent = null;
        Node() {
            children = new ArrayList<Node>();
        }
        void child_done(Node c) {
            children_computed++;
            child_max_val = Math.max(c.penalty_val, child_max_val);
        }
        void compute() {
            int highest_reasonable = child_max_val+2;
            int[] additional_cost = new int[highest_reasonable+1];
            for (int i = 1; i <= highest_reasonable; i++) {
                additional_cost[i] = i;
            }
            int min_no_penalty = 0;
            for (Node c : children) {
                additional_cost[c.penalty_val] += c.penalty;
                min_no_penalty += c.min_cost;
            }
            // track best and next best
            int lowest_penalty = Integer.MAX_VALUE;
            int second_lowest_penalty = Integer.MAX_VALUE;
            int lowest_penalty_val = -1;
            for (int i = 1; i <= highest_reasonable; i++) {
                if (lowest_penalty >= additional_cost[i]) {
                    second_lowest_penalty = lowest_penalty;
                    lowest_penalty = additional_cost[i];
                    lowest_penalty_val = i;
                } else if (second_lowest_penalty >= additional_cost[i]) {
                    second_lowest_penalty = additional_cost[i];
                }
            }
            // you're punished if you use this value, otherwise you're fine :)
            penalty_val = lowest_penalty_val;
            // diff between lowest and second lowest is the actual penalty
            penalty = second_lowest_penalty - lowest_penalty;
            min_cost = min_no_penalty + lowest_penalty;
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

}
