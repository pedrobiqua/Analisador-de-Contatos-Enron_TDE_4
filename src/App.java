public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("TDE 4 - Projero Colaborativo");

        Grafo grafo = new Grafo(4);

        grafo.cria_Vertice(0);
        grafo.cria_Vertice(1);
        grafo.cria_Vertice(2);
        grafo.cria_Vertice(3);

        //Para criar a adj basta passar a linha e a coluna.
        grafo.cria_Adj(0, 1, 2.0);
        grafo.cria_Adj(0, 2, 2.0);
        grafo.cria_Adj(0, 3, 2.0);

        grafo.cria_Adj(1, 3, 4.0);
        grafo.cria_Adj(1, 2, 4.0);

        grafo.cria_Adj(2, 1, 2.0);
        grafo.cria_Adj(2, 3, 2.0);

        grafo.cria_Adj(3, 1, 4.0);
        grafo.cria_Adj(3, 1, 1.0);

        System.out.println();

        // Testando warshall
        // grafo.algoritimoWarshall();

        System.out.println();
        
    }
}
