package arvore;

import arvoreB.ArvoreB;
import arvoreB.Musica;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class CriaArvoreB {

  public static void main(String[] args) throws Exception {
    // Criação de uma árvore B com ordem 2
    ArvoreB acervo = new ArvoreB(2);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // Leitor para entrada do usuário

    while (true) {
      // Exibição do menu de opções para o usuário
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

      // Leitura da opção escolhida pelo usuário
      int opcao = Integer.parseInt(in.readLine());

      // Avaliação da escolha do usuário usando switch-case
      switch (opcao) {
        case 1: // Inserir música manualmente
          System.out.print("Digite a chave (inteiro): ");
          int chave = Integer.parseInt(in.readLine());
          System.out.print("Digite o nome do artista: ");
          String artista = in.readLine();
          System.out.print("Digite o nome da música: ");
          String nomeMusica = in.readLine();
          System.out.print("Digite a letra da música: ");
          String letra = in.readLine();

          // Criação de um objeto Musica com os dados fornecidos
          Musica novaMusica = new Musica(chave, artista, nomeMusica, letra);

          // Inserção da música na árvore B
          acervo.insere(novaMusica);
          System.out.println("Música inserida.");
          break;

        case 2: // Carregar músicas de um arquivo CSV
          System.out.print("Digite o caminho do arquivo: ");
          String caminhoArquivo = in.readLine();

          // Método para carregar músicas do arquivo para a árvore B
          ArvoreB.carregarMusicasDoCSV(acervo, caminhoArquivo);
          System.out.println("Músicas carregadas do arquivo.");
          break;

        case 3: // Pesquisar música por chave
          System.out.print("Digite a chave para pesquisar: ");
          chave = Integer.parseInt(in.readLine());

          // Criação de um objeto Musica para a pesquisa
          Musica itemPorChave = new Musica(chave, "", "", "");

          // Busca na árvore e exibição do resultado
          Musica resultadoChave = (Musica) acervo.pesquisa(itemPorChave);
          if (resultadoChave == null) {
            System.out.println("Música não encontrada.");
          } else {
            System.out.println("Música encontrada: " + resultadoChave);
          }
          break;

        case 4: // Pesquisar música por artista
          System.out.print("Digite o nome do artista para pesquisar: ");
          String buscaArtista = in.readLine();

          // Exibição dos resultados encontrados
          ArvoreB.mostrarResultado(acervo.buscarPorArtista(buscaArtista));
          break;

        case 5: // Pesquisar música por nome
          System.out.print("Digite o nome da música para pesquisar: ");
          String buscaMusica = in.readLine();

          // Exibição dos resultados encontrados
          ArvoreB.mostrarResultado(acervo.buscarPorNomeMusica(buscaMusica));
          break;

        case 6: // Pesquisar música por letra
          System.out.print("Digite a letra da música para pesquisar: ");
          String buscaLetra = in.readLine();

          // Exibição dos resultados encontrados
          ArvoreB.mostrarResultado(acervo.buscarPorLetra(buscaLetra));
          break;

        case 7: // Imprimir a estrutura da árvore B
          acervo.imprime();
          break;

        case 8: // Remover música por chave
          System.out.print("Digite a chave para remover: ");
          chave = Integer.parseInt(in.readLine());

          // Remoção da música na árvore
          Musica itemParaRemover = new Musica(chave, "", "", "");
          acervo.retira(itemParaRemover);
          System.out.println("Música removida.");
          break;

        case 9: // Remover música por nome
          System.out.print("Digite o nome da música para remover: ");
          String nomeMusicaRemover = in.readLine();

          // Remoção baseada no nome da música
          acervo.removerPorNomeMusica(nomeMusicaRemover);
          System.out.println("Música removida.");
          break;

        case 10: // Remover música por artista
          System.out.print("Digite o nome do artista para remover: ");
          String artistaRemover = in.readLine();

          // Remoção baseada no nome do artista
          acervo.removerPorArtista(artistaRemover);
          System.out.println("Música removida.");
          break;

        case 11: // Remover música por letra
          System.out.print("Digite a letra da música para remover: ");
          String letraRemover = in.readLine();

          // Remoção baseada na letra da música
          acervo.removerPorLetra(letraRemover);
          System.out.println("Música removida.");
          break;

        case 0: // Encerrar o programa
          System.out.println("Saindo...");
          return;

        default: // Opção inválida
          System.out.println("Opção inválida!");
      }
    }
  }
}
