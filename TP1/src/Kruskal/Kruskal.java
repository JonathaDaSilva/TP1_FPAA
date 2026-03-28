package Kruskal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {
    public static List<Aresta> executarKruskal(int numVertices, List<Aresta> arestas) {
        List<Aresta> mst = new ArrayList<>();
        DSU dsu = new DSU(numVertices);

        Collections.sort(arestas);

        for (Aresta aresta : arestas) {
            int rootOrigem = dsu.find(aresta.origem);
            int rootDestino = dsu.find(aresta.destino);

            if (rootOrigem != rootDestino) {
                mst.add(aresta);
                dsu.union(rootOrigem, rootDestino);
            }
        }
        return mst;
    }
}
