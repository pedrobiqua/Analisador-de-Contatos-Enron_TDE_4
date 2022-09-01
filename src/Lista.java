public class Lista {
  public No primeiro;
  public No aresta;

  public Lista() {
    primeiro = null;
    aresta = null;
  }

  public void insere(int dado, Double peso) {

    No p = new No(dado, peso);

    if (aresta != null) {
      this.aresta.proximo = p;
    } else {
      this.primeiro = p;
    }
    this.aresta = p;

  }

  public void imprimir() {
    No p = this.primeiro;
    while (p != null) {
      // Usar essa estrutura para percorrer a lista de adjacencias
      System.out.println("Valor: " + p.vertice);
      p = p.proximo;
    }
  }

  public boolean vazio() {
    if (primeiro == null && aresta == null) {
      System.out.println("A fila está vazia");
      return true;
    }
    return false;
  }

}