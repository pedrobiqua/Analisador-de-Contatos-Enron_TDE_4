import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class App {

    static int numeroVertice = 0;
    static Grafo grafo = new Grafo(0);

    public static void main(String[] args) throws Exception {
        System.out.println("TDE 4 - Projeyo Colaborativo");

        // Para ler arquivos
        Path path = FileSystems.getDefault().getPath("");
        String directoryName = path.toAbsolutePath().toString();
        String diretorioEron = directoryName + "\\Amostra Enron\\Amostra Enron";
        File directoryEron = new File(diretorioEron);
        
        ArrayList<Integer> caminho = new ArrayList<>();

        for (File file : directoryEron.listFiles()) {
            if (!(file.getName().equals(".DS_Store"))) {
                String filesInEron = diretorioEron + "\\" + file.getName() + "\\sent";

                try {
                    File fileInSentMail = new File(filesInEron);
                    if(fileInSentMail.exists()) {
                        for (File fileMail : fileInSentMail.listFiles()) {
                            //System.out.println(fileMail.getName());
                            leitor(filesInEron + "\\" + fileMail.getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Preciso deixar isso mais eficiente fazer a mesma coisa duas vezes não p
        for (File file : directoryEron.listFiles()) {
            if (!(file.getName().equals(".DS_Store"))) {
                String filesInEron = diretorioEron + "\\" + file.getName() + "\\_sent_mail";

                try {
                    File fileInSentMail = new File(filesInEron);
                    if(fileInSentMail.exists()) {
                        for (File fileMail : fileInSentMail.listFiles()) {
                            //System.out.println(fileMail.getName());
                            leitor(filesInEron + "\\" + fileMail.getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // a. (0.25 ponto) O n. de vértices do grafo.
        //grafo.NumeroDeVerticesDoGrafo();

        // b. (0.25 ponto) O n. de arestas do grafo.
        //grafo.NumeroDeArestasDoGrafo();

        // c. (0.25  ponto)  Os  20  indivíduos  que  possuem  maior  grau  de  saída  e  o  valor correspondente.
        // obs: Preciso mudar a lógica, pois não está 100% correto
        //grafo.VinteIndividuosMaiorGrauSaida();

        // d. (0.25 ponto) Os 20 indivíduos que possuem maior grau de entrada e o valor correspondente.
        //grafo.VinteIndividuosMaiorGrauEntrada();

        // Mostrar caminho para chegar de um vertice X para um Y
        //grafo.Dijkstra(0, 25);

        // Para o outro tenho que mostrar o inverso do dijkstra
        //grafo.DijkstraMaiorCaminho(0, 25);

        //grafo.buscaProfundidade(44, 0, caminho);
        grafo.buscaLargura(0, 40, caminho);
    }

    /**
     * Metodo que lê as linhas de um texto.
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
                    //System.out.println(string);

                    // Obtem o nome do from
                    if (string.equals("From")) {
                        tagFrom = true;
                    } else if (tagFrom) {
                        if (!string.trim().equals("")) {
                            //System.out.println(string.trim());
                            listaDeNomesFrom.add(string.trim());
                            tagFrom = false;
                        }
                    }

                    // Obtem nome do To
                    if (string.equals("To")) {
                        tagTo = true;
                    } else if (tagTo) {
                        String emailsTo[] = string.split(",");
                        for (int i = 0; i < emailsTo.length; i++) {
                            // Verifica se vai vir string sem nada
                            if (!emailsTo[i].trim().equals("")) {
                                // Verifica se não não vai vir email com terminação >
                                if (!(emailsTo[i].charAt(emailsTo[i].length()-1) == '>')) {
                                    //System.out.println(emailsTo[i].trim());
                                    listaDeNomesTo.add(emailsTo[i].trim());
                                }   
                            }
                        }
                        break;
                    }
                }
                if (tagTo) {
                    break;
                }

			} else
				break;
			linha = buffRead.readLine();
		}

        grafo.adicionaVerticeAoGrafoApartirDoEmail(listaDeNomesFrom, listaDeNomesTo);
		buffRead.close();
	}

}