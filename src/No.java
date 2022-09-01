public class No {
    public int vertice;
    public Double peso;
    public No proximo;
  
    public No(int valor, Double peso){
        this.vertice = valor;
        this.peso = peso;
        this.proximo = null;
    }
}