package Estruturas;

public abstract class VarianteBase {
    int[] pai;
    VarianteBase(int n){
        this.pai = new int[n];
    }

    public abstract int find(int i);
    public abstract void union(int a, int b);
}
