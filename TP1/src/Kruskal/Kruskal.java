package Kruskal;

import Estruturas.NaiveDSU;
import Estruturas.VarianteBase;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {


    public static List<Aresta> executarKruskalDeVarianteParaGrafo(VarianteBase variante, Grafo grafo) {
        List<Aresta> mst = new ArrayList<>();

        Collections.sort(grafo.getArestas());

        for (Aresta aresta : grafo.getArestas()) {
            int rootOrigem = variante.find(aresta.origem);
            int rootDestino = variante.find(aresta.destino);

            if (rootOrigem != rootDestino) {
                mst.add(aresta);
                variante.union(aresta.origem, aresta.destino);
            }
        }
        return mst;
    }
}
