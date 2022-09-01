import java.util.ArrayList;

public class Grafo {
    ArrayList<Vertice> listaVertices;
    Lista adjacencias[];
    public double[][] matriz;
    public boolean[][] matrizBoolean;

    Double infinitDouble = Double.MAX_VALUE;

    int tamanho;

    /**
     * Contrutor do Grafo
     * @param tamanho Tamanho do grafo, onde será o total de vertices  i x j
     */    
    public Grafo(int tamanho) {

        this.tamanho = tamanho;
        listaVertices = new ArrayList<Vertice>();
        adjacencias = new Lista[tamanho];
        matriz = new double[tamanho][tamanho];
        matrizBoolean = new boolean[tamanho][tamanho];

        //Inicializa as posições dos vertices
        for (int index = 0; index < adjacencias.length; index++) {
           adjacencias[index] = new Lista();
        }
    }

    /**
     * 
     * @param i Linha / Vertice
     * @param j Coluna / Vertce
     * @param peso Peso da ligaçao i x j
     */
    public void cria_Adj(int i, int j, Double peso) {
        adjacencias[i].insere(j, peso);
    }

    
    public void matrizTempDouble() {
        for (int i = 0; i < adjacencias.length; i++) {
            for (int j = 0; j < listaVertices.size(); j++) {
                double pesoAresta = pesquisa_pesoAdj(i, j);
                if (pesoAresta != infinitDouble) {
                    matriz[i][j] = pesoAresta;
                } else {
                    matriz[i][j] = infinitDouble;
                }
            }
            
        }
    }

    public void matrizTempBoolean() {
        for (int i = 0; i < adjacencias.length; i++) {
            for (int j = 0; j < listaVertices.size(); j++) {
                double pesoAresta = pesquisa_pesoAdj(i, j);
                if (pesoAresta != infinitDouble) {
                    matrizBoolean[i][j] = true;
                } else {
                    matrizBoolean[i][j] = false;
                }
            }
            
        }
    }

    /**
     * Imprime a matriz
     * @param matriz Recebe uma matriz de pesos
     */
    public void printMatriz(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print((matriz[i][j] == infinitDouble) ? "inf " : matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 
     * @param matriz Recebe uma matriz de booleanos
     */
    public void printMatriz(boolean[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j]+ " ");
            }
            System.out.println();
        }
    }

    public void algoritimoWarshall(){
        int k, j , i;

        this.matrizTempBoolean();

        // algoritmo de Warshall
        for(k = 0; k < tamanho; k++){
            for(i = 0; i < tamanho; i++){
                if (matrizBoolean[i][k]){
                    for(j = 0; j < tamanho; j++){
                        matrizBoolean[i][j] = matrizBoolean[i][j]  ||  matrizBoolean[k][j];
                    }
                }
            }
        }

        //imprime a matriz
        for(int x = 0; x < tamanho; x++){
            System.out.println("");
            for(int y = 0; y < tamanho; y++){
                System.out.print(" "+ matrizBoolean[x][y]);
            }
        }
        System.out.println();
    }

    /**
     * Pesquisa o peso da adjacência i com j;
     * @param i Linha
     * @param j Coluna
     * @return
     */
    public double pesquisa_pesoAdj(int i, int j) {

        No p = adjacencias[i].primeiro;
        while (p != null) {
        // Usar essa estrutura para percorrer a lista de adjacencias
            if (p.vertice == j) {
                //System.out.println("Valor achado: " + p.vertice);
                return p.peso;
            }
            
            p = p.proximo;
        }

        return infinitDouble;
    }

    /**
     * Se a adjacência existir o metodo retorna true, se for falso retorna false
     * @param i Linha
     * @param j Coluna
     * @return
     */
    public boolean verifica_Adj(int i, int j) {

        No p = adjacencias[i].primeiro;
        while (p != null) {
        // Usar essa estrutura para percorrer a lista de adjacencias
            if (p.vertice == j) {
                return true;
            }
            
            p = p.proximo;
        }

        return false;
    }

    /**
     * Cria um novo vertice e adiciona a lista de vertices
     * @param numeroVertice Número do novo vertice
     */
    public void cria_Vertice(int numeroVertice){
        Vertice novoVertice = new Vertice(numeroVertice);
        listaVertices.add(novoVertice);
    }

}
