package Kruskal;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    int numVertices;
    List<Aresta> arestas;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.arestas = new ArrayList<Aresta>();
    }

    public void addAresta(int origem, int destino, int peso) {
         if(origem < 0 || origem >= numVertices || destino < 0 || destino >= numVertices)
            throw new IllegalArgumentException("Vértices devem estar entre 0 e " + (numVertices - 1));

        this.arestas.add(new Aresta(origem, destino, peso));
    }
    public int getNumVertices() {
        return numVertices;
    }
    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }
    public List<Aresta> getArestas() {
        return arestas;
    }
}
