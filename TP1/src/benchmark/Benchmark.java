package benchmark;

import dsu.FullTarjan;
import dsu.Naive;
import dsu.UnionByRank;
import dsu.Dsu;
import kruskal.Grafo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import static kruskal.Kruskal.executarKruskalDeVarianteParaGrafo;

public class Benchmark {

    private static final String CSV_PATH = "resultados.csv";
    private static final int[] N_VERTICES = {100, 500, 1_000, 1_500, 2_000};
    private static final int MEDICOES = 5;

    @SuppressWarnings("unchecked")
    private static final Class<? extends Dsu>[] VARIANTES = new Class[]{
            Naive.class,
            UnionByRank.class,
            FullTarjan.class
    };

    public static void executar() {
        File arquivo = new File(CSV_PATH);
        System.out.println("Iniciando benchmark...");
        System.out.println("CSV destino: " + arquivo.getAbsolutePath() + "\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            writer.println("n_vertices,tipo_grafo,variante,tempo_ns,tempo_ms,operacoes");

            for (int v : N_VERTICES) {
                int numArestas = (int)Math.sqrt(v * (v - 1.0) / 2) * 10;

                Grafo grafoAleatorio = GeradorGrafo.gerarAleatorio(v, numArestas);
                Grafo grafoPiorCaso  = GeradorGrafo.gerarPiorCaso(v);

                System.out.printf("=== n = %,d vertices ===%n", v);

                medir(writer, v, "aleatorio", grafoAleatorio);
                writer.flush();
                medir(writer, v, "pior_caso", grafoPiorCaso);
                writer.flush();
            }

            System.out.println("\nResultados salvos em: " + arquivo.getAbsolutePath());

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
            long totalNs = 0;
            Dsu dsu = null;
            for (int m = 0; m < MEDICOES; m++) {
                long inicio = System.nanoTime();
                dsu = executarKruskalDeVarianteParaGrafo(variante, grafo);
                totalNs += System.nanoTime() - inicio;
            }
            long tempoNs = totalNs / MEDICOES;
            long operacoes = dsu != null ? dsu.getOperacoes() : 0;
            registrar(writer, n, tipoGrafo, variante, tempoNs, operacoes);
        }
    }

    private static void registrar(PrintWriter writer, int n, String tipoGrafo,
                                  Class<? extends Dsu> variante, long tempoNs, long operacoes) {
        double tempoMs = tempoNs / 1_000_000.0;

        System.out.printf("    %-15s %,12d ns  (%8.3f ms)  ops: %,d%n",
                variante.getSimpleName(), tempoNs, tempoMs, operacoes);

        writer.printf(Locale.US, "%d,%s,%s,%d,%.3f,%d%n",
                n, tipoGrafo, variante.getSimpleName(), tempoNs, tempoMs, operacoes);
    }
}
