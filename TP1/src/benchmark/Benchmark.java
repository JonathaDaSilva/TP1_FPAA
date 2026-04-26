package benchmark;

import dsu.FullTarjan;
import dsu.Naive;
import dsu.UnionByRank;
import dsu.Dsu;
import kruskal.Grafo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Locale;

import static kruskal.Kruskal.executarKruskalDeVarianteParaGrafo;

public class Benchmark {

    private static final int[] N_VERTICES = {100, 500, 1_000, 1_500, 2_000};

    @SuppressWarnings("unchecked")
    private static final Class<? extends Dsu>[] VARIANTES = new Class[]{
            Naive.class,
            UnionByRank.class,
            FullTarjan.class
    };

    public static void executar() {
        System.out.println("Iniciando benchmark...\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {
            writer.println("n_vertices,tipo_grafo,variante,tempo_ns,tempo_ms,operacoes");

            for (int v : N_VERTICES) {
                int numArestas = (int)Math.sqrt(v * (v - 1.0) / 2) * 10;

                Grafo grafoAleatorio = GeradorGrafo.gerarAleatorio(v, numArestas);
                Grafo grafoPiorCaso  = GeradorGrafo.gerarPiorCaso(v);

                System.out.printf("=== n = %,d vertices ===%n", v);

                medir(writer, v, "aleatorio", grafoAleatorio);
                medir(writer, v, "pior_caso", grafoPiorCaso);
            }

            System.out.println("\nResultados salvos em: resultados.csv");

        } catch (IOException e) {
            System.err.println("Erro ao escrever CSV: " + e.getMessage());
        }
    }

    private static void medir(PrintWriter writer, int n, String tipoGrafo, Grafo grafo) {
        System.out.printf("  [%s]%n", tipoGrafo);

        for (int w = 0; w < 3; w++) {
            for (Class<? extends Dsu> variante : VARIANTES) {
                executarKruskalDeVarianteParaGrafo(variante, grafo);
            }
        }

        for (Class<? extends Dsu> variante : VARIANTES) {
            long tempoNs = medirVariante(variante, grafo);
            long operacoes = contarOperacoes(variante, grafo);
            registrar(writer, n, tipoGrafo, variante, tempoNs, operacoes);
        }
    }

    private static long medirVariante(Class<? extends Dsu> variante, Grafo grafo) {
        long inicio = System.nanoTime();
        executarKruskalDeVarianteParaGrafo(variante, grafo);
        return System.nanoTime() - inicio;
    }

    private static long contarOperacoes(Class<? extends Dsu> variante, Grafo grafo) {
        Dsu instancia = executarKruskalDeVarianteParaGrafo(variante, grafo);
        return instancia != null ? instancia.getOperacoes() : 0;
    }

    private static void registrar(PrintWriter writer, int n, String tipoGrafo,
                                  Class<? extends Dsu> variante, long tempoNs, long operacoes) {

        double tempoMs = tempoNs / 1_000_000.0;

        System.out.printf("    %-15s %,12d ns  (%8.3f ms)  ops: %,d%n",
                variante.getSimpleName(), tempoNs, tempoMs, operacoes);

        String tempoNsFmt = String.format(Locale.GERMANY, "%,d", tempoNs);
        String tempoMsFmt = String.format(Locale.US, "%,.3f", tempoMs).replace(",", ".");
        String operacoesFmt = String.format(Locale.GERMANY, "%,d", operacoes);

        writer.printf("%d,%s,%s,%s,%s,%s\n",
                n, tipoGrafo, variante.getSimpleName(), tempoNsFmt, tempoMsFmt, operacoesFmt);
    }
}
