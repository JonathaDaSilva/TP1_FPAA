import sys
import os
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

CSV_PATH = sys.argv[1] if len(sys.argv) > 1 else "resultados.csv"

df = pd.read_csv(CSV_PATH)

COLORS  = {"Naive": "#e74c3c", "UnionByRank": "#f39c12", "FullTarjan": "#2ecc71"}
MARKERS = {"Naive": "o",       "UnionByRank": "s",       "FullTarjan": "^"}
LABELS  = {"Naive": "Naive — O(n)",
           "UnionByRank": "Union by Rank — O(log n)",
           "FullTarjan":  "Full Tarjan — O(α(n))"}

os.makedirs("graficos", exist_ok=True)

for tipo in df["tipo_grafo"].unique():
    subset = df[df["tipo_grafo"] == tipo]

    fig, axes = plt.subplots(2, 2, figsize=(14, 10))
    fig.suptitle(f"DSU — grafo {tipo}", fontsize=15, fontweight="bold")

    configs = [
        (axes[0, 0], "tempo_ns",   "Tempo médio (ns)",  "Tempo — escala linear", False),
        (axes[0, 1], "tempo_ns",   "Tempo médio (ns)",  "Tempo — escala log",    True),
        (axes[1, 0], "operacoes",  "Operações",         "Operações — escala linear", False),
        (axes[1, 1], "operacoes",  "Operações",         "Operações — escala log",    True),
    ]

    for ax, col, ylabel, title, use_log in configs:
        for variante in ["Naive", "UnionByRank", "FullTarjan"]:
            d = subset[subset["variante"] == variante].sort_values("n_vertices")
            ax.plot(
                d["n_vertices"], d[col],
                label=LABELS[variante],
                color=COLORS[variante],
                marker=MARKERS[variante],
                linewidth=2,
                markersize=6,
            )

        ax.set_xlabel("n (vértices)")
        ax.set_ylabel(ylabel)
        ax.set_title(title)
        ax.legend(fontsize=8)
        ax.grid(True, alpha=0.3)
        ax.xaxis.set_major_formatter(ticker.FuncFormatter(lambda x, _: f"{int(x):,}"))

        if use_log:
            ax.set_yscale("log")
        else:
            ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda x, _: f"{int(x):,}"))

    plt.tight_layout()
    out = f"graficos/{tipo}.png"
    plt.savefig(out, dpi=150)
    plt.close()
    print(f"Salvo: {out}")

print("Pronto.")
