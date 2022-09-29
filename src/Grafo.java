import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Grafo {
    /**
     * Lista de vertices do grafo, é adicionado um novo vertice no metodo cria vertice.
     */
    public ArrayList<Vertice> listaVertices;

    /**
     * Lista de graus de saida, onde o indice é representado pelo vertice.
     */
    public ArrayList<Integer> listaGrausSaida;

    /**
     * Lista de graus de entrada, onde o indice é representado pelo vertice.
     */
    public ArrayList<Integer> listaGrausEntrada;

    /**
     * Lista encadeada de adjacências.
     */
    public Lista adjacencias[];

    /**
     * Matriz adjacência, onde convertemos a lista de adjacências para uma matriz de pesos.
     */
    private double[][] matriz;

    /**
     * Matriz boleana usada para o algoritimo de Warshall, onde convertemos a lista de adjacências para uma matriz de boleanos
     */
    private boolean[][] matrizBoolean;

    /**
     * Constante do maior número double.
     */
    private final Double infinitDouble = Double.MAX_VALUE;

    /**
     * Tamanho do grafo
     */
    private int tamanho;

    int vertice;

    /**
     * Contrutor do Grafo
     * @param tamanho Tamanho do grafo, onde será o total de vertices  i x j
     */    
    public Grafo(int tamanho) {

        this.tamanho = tamanho;
        listaVertices = new ArrayList<Vertice>();
        listaGrausSaida = new ArrayList<Integer>();
        listaGrausEntrada = new ArrayList<Integer>();
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
    public void cria_Adj(int i, int j, String email, Double peso) {
        adjacencias[i].insere(j, email, peso);
        listaGrausSaida.set(i, listaGrausSaida.get(i) + 1);
        listaGrausEntrada.set(j, listaGrausEntrada.get(j) + 1);
    }

    /**
     * Monta matriz de pesos a partir da lista de adjacência
     */
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

    /**
     * Monta matriz de booleanos a partir da lista de adjacência
     */
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
     * Imprime a matriz
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

    /**
     * Metodo que aplica o algoritimo de Warshall
     */
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
     * Implementação do algortimo Dijkstra que encontra o menor caminho.
     * @param x Vertice x.
     * @param y Vertice y.
     */
    public void Dijkstra( int x, int y) {
        if (x < 0 || y > adjacencias.length) {
            System.out.println("Não é possivel fazer o Dijkstra");
        } else {
            // Instânciando as listas de caminho, distancia, pai e aberto.
            List<Integer> caminhoList = new ArrayList<Integer>();

            double[] distancia = new double[tamanho];
            int[] pai = new int[tamanho];
            boolean[] aberto = new boolean[tamanho];

            // Setando as listas a serem analisadas.
            for (int i = 0; i < tamanho; i++) {
                if (i == x) {
                    distancia[i] = 0;
                    pai[i] = -1;
                    aberto[i] = true;
                } else {
                    distancia[i] = infinitDouble;
                    pai[i] = -1;
                    aberto[i] = true;
                }
            }

            while (true) {
                double menorDistancia = infinitDouble;
                int menorIndice = -1;
                for (int i = 0; i < tamanho; i++) {
                    if (aberto[i] && distancia[i] < menorDistancia) {
                        menorDistancia = distancia[i];
                        menorIndice = i;
                    }
                }

                // Se não for achado ele sai do while
                if (menorIndice == -1) {
                    break;
                }

                aberto[menorIndice] = false;
                Lista adj = Adjacentes(menorIndice);

                // Percorre a as adjacencias do menor indice.
                No p = adj.primeiro;
                while (p != null) {
                    if (distancia[menorIndice] + pesquisa_pesoAdj(menorIndice, p.vertice) < distancia[p.vertice]) {
                        distancia[p.vertice] = distancia[menorIndice] + pesquisa_pesoAdj(menorIndice, p.vertice);
                        pai[p.vertice] = menorIndice;
                    }
                    p = p.proximo;
                }
            }

            int p = y;
            while(p!=-1) {
                caminhoList.add(p);
                p = pai[p];
            }

            Collections.sort(caminhoList);
            System.out.println("A menor distancia entre "+x+" e "+y+" é: "+distancia[y]);
            System.out.println("Caminho entre  "+x+" e "+y+" é: "+caminhoList);
        }        
    }

    /**
     * Retorna o peso da adjacência i x j
     * @param i Vertice I
     * @param j Vertice J
     * @return p.peso || infinitDouble
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
     * @return True or False
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
     * Metódo que entrega as adjacencias daquele vertice passado como parametro.
     * @param i Vertice a ser analisado.
     * @return Retorna a lista de adjacências do vertice passado como parametro.
     */
    public Lista Adjacentes(int i) {
        return adjacencias[i];
    }

    public int[] adjacentesInt(int i) {
        int[] adjacentes = new int[listaVertices.size()];
        No p = adjacencias[i].primeiro;
        int j = 0;
        while (p != null) {
            adjacentes[j] = p.vertice;           
            p = p.proximo;
            j++;
        }
        return adjacentes;
    }

    /**
     * Cria um novo vertice e adiciona a lista de vertices
     * @param numeroVertice Número do novo vertice
     */
    public void cria_Vertice(int numeroVertice, String email){
        Vertice novoVertice = new Vertice(numeroVertice, email);
        adjacencias = atualizaTamanho();
        listaVertices.add(novoVertice);
        listaGrausSaida.add(0);
        listaGrausEntrada.add(0);
    }

    /**
     * Atualiza o tamanho do grafo quando é criado um novo vertice
     * @return Retorna a lista de adjacências com uma nova posição
     */
    private Lista[] atualizaTamanho() {
        tamanho++;
        Lista[] temp = new Lista[tamanho];

        for (int i = 0; i < adjacencias.length; i++) {
            temp[i] = adjacencias[i];
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == null) {
                temp[i] = new Lista();
            }
        }

        matriz = new double[tamanho][tamanho];
        matrizBoolean = new boolean[tamanho][tamanho];

        return temp;
        
    }

    /**
     * Verifica se existe o vertice
     * @param i
     * @return true ou false
     */
    public boolean verifica_Vertice(int i) {
        for (int j = 0; j < listaVertices.size(); j++) {
            if (listaVertices.get(j).dado == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Atualiza o peso quando já existe a adjacência
     * @param i vertice i 
     * @param j vertice j adjacênte a i
     */
    public void atualizaPeso(int i, int j) {

        No p = adjacencias[i].primeiro;
        while (p != null) {
        // Usar essa estrutura para percorrer a lista de adjacencias
            if (p.vertice == j) {
                p.peso += 1.0;
            }
            
            p = p.proximo;
        }
    }

    /**
     * Busca o vertice a partir do email, 
     * @param email email usado como referência para a busca
     * @return retorna o numero do vertice correspondente ao enviado por e-mail
     */
    public int buscaVerticeApartirDaString(String email) {

        for (int i = 0; i < listaVertices.size(); i++) {
            if (listaVertices.get(i).email.equals(email)) {
                return i;
            }

            // printa o indice
            //if (listaVertices.get(i).email.equals()) {
            //    System.out.println(i);
            //}
        }

        return -1;
        
    }

    /**
     * Metódo de busca em profundidade.
     * @param atual vertice atual
     * @param destino vertice a ser buscado
     * @param caminho ArrayList de inteiros para montar o caminho
     */
    public void buscaProfundidade(int atual, int destino, ArrayList<Integer> caminho){
        if(!caminho.contains(atual)){ 
            // vértice atual será adicionado ao caminho
			caminho.add(atual); 
		}

         //se o vértice atual for igual ao destino
		if(atual == destino){
            System.out.println("\n\n\n\n\n\n\n\n");
			System.out.println("Caminho: ");
			for(int i = 0; i < caminho.size(); i++){
                // agora, printaremos o caminho
				System.out.println(listaVertices.get(caminho.get(i)).dado + ": " + listaVertices.get(caminho.get(i)).email + " ");
			}
		}

		int[] adj = adjacentesInt(atual);
		for(int i : adj){
			if(!caminho.contains(i)){
				if(i == -1){
					break;
				}
                // recursivo
				buscaProfundidade(i, destino, caminho);
				if(caminho.contains(destino)){
					break;
				}
			}
		}
	}

    /**
     * Busca em largura
     * @param atual
     * @param destino
     * @param caminho
     */
    public void buscaLargura(int atual, int destino, ArrayList<Integer> caminho){
        // array para guardar os saltos
		ArrayList<Integer> listaSaltos = new ArrayList<>();

		for(int i = 0; i < tamanho; i++){
			ArrayList<Integer> visitados = new ArrayList<>();
            // chama recursivamente o metodo saltos
			saltos(atual, i, listaSaltos, visitados); 
            // verifica se o destino já esta na lista de saltos
			if(listaSaltos.contains(destino)){ 
				break;
			}
		}
        System.out.println("\n\n\n\n\n\n\n\n");
		System.out.println("Caminho: ");
		System.out.println(listaVertices.get(atual).dado + ": " + listaVertices.get(atual).email + " "); // printa o email atual
		for(int i : listaSaltos){
			System.out.println(listaVertices.get(i).dado + ": " + listaVertices.get(i).email + " "); // printa o email do indíce
			// caso o i seja igual ao destino
            if(i == destino){
				break;
			}
		}
	}

    /**
     * Saltos do busca em largura
     * @param origem
     * @param pulos
     * @param listaSaltos
     * @param visitados
     */
	public void saltos(int origem, int pulos, ArrayList<Integer> listaSaltos, ArrayList<Integer> visitados){
        // pegar as adjacentes da origem
		int[] adj = adjacentesInt(origem);

        // verifica se a origem ja foi adicionada nos visitados
		if(!visitados.contains(origem)){
			visitados.add(origem);
		}

        // criterio para adicionar um numero nos listas saltos
		if(pulos == 1){
            // passar pelas adjacencias da origem
			for(int i : adj){
                // checar se acabaram as adjacentes
				if(i == -1){
					break;
                    // adiciona a adjacência na lista
				}else if(!listaSaltos.contains(i) && !visitados.contains(i)){
					listaSaltos.add(i);
				}
			}
		}
		if(pulos > 1){
			for(int i : adj){
				if(i == -1){
					break;
				}
				if(!visitados.contains(i)){
                    // chama recursivamente subtraindo 1 valor do pulo
					saltos(i, pulos - 1, listaSaltos, visitados);
				}
			}
		}
	}

    /**
     * Distância de um ponto X a Y de acordo com uma distancia passada como parâmetro
     * @param atual
     * @param caminho
     * @param alcance
     */
    public void distanciaXY(int atual, ArrayList<Integer> caminho, int alcance){
        // array para guardar os saltos
		ArrayList<Integer> listaSaltos = new ArrayList<>();

		//for(int i = 0; i < alcance + 1; i++){
            // chama recursivamente o metodo saltos
			saltosDistancia(atual, alcance, listaSaltos, alcance); 
            // verifica se o destino já esta na lista de saltos
		//}
		//System.out.println(listaVertices.get(atual).dado + ": " + listaVertices.get(atual).email + " "); // printa o email atual
		//System.out.println(listaVertices.get(listaSaltos.size()).dado);
        
        for(int i : listaSaltos){
            if (i == 0) {
                break;
            }
            // printa o email do indíce
			System.out.println(listaVertices.get(i).dado + ": " + listaVertices.get(i).email + " ");
		}
        
	}

    /**
     * Saltos usado para o metódo
     * @param origem
     * @param pulos
     * @param listaSaltos
     * @param distancia
     */
    public void saltosDistancia(int origem, int pulos, ArrayList<Integer> listaSaltos, int distancia){
        // pegar as adjacentes da origem
        int[] adj = adjacentesInt(origem);

        // criterio para adicionar um numero nos listas saltos
		if(pulos == 1){
            // passar pelas adjacencias da origem
			for(int i : adj){
                // checar se acabaram as adjacentes
				if(i == -1 ){
					break;
				}else {
					listaSaltos.add(i);
				}
			}
		}
		if(pulos > 1){
			for(int i : adj){
				if(i == -1){
					break;
				} else {
                    saltosDistancia(i, pulos - 1, listaSaltos, distancia);
                }
			}
		}
	}

    /**
     * A adiciona vertice no grafo utilizando e-mail como ponte.
     * @param emailsFrom
     * @param emailsTo
     */
    public void adicionaVerticeAoGrafoApartirDoEmail(ArrayList<String> emailsFrom, ArrayList<String> emailsTo) {
        for (int i = 0; i < emailsFrom.size(); i++) {

            int vertice_i = buscaVerticeApartirDaString(emailsFrom.get(i));
            // Se o vertice nao existir
            if (vertice_i == -1) {
                cria_Vertice(App.numeroVertice, emailsFrom.get(i));
                vertice_i = App.numeroVertice;
                App.numeroVertice++;
            }
            for (int j = 0; j < emailsTo.size(); j++) {

                int vertice_j = buscaVerticeApartirDaString(emailsTo.get(j));
                // Se o vertice nao existir
                if (vertice_j == -1) {
                    cria_Vertice(App.numeroVertice, emailsTo.get(j));
                    vertice_j = App.numeroVertice;
                    App.numeroVertice++;
                }

                // Se já existir essa adjacência apenas atualiza o peso.
                // Se não cria-se a nova adjacência.
                if (verifica_Adj(vertice_i, vertice_j)) {
                    atualizaPeso(vertice_i, vertice_j);
                } else {
                    cria_Adj(vertice_i, vertice_j, emailsTo.get(j), 1.0);
                }
                
            }
        }
    }

    //-----------------------------------------------------------------------------
    // 2. Implemente métodos/funções para extrair as seguintes informações gerais:

    /**
     * O número de vértices do grafo
     */
    public void NumeroDeVerticesDoGrafo() {
        System.out.println("Número de vertices do grafo: " + listaVertices.size());
    }

    /**
     * O número de arestas do grafo
     */
    public void NumeroDeArestasDoGrafo() {
        int contador = 0;
        for (int i = 0; i < listaVertices.size(); i++) {
            No p = adjacencias[i].primeiro;
            // percorre a lista do vertice i;
            while (p != null) {
                contador++;
                p = p.proximo;
            }   
        }

        System.out.println("Número de arestas do grafo: " + contador);
    }

    /**
     * Os  20  indivíduos  que  possuem  maior  grau  de  saída  e  o  valor.
     */
    public void VinteIndividuosMaiorGrauSaida() {
        ArrayList<Integer> visitados = new ArrayList<Integer>();
        ArrayList<Integer> topVinte = new ArrayList<Integer>();
        int contador = 1;

        for (int i = 0; i < listaGrausSaida.size(); i++) {
            topVinte.add(listaGrausSaida.get(i));
        }

        Collections.sort(topVinte, Collections.reverseOrder());

        // Buscar indices dos vertices
        System.out.println("\nTop 20 maiores graus de saida");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < listaGrausSaida.size(); j++) {
                if (topVinte.get(i) == listaGrausSaida.get(j)) {
                    if (verificaVisitado(visitados, j)) {
                        System.out.println(contador + ": " + listaVertices.get(j).email);
                        visitados.add(j);
                        contador++;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Verifica se o vertice já foi visitado ou não
     * @param visitados Array de visitados
     * @param verticeAnalisado Vertice a ser analisado
     * @return
     */
    public boolean verificaVisitado(ArrayList<Integer> visitados, int verticeAnalisado) {
        for (int i = 0; i < visitados.size(); i++) {
            if (visitados.get(i) == verticeAnalisado) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Os 20 indivíduos que possuem maior grau de entrada e o valor correspondente.
     */
    public void VinteIndividuosMaiorGrauEntrada() {
        ArrayList<Integer> visitados = new ArrayList<Integer>();
        ArrayList<Integer> topVinte = new ArrayList<Integer>();
        int contador = 1;

        for (int i = 0; i < listaGrausEntrada.size(); i++) {
            topVinte.add(listaGrausEntrada.get(i));
        }

        Collections.sort(topVinte, Collections.reverseOrder());

        // Buscar indices dos vertices
        System.out.println("\nTop 20 maiores graus de entrada");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < listaGrausEntrada.size(); j++) {
                if (topVinte.get(i) == listaGrausEntrada.get(j)) {
                    if (verificaVisitado(visitados, j)) {
                        System.out.println(contador + ": " + listaVertices.get(j).email);
                        visitados.add(j);
                        contador++;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Implementação do algortimo Dijkstra que encontra o maior caminho.
     * @param x Vertice x.
     * @param y Vertice y.
     */
    public void DijkstraMaiorCaminho( int x, int y) {
        if (x < 0 || y > adjacencias.length) {
            System.out.println("Não é possivel fazer o Dijkstra");
        } else {
            // Instânciando as listas de caminho, distancia, pai e aberto.
            List<Integer> caminhoList = new ArrayList<Integer>();

            double[] distancia = new double[tamanho];
            int[] pai = new int[tamanho];
            boolean[] aberto = new boolean[tamanho];

            // Setando as listas a serem analisadas.
            for (int i = 0; i < tamanho; i++) {
                if (i == x) {
                    distancia[i] = 0;
                    pai[i] = -1;
                    aberto[i] = true;
                } else {
                    distancia[i] = infinitDouble;
                    pai[i] = -1;
                    aberto[i] = true;
                }
            }

            while (true) {
                double maiorDistancia = infinitDouble;
                int menorIndice = -1;
                for (int i = 0; i < tamanho; i++) {
                    if (aberto[i] && distancia[i] < maiorDistancia) {
                        maiorDistancia = distancia[i];
                        menorIndice = i;
                    }
                }

                // Se não for achado ele sai do while
                if (menorIndice == -1) {
                    break;
                }

                aberto[menorIndice] = false;
                Lista adj = Adjacentes(menorIndice);

                // Percorre a as adjacencias do menor indice.
                No p = adj.primeiro;
                while (p != null) {
                    if (distancia[menorIndice] + (1/pesquisa_pesoAdj(menorIndice, p.vertice)) < distancia[p.vertice]) {
                        distancia[p.vertice] = distancia[menorIndice] + (1/pesquisa_pesoAdj(menorIndice, p.vertice));
                        pai[p.vertice] = menorIndice;
                    }
                    p = p.proximo;
                }
            }

            int p = y;
            while(p!=-1) {
                caminhoList.add(p);
                p = pai[p];
            }

            Collections.sort(caminhoList);
            System.out.println("Caminho entre  "+x+" e "+y+" é: "+caminhoList);
        }        
    }


}
