package arvore;
import arvoreB.ArvoreB;
import arvoreB.Musica;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class CriaArvoreB {

  public static void main(String[] args) throws Exception {
    ArvoreB acervo = new ArvoreB(2); // Ordem da árvore B
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      System.out.println("\nMenu:");
      System.out.println("1 - Inserir música manualmente");
      System.out.println("2 - Carregar músicas de arquivo");
      System.out.println("3 - Pesquisar por chave");
      System.out.println("4 - Pesquisar por artista");
      System.out.println("5 - Pesquisar por nome da música");
      System.out.println("6 - Pesquisar por letra da música");
      System.out.println("7 - Imprimir árvore");
      System.out.println("8 - Remover por chave");
      System.out.println("9 - Remover por nome da música");
      System.out.println("10 - Remover por artista");
      System.out.println("11 - Remover por letra");
      System.out.println("0 - Sair");
      System.out.print("Escolha uma opção: ");
      int opcao = Integer.parseInt(in.readLine());

      switch (opcao) {
        case 1: // Inserir manualmente
          System.out.print("Digite a chave (inteiro): ");
          int chave = Integer.parseInt(in.readLine());
          System.out.print("Digite o nome do artista: ");
          String artista = in.readLine();
          System.out.print("Digite o nome da música: ");
          String nomeMusica = in.readLine();
          System.out.print("Digite a letra da música: ");
          String letra = in.readLine();
          Musica novaMusica = new Musica(chave, artista, nomeMusica, letra);
          acervo.insere(novaMusica);
          System.out.println("Música inserida.");
          break;

        case 2: // Carregar de arquivo
          RandomAccessFile arq = null;
          System.out.print("Digite o caminho do arquivo: ");
          String caminhoArquivo = in.readLine();
          ArvoreB.carregarMusicasDoCSV(acervo, caminhoArquivo);
          System.out.println("Músicas carregadas do arquivo.");
          break;

        case 3: // Pesquisar por chave
          System.out.print("Digite a chave para pesquisar: ");
          chave = Integer.parseInt(in.readLine());
          Musica itemPorChave = new Musica(chave, "", "", "");
          Musica resultadoChave = (Musica) acervo.pesquisa(itemPorChave);
          if (resultadoChave == null) {
            System.out.println("Música não encontrada.");
          } else {
            System.out.println("Música encontrada: " + resultadoChave);
          }
          break;

        case 4: // Pesquisar por artista
          System.out.print("Digite o nome do artista para pesquisar: ");
          String buscaArtista = in.readLine();
          ArvoreB.mostrarResultado(acervo.buscarPorArtista(buscaArtista));
          break;

        case 5: // Pesquisar por nome da música
          System.out.print("Digite o nome da música para pesquisar: ");
          String buscaMusica = in.readLine();
          ArvoreB.mostrarResultado(acervo.buscarPorNomeMusica(buscaMusica));
          break;

        case 6: // Pesquisar por letra da música
          System.out.print("Digite a letra da música para pesquisar: ");
          String buscaLetra = in.readLine();
          ArvoreB.mostrarResultado(acervo.buscarPorLetra(buscaLetra));
        break;

        case 7: // Imprimir árvore
          acervo.imprime();
          break;

        case 8: // Remover por chave
          System.out.print("Digite a chave para remover: ");
          chave = Integer.parseInt(in.readLine());
          Musica itemParaRemover = new Musica(chave, "", "", "");
          acervo.retira(itemParaRemover);
          System.out.println("Música removida.");
          break;

        case 9: // Remover por nome da música
          System.out.print("Digite o nome da música para remover: ");
          String nomeMusicaRemover = in.readLine();
          acervo.removerPorNomeMusica(nomeMusicaRemover);
          System.out.println("Música removida.");
          break;

        case 10: // Remover por artista
          System.out.print("Digite o nome do artista para remover: ");
          String artistaRemover = in.readLine();
          acervo.removerPorArtista(artistaRemover);
          System.out.println("Música removida.");
          break;

        case 11: // Remover por letra
          System.out.print("Digite a letra da música para remover: ");
          String letraRemover = in.readLine();
          acervo.removerPorLetra(letraRemover);
          System.out.println("Música removida.");
          break;

        case 0: // Sair
          System.out.println("Saindo...");
          return;

        default:
          System.out.println("Opção inválida!");
      }
    }
  }
}
