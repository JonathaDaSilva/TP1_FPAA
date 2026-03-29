import Estruturas.NaiveDSU;
import Estruturas.UnionByRank;
import Kruskal.Aresta;
import Kruskal.Grafo;

import java.util.ArrayList;
import java.util.List;

import static Kruskal.Kruskal.executarKruskalDeVarianteParaGrafo;

public class Main {
    public static void main(String[] args) {
        Grafo grafoBase = new Grafo(4);
        int V = 4;
        List<Aresta> arestas = new ArrayList<>();
        grafoBase.addAresta(0, 1, 10);
        grafoBase.addAresta(0, 2, 6);
        grafoBase.addAresta(0, 3, 5);
        grafoBase.addAresta(1, 3, 15);
        grafoBase.addAresta(2, 3, 4);

        long inicioNaive = System.nanoTime();
        List<Aresta> mst00 = executarKruskalDeVarianteParaGrafo(NaiveDSU.class, grafoBase);
        long fimNaive = System.nanoTime();

        long inicioUbR = System.nanoTime();
        List<Aresta> mst01 = executarKruskalDeVarianteParaGrafo(UnionByRank.class, grafoBase);
        long FimUbR = System.nanoTime();

        System.out.println("Naive: " + (fimNaive - inicioNaive) + " ns"
        + "\nUnionByRank: " + (FimUbR - inicioUbR) + " ns");
    }
}