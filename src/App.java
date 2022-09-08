import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("TDE 4 - Projero Colaborativo");

        Grafo grafo = new Grafo(0);

        grafo.cria_Vertice(0);
        grafo.cria_Vertice(1);
        grafo.cria_Vertice(2);
        grafo.cria_Vertice(3);

        //Para criar a adj basta passar a linha e a coluna.
        grafo.cria_Adj(0, 1, 2.0);
        grafo.cria_Adj(0, 2, 2.0);

        grafo.cria_Adj(1, 3, 1.0);
        grafo.cria_Adj(1, 2, 4.0);

        grafo.cria_Adj(2, 1, 2.0);
        grafo.cria_Adj(2, 3, 2.0);

        grafo.cria_Adj(3, 1, 4.0);
        grafo.cria_Adj(3, 1, 1.0);

        //grafo.adjacencias = grafo.atualizaTamanho();

        System.out.println();

        // Testando warshall
        // grafo.algoritimoWarshall();

        // Para ler arquivos
        Path path = FileSystems.getDefault().getPath("");
        String directoryName = path.toAbsolutePath().toString();

        // Já está lendo o e-mail
        //leitor(directoryName + "\\Amostra Enron\\Amostra Enron\\brawner-s\\_sent_mail\\1");

        //Mostra o menor caminho
        grafo.Dijkstra(0, 3);

        System.out.println();

        
    }

    /**
     * Metodo que le as linhas de um texto.
     * @param path Caminho do arquivo a ser analisado
     * @throws IOException
     */
    public static void leitor(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
        ArrayList<String> listaDeNomesFrom = new ArrayList<String>();
        ArrayList<String> listaDeNomesTo = new ArrayList<String>();

		String linha = "";
		while (true) {
			if (linha != null) {
				// System.out.println(linha);

                String[] separandoString = linha.split(":");
                boolean tagFrom = false;
                boolean tagTo = false;

                for (String string : separandoString) {
                    System.out.println(string);

                    // Obtem o nome do from
                    if (string.equals("From")) {
                        tagFrom = true;
                    } else if (tagFrom) {
                        listaDeNomesFrom.add(string);
                    }

                    // Obtem nome do To
                    if (string.equals("To")) {
                        tagTo = true;
                    } else if (tagTo) {
                        listaDeNomesTo.add(string);
                    }
                }

			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
	}
}
