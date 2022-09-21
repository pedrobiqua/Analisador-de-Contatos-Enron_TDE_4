public class No {
    public int vertice;
    public String email;
    public Double peso;
    public No proximo;
  
    public No(int vertice, String email, Double peso){
        this.vertice = vertice;
        this.email = email;
        this.peso = peso;
        this.proximo = null;
    }
}