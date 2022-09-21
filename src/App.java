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
        System.out.println("TDE 4 - Projero Colaborativo");

        // Para ler arquivos
        Path path = FileSystems.getDefault().getPath("");
        String directoryName = path.toAbsolutePath().toString();
        String diretorioEron = directoryName + "\\Amostra Enron\\Amostra Enron";
        File directoryEron = new File(diretorioEron);

        //leitor(directoryName + "\\Amostra Enron\\Amostra Enron\\giron-d\\sent\\98");

        // Exemplo de como pegar o nome das pastas de um repositório
        for (File file : directoryEron.listFiles()) {
            if (!(file.getName().equals(".DS_Store"))) {
                String filesInEron = diretorioEron + "\\" + file.getName() + "\\sent";

                try {
                    File fileInSentMail = new File(filesInEron);
                    for (File fileMail : fileInSentMail.listFiles()) {
                        //System.out.println(fileMail.getName());
                        
                        // Já está lendo o e-mail
                        leitor(filesInEron + "\\" + fileMail.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    
            }
        }
        
        
        // Já está lendo o e-mail
        //leitor(diretorioEron + "\\brawner-s\\_sent_mail\\1");

        //Mostra o menor caminho
        grafo.Dijkstra(0, 1);

        
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
                            System.out.println(string.trim());
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
                                    System.out.println(emailsTo[i].trim());
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
