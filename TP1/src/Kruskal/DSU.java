package Kruskal;

public class DSU {
    int[] pai;
    int[] rank;

    public DSU(int n) {
        pai = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            pai[i] = i;
            rank[i] = 1;
        }
    }

    public int find (int i) {
        if (pai[i] == i) {
            return i;
        }
        return pai[i] = find(pai[i]);
    }

    public void union (int a, int b) {
        int p1 = find(a);
        int p2 = find(b);
        if (p1 != p2) {
            if (rank[p1] < rank[p2]) {
                pai[p1] = p2;
            } else if (rank[p1] > rank[p2]) {
                pai[p2] = p1;
            }  else {
                pai[p1] = p2;
                rank[p1]++;
            }
        }
    }
}
