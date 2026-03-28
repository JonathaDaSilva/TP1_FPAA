package Kruskal;
import java.util.Comparator;

public class Aresta implements Comparable<Aresta> {
    public int origem, destino, peso;

    public Aresta(int origem, int destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public int compareTo(Aresta o) {
        return Integer.compare(this.origem, o.origem);
    }
}
