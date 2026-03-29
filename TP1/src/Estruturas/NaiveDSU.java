package Estruturas;

public class NaiveDSU extends VarianteBase {

    public NaiveDSU(int n) {
        super(n);
        for (int i = 0; i < n; i++) {
            this.pai[i] = i;
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
            this.pai[rootA] = rootB;
        }
    }
}
