package Estruturas;

public class UnionByRank extends VarianteBase {

    int[] rank;

    public UnionByRank(int n) {
        super(n);
        this.rank = new int[n];
        for (int i = 0; i < n; i++) {
            this.pai[i] = i;
            this.rank[i] = 0;
        }
    }

    @Override
    public int find(int i) {
        if (this.pai[i] == i) {
            return i;
        }
        return find(this.pai[i]);
    }

    @Override
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            if (this.rank[rootA] < this.rank[rootB]) {
                this.pai[rootA] = rootB;
            } else if (this.rank[rootA] > this.rank[rootB]) {
                this.pai[rootB] = rootA;
            } else {
                this.pai[rootB] = rootA;
                this.rank[rootA]++;
            }
        }
    }
}
