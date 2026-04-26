package dsu;

public class FullTarjan extends Dsu {

    int[] rank;

    public FullTarjan(int n) {
        super(n);
        this.rank = new int[n];
        for (int i = 0; i < n; i++) {
            this.pai[i] = i;
            this.rank[i] = 0;
        }
    }

    @Override
    public int find(int i) {
        operacoes++;
        if (this.pai[i] == i) {
            return i;
        }
        return this.pai[i] = find(this.pai[i]);
    }

    @Override
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            operacoes++;
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
