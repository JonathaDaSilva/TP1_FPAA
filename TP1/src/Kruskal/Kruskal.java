package Kruskal;

import Estruturas.NaiveDSU;
import Estruturas.VarianteBase;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {


    public static List<Aresta>  executarKruskalDeVarianteParaGrafo(Class<? extends VarianteBase> clazz, Grafo grafo) {
        List<Aresta> mst = new ArrayList<>();

        VarianteBase variante;
        try {
            variante = (VarianteBase) clazz.getDeclaredConstructors()[0].newInstance(grafo.getNumVertices());
        } catch (Exception e) {
            System.out.println("Incapaz de instanciar variante de DSU do tipo:" + clazz.getName());
            return mst;
        }

        Collections.sort(grafo.getArestas());

        for (Aresta aresta : grafo.getArestas()) {
            // Verifica se adicionar a aresta criaria um ciclo
            if (variante.find(aresta.origem) != variante.find(aresta.destino)) {
                mst.add(aresta);
                variante.union(aresta.origem, aresta.destino);
            }
        }

        return mst;
    }
}
