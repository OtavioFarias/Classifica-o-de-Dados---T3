package arvoreB;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class ArvoreB {

  // Classe interna que representa uma página da árvore B
  private static class Pagina {
    int n; // Número de registros na página
    Musica r[]; // Array de registros, onde cada elemento é um objeto Musica
    Pagina p[]; // Array de ponteiros para as páginas filhas

    // Construtor que inicializa a página com capacidade máxima 'mm'
    public Pagina(int mm) {
      this.n = 0; // Inicialmente, a página está vazia
      this.r = new Musica[mm]; // Array de registros com tamanho 'mm'
      this.p = new Pagina[mm + 1]; // Array de ponteiros para as páginas filhas, com tamanho 'mm + 1'
    }
  }

  private Pagina raiz; // Ponteiro para a raiz da árvore
  private int m, mm; // 'm' é o grau mínimo da árvore (número mínimo de filhos por página), 'mm' é a
                     // capacidade máxima de registros por página

  // Método recursivo que imprime a árvore em formato de níveis
  public void imprime(Pagina p, int nivel) {
    if (p != null) {
      // Imprime o nível e os registros da página
      System.out.print("  Nivel " + nivel + ":");
      for (int i = 0; i < p.n; i++)
        System.out.print(" " + p.r[i].toString()); // Chama toString de cada objeto Musica para exibir
      System.out.println();

      // Chama recursivamente para imprimir as páginas filhas
      for (int i = 0; i <= p.n; i++) {
        if (p.p[i] != null) {
          if (i < p.n)
            System.out.println("  Esq: " + p.r[i].toString()); // Imprime o lado esquerdo
          else
            System.out.println("  Dir: " + p.r[i - 1].toString()); // Imprime o lado direito
        }
        imprime(p.p[i], nivel + 1); // Chama para o próximo nível
      }
    }
  }

  // Método de pesquisa que busca um registro na árvore
  public Musica pesquisa(Musica reg, Pagina ap) {
    if (ap == null)
      return null; // Se a página é nula, o registro não foi encontrado
    else {
      int i = 0;
      // Procura o registro dentro da página
      while ((i < ap.n - 1) && (reg.compara(ap.r[i]) > 0))
        i++;

      // Se encontrou o registro
      if (reg.compara(ap.r[i]) == 0)
        return ap.r[i];
      // Se o registro é menor, busca na subárvore à esquerda
      else if (reg.compara(ap.r[i]) < 0)
        return pesquisa(reg, ap.p[i]);
      // Caso contrário, busca na subárvore à direita
      else
        return pesquisa(reg, ap.p[i + 1]);
    }
  }

  // Método para inserir um registro em uma página
  public void insereNaPagina(Pagina ap, Musica reg, Pagina apDir) {
    int k = ap.n - 1;
    // Desloca os registros para a direita para abrir espaço para o novo registro
    while ((k >= 0) && (reg.compara(ap.r[k]) < 0)) {
      ap.r[k + 1] = ap.r[k];
      ap.p[k + 2] = ap.p[k + 1];
      k--;
    }
    ap.r[k + 1] = reg; // Insere o registro na posição correta
    ap.p[k + 2] = apDir; // Insere o ponteiro para a página à direita
    ap.n++; // Aumenta o número de registros na página
  }

  // Método recursivo que insere um registro na árvore
  public Pagina insere(Musica reg, Pagina ap, Musica[] regRetorno, boolean[] cresceu) {
    Pagina apRetorno = null;
    if (ap == null) {
      cresceu[0] = true; // Se a página está nula, a árvore cresceu
      regRetorno[0] = reg; // O registro é o retorno da operação
    } else {
      int i = 0;
      // Encontra a posição correta para inserir o registro
      while ((i < ap.n - 1) && (reg.compara(ap.r[i]) > 0))
        i++;

      if (reg.compara(ap.r[i]) == 0) {
        System.out.println("Erro: Registro já existente"); // Não pode inserir registro duplicado
        cresceu[0] = false;
      } else {
        if (reg.compara(ap.r[i]) > 0)
          i++; // Se o registro é maior que o da posição, vai para a próxima posição

        // Chama recursivamente para inserir o registro na subárvore
        apRetorno = insere(reg, ap.p[i], regRetorno, cresceu);

        if (cresceu[0]) // Se a árvore cresceu (houve divisão de páginas)
          if (ap.n < this.mm) { // Se a página ainda tem espaço
            // Insere o registro e o ponteiro à direita na página
            this.insereNaPagina(ap, regRetorno[0], apRetorno);
            cresceu[0] = false; // Não houve mais crescimento
            apRetorno = ap; // A página atual é a retornada
          } else { // Se a página não tem mais espaço (overflow)
            // Divide a página em duas
            Pagina apTemp = new Pagina(this.mm); // Nova página temporária
            apTemp.p[0] = null; // Ponteiro nulo, pois é uma nova página

            if (i <= this.m) {
              // Insere o maior registro na nova página
              this.insereNaPagina(apTemp, ap.r[this.mm - 1], ap.p[this.mm]);
              ap.n--; // Decrementa o número de registros da página original
              this.insereNaPagina(ap, regRetorno[0], apRetorno);
            } else
              // Caso contrário, insere o registro diretamente na nova página
              this.insereNaPagina(apTemp, regRetorno[0], apRetorno);

            // Move os registros restantes para a nova página
            for (int j = this.m + 1; j < this.mm; j++) {
              this.insereNaPagina(apTemp, ap.r[j], ap.p[j + 1]);
              ap.p[j + 1] = null; // Libera a memória do ponteiro
            }
            ap.n = this.m; // Atualiza o número de registros da página original
            apTemp.p[0] = ap.p[this.m + 1]; // Atualiza o ponteiro da nova página
            regRetorno[0] = ap.r[this.m]; // O registro que será retornado após a divisão
            apRetorno = apTemp; // A nova página será retornada
          }
      }
    }
    return (cresceu[0] ? apRetorno : ap); // Retorna a página modificada
  }

  public boolean reconstitui(Pagina apPag, Pagina apPai, int posPai) {
    boolean diminuiu = true; // Variável para controlar se a árvore diminuiu de altura após a operação

    // Se o irmão à direita da página apPag tem registros suficientes
    if (posPai < apPai.n) {
      Pagina aux = apPai.p[posPai + 1]; // O irmão à direita
      int dispAux = (aux.n - this.m + 1) / 2; // Calcula a quantidade de registros disponíveis para transferência

      // Move o registro do pai para a página apPag
      apPag.r[apPag.n++] = apPai.r[posPai];
      apPag.p[apPag.n] = aux.p[0]; // Move o ponteiro do irmão para apPag
      aux.p[0] = null; // Libera o ponteiro da memória

      if (dispAux > 0) { // Se há folga no irmão à direita, transfere registros de aux para apPag
        for (int j = 0; j < dispAux - 1; j++) {
          this.insereNaPagina(apPag, aux.r[j], aux.p[j + 1]); // Insere registros na página apPag
          aux.p[j + 1] = null; // Libera o ponteiro da memória
        }
        // Atualiza o registro do pai com o último registro do irmão à direita
        apPai.r[posPai] = aux.r[dispAux - 1];
        aux.n = aux.n - dispAux; // Atualiza a quantidade de registros no irmão
        for (int j = 0; j < aux.n; j++) {
          aux.r[j] = aux.r[j + dispAux]; // Move os registros restantes no irmão
        }
        for (int j = 0; j <= aux.n; j++) {
          aux.p[j] = aux.p[j + dispAux]; // Move os ponteiros das páginas filhas
        }
        aux.p[aux.n + dispAux] = null; // Libera o último ponteiro
        diminuiu = false; // A árvore não diminuiu de altura
      } else { // Se não há folga, realiza a fusão das páginas
        for (int j = 0; j < this.m; j++) {
          this.insereNaPagina(apPag, aux.r[j], aux.p[j + 1]); // Move registros do irmão para apPag
          aux.p[j + 1] = null; // Libera o ponteiro
        }
        aux = apPai.p[posPai + 1] = null; // Libera a página do irmão
        for (int j = posPai; j < apPai.n - 1; j++) {
          // Move os registros do pai para "fechar" o espaço deixado pelo irmão
          apPai.r[j] = apPai.r[j + 1];
          apPai.p[j + 1] = apPai.p[j + 2];
        }
        apPai.p[apPai.n--] = null; // Libera o ponteiro da memória
        diminuiu = apPai.n < this.m; // Verifica se o pai tem registros suficientes
      }
    } else { // Caso em que o irmão está à esquerda da página apPag
      Pagina aux = apPai.p[posPai - 1]; // O irmão à esquerda
      int dispAux = (aux.n - this.m + 1) / 2; // Calcula a folga no irmão à esquerda

      // Desloca os registros da página apPag para a direita para abrir espaço para o
      // registro do pai
      for (int j = apPag.n - 1; j >= 0; j--)
        apPag.r[j + 1] = apPag.r[j];
      apPag.r[0] = apPai.r[posPai - 1]; // Insere o registro do pai na primeira posição de apPag
      for (int j = apPag.n; j >= 0; j--)
        apPag.p[j + 1] = apPag.p[j]; // Move os ponteiros das páginas filhas
      apPag.n++; // Aumenta o número de registros em apPag

      if (dispAux > 0) { // Se há folga no irmão à esquerda, transfere registros para apPag
        for (int j = 0; j < dispAux - 1; j++) {
          this.insereNaPagina(apPag, aux.r[aux.n - j - 1], aux.p[aux.n - j]); // Insere os registros na apPag
          aux.p[aux.n - j] = null; // Libera o ponteiro da memória
        }
        apPag.p[0] = aux.p[aux.n - dispAux + 1]; // Atualiza o ponteiro de apPag
        aux.p[aux.n - dispAux + 1] = null; // Libera o ponteiro
        apPai.r[posPai - 1] = aux.r[aux.n - dispAux]; // Atualiza o registro do pai
        aux.n = aux.n - dispAux; // Atualiza o número de registros do irmão
        diminuiu = false; // A árvore não diminuiu de altura
      } else { // Caso de fusão: intercala apPag no irmão à esquerda
        for (int j = 0; j < this.m; j++) {
          this.insereNaPagina(aux, apPag.r[j], apPag.p[j + 1]); // Move registros de apPag para o irmão
          apPag.p[j + 1] = null; // Libera o ponteiro
        }
        apPag = null; // Libera apPag da memória
        apPai.p[apPai.n--] = null; // Libera o ponteiro do pai
        diminuiu = apPai.n < this.m; // Verifica se o pai tem registros suficientes
      }
    }
    return diminuiu; // Retorna se a árvore diminuiu de altura após a operação
  }

  public boolean antecessor(Pagina ap, int ind, Pagina apPai) {
    boolean diminuiu = true; // Assume que a árvore diminuiu após a remoção

    // Se o ponteiro à direita de apPai não for nulo, busca no subárvore direita
    if (apPai.p[apPai.n] != null) {
      diminuiu = antecessor(ap, ind, apPai.p[apPai.n]); // Recursivamente busca na subárvore direita
      if (diminuiu)
        diminuiu = reconstitui(apPai.p[apPai.n], apPai, apPai.n); // Reconstroi se necessário
    } else {
      // Caso em que o antecessor está na página pai
      ap.r[ind] = apPai.r[--apPai.n]; // Move o antecessor da página pai
      diminuiu = apPai.n < this.m; // Verifica se a página pai diminuiu de altura
    }
    return diminuiu; // Retorna se a árvore diminuiu de altura após a operação
  }

  public Pagina retira(Musica reg, Pagina ap, boolean[] diminuiu) {
    if (ap == null) {
      System.out.println("Erro: Registro nao encontrado");
      diminuiu[0] = false;
    } else {
      int ind = 0;
      // Busca o índice do registro a ser removido
      while ((ind < ap.n - 1) && (reg.compara(ap.r[ind]) > 0))
        ind++;

      if (reg.compara(ap.r[ind]) == 0) { // Registro encontrado
        if (ap.p[ind] == null) { // Se a página é folha
          ap.n--; // Decrementa o número de registros
          diminuiu[0] = ap.n < this.m; // Verifica se a página precisa ser reconstituída
          for (int j = ind; j < ap.n; j++) {
            ap.r[j] = ap.r[j + 1]; // Desloca os registros para preencher o espaço
            ap.p[j] = ap.p[j + 1]; // Desloca os ponteiros das páginas filhas
          }
          ap.p[ap.n] = ap.p[ap.n + 1]; // Atualiza o ponteiro
          ap.p[ap.n + 1] = null; // Libera a memória
        } else { // Página não é folha: troca com antecessor
          diminuiu[0] = antecessor(ap, ind, ap.p[ind]); // Busca o antecessor
          if (diminuiu[0])
            diminuiu[0] = reconstitui(ap.p[ind], ap, ind); // Reconstituí a página pai se necessário
        }
      } else { // Registro não encontrado: continua a busca na subárvore
        if (reg.compara(ap.r[ind]) > 0)
          ind++;
        ap.p[ind] = retira(reg, ap.p[ind], diminuiu); // Chama recursivamente para buscar na subárvore
        if (diminuiu[0])
          diminuiu[0] = reconstitui(ap.p[ind], ap, ind); // Reconstituí se necessário
      }
    }
    return ap; // Retorna a página modificada
  }

  public ArvoreB(int m) {
    this.raiz = null; // Inicializa a raiz como nula (árvore vazia)
    this.m = m; // Define o valor de m (número mínimo de registros por página)
    this.mm = 2 * m; // Define o valor de mm (número máximo de registros por página)
  }

  // Método de pesquisa de uma música na árvore, começa pela raiz
  public Musica pesquisa(Musica reg) {
    return this.pesquisa(reg, this.raiz); // Chama o método recursivo de pesquisa, começando pela raiz
  }

  // Método para inserir uma música na árvore B
  public void insere(Musica reg) {
    Musica regRetorno[] = new Musica[1]; // Array para armazenar o registro retornado após a inserção
    boolean cresceu[] = new boolean[1]; // Flag para verificar se a árvore cresceu em altura
    Pagina apRetorno = this.insere(reg, this.raiz, regRetorno, cresceu); // Chama o método recursivo de inserção

    if (cresceu[0]) { // Se a árvore cresceu (ou seja, a raiz foi dividida)
      Pagina apTemp = new Pagina(this.mm); // Cria uma nova página para a raiz
      apTemp.r[0] = regRetorno[0]; // Coloca o registro retornado na nova raiz
      apTemp.p[0] = this.raiz; // O ponteiro para a antiga raiz
      apTemp.p[1] = apRetorno; // O novo ponteiro para a página dividida
      this.raiz = apTemp; // Atualiza a raiz da árvore
      this.raiz.n++; // Aumenta o número de registros na nova raiz
    } else {
      this.raiz = apRetorno; // Caso contrário, apenas atualiza a raiz
    }
  }

  // Método para remover uma música da árvore
  public void retira(Musica reg) {
    boolean diminuiu[] = new boolean[1]; // Flag para verificar se a árvore diminuiu em altura
    this.raiz = this.retira(reg, this.raiz, diminuiu); // Chama o método recursivo de remoção

    // Se a árvore diminuiu em altura e a raiz está vazia, atualiza a raiz para o
    // filho da raiz
    if (diminuiu[0] && (this.raiz.n == 0)) {
      this.raiz = this.raiz.p[0];
    }
  }

  // Método para imprimir a árvore B
  public void imprime() {
    System.out.println("ARVORE:");
    this.imprime(this.raiz, 0); // Chama o método recursivo de impressão, começando pela raiz
  }

  // Busca recursiva por músicas que possuem a letra fornecida
  public Musica[] buscarPorLetra(String letra, Pagina ap, List<Musica> resultado) {
    if (ap == null) {
      return resultado.toArray(new Musica[0]); // Se a página for nula, retorna o resultado
    }

    // Verifica cada registro da página se a letra está contida no campo "letra"
    for (int i = 0; i < ap.n; i++) {
      if (ap.r[i].getLetra().contains(letra)) {
        resultado.add(ap.r[i]); // Adiciona a música ao resultado
      }
    }

    // Chama recursivamente os filhos da página
    for (int i = 0; i <= ap.n; i++) {
      buscarPorLetra(letra, ap.p[i], resultado); // Pesquisa nos filhos da página
    }

    return resultado.toArray(new Musica[0]); // Retorna a lista de músicas encontradas
  }

  // Busca recursiva por músicas de um artista específico
  public Musica[] buscarPorArtista(String artista, Pagina ap, List<Musica> resultado) {
    if (ap == null) {
      return resultado.toArray(new Musica[0]); // Se a página for nula, retorna o resultado
    }

    // Verifica cada registro da página se o artista corresponde ao fornecido
    for (int i = 0; i < ap.n; i++) {
      if (ap.r[i].getArtista().equals(artista)) {
        resultado.add(ap.r[i]); // Adiciona a música ao resultado
      }
    }

    // Chama recursivamente os filhos da página
    for (int i = 0; i <= ap.n; i++) {
      buscarPorArtista(artista, ap.p[i], resultado); // Pesquisa nos filhos da página
    }

    return resultado.toArray(new Musica[0]); // Retorna a lista de músicas encontradas
  }

  // Busca recursiva por músicas com um nome específico
  public Musica[] buscarPorNomeMusica(String nomeMusica, Pagina ap, List<Musica> resultado) {
    if (ap == null) {
      return resultado.toArray(new Musica[0]); // Se a página for nula, retorna o resultado
    }

    // Verifica cada registro da página se o nome da música corresponde ao fornecido
    for (int i = 0; i < ap.n; i++) {
      if (ap.r[i].getNomeMusica().equals(nomeMusica)) {
        resultado.add(ap.r[i]); // Adiciona a música ao resultado
      }
    }

    // Chama recursivamente os filhos da página
    for (int i = 0; i <= ap.n; i++) {
      buscarPorNomeMusica(nomeMusica, ap.p[i], resultado); // Pesquisa nos filhos da página
    }

    return resultado.toArray(new Musica[0]); // Retorna a lista de músicas encontradas
  }

  // Métodos de busca por letra, artista e nome da música, começando pela raiz
  public Musica[] buscarPorLetra(String letra) {
    return buscarPorLetra(letra, this.raiz, new ArrayList<>());
  }

  public Musica[] buscarPorArtista(String artista) {
    return buscarPorArtista(artista, this.raiz, new ArrayList<>());
  }

  public Musica[] buscarPorNomeMusica(String nomeMusica) {
    return buscarPorNomeMusica(nomeMusica, this.raiz, new ArrayList<>());
  }

  // Método para remover músicas por artista de uma página (recursivo)
  public Pagina removerPorArtista(String artista, Pagina ap, boolean[] diminuiu) {
    if (ap == null) {
      diminuiu[0] = false;
      return null; // Se a página for nula, retorna nulo e não diminui a árvore
    }

    // Percorre os registros da página para encontrar músicas do artista
    for (int i = 0; i < ap.n; i++) {
      if (ap.r[i].getArtista().equals(artista)) {
        // Música encontrada, removendo-a
        System.out.println("Removendo música de " + artista + ": " + ap.r[i]);
        ap = retira(ap.r[i], ap, diminuiu); // Remove a música da página
        break;
      }
    }

    // Chama recursivamente os filhos da página para remover músicas do artista
    for (int i = 0; i <= ap.n; i++) {
      ap.p[i] = removerPorArtista(artista, ap.p[i], diminuiu); // Recursivamente remove músicas nos filhos
    }
    return ap; // Retorna a página (possivelmente modificada)
  }

  // Método para remover músicas por nome de música de uma página (recursivo)
  public Pagina removerPorNomeMusica(String nomeMusica, Pagina ap, boolean[] diminuiu) {
    if (ap == null) {
      diminuiu[0] = false;
      return null; // Se a página for nula, retorna nulo e não diminui a árvore
    }

    // Percorre os registros da página para encontrar a música pelo nome
    for (int i = 0; i < ap.n; i++) {
      if (ap.r[i].getNomeMusica().equals(nomeMusica)) {
        // Música encontrada, removendo-a
        System.out.println("Removendo música: " + ap.r[i]);
        ap = retira(ap.r[i], ap, diminuiu); // Remove a música da página
        break;
      }
    }

    // Chama recursivamente os filhos da página para remover músicas pelo nome
    for (int i = 0; i <= ap.n; i++) {
      ap.p[i] = removerPorNomeMusica(nomeMusica, ap.p[i], diminuiu); // Recursivamente remove músicas nos filhos
    }
    return ap; // Retorna a página (possivelmente modificada)
  }

  // Método que remove músicas por letra
  public void removerPorLetra(String letra) {
    boolean diminuiu[] = new boolean[1]; // Array que indica se a árvore diminuiu
    this.raiz = removerPorLetra(letra, this.raiz, diminuiu); // Chama o método recursivo para a raiz
    if (diminuiu[0] && this.raiz.n == 0) { // Verifica se a raiz ficou vazia após a remoção
      this.raiz = this.raiz.p[0]; // Se a árvore diminuiu de altura, ajusta a raiz
    }
  }

  // Método que remove músicas por artista
  public void removerPorArtista(String artista) {
    boolean diminuiu[] = new boolean[1]; // Array que indica se a árvore diminuiu
    this.raiz = removerPorArtista(artista, this.raiz, diminuiu); // Chama o método recursivo para a raiz
    if (diminuiu[0] && this.raiz.n == 0) { // Verifica se a raiz ficou vazia após a remoção
      this.raiz = this.raiz.p[0]; // Se a árvore diminuiu de altura, ajusta a raiz
    }
  }

  // Método que remove músicas por nome da música
  public void removerPorNomeMusica(String nomeMusica) {
    boolean diminuiu[] = new boolean[1]; // Array que indica se a árvore diminuiu
    this.raiz = removerPorNomeMusica(nomeMusica, this.raiz, diminuiu); // Chama o método recursivo para a raiz
    if (diminuiu[0] && this.raiz.n == 0) { // Verifica se a raiz ficou vazia após a remoção
      this.raiz = this.raiz.p[0]; // Se a árvore diminuiu de altura, ajusta a raiz
    }
  }

  // Método recursivo que remove músicas por letra
  public Pagina removerPorLetra(String letra, Pagina ap, boolean[] diminuiu) {
    // Verifica se a página é nula (caso base para a recursão)
    if (ap == null) {
      diminuiu[0] = false; // Se a página é nula, a árvore não diminuiu
      return null;
    }

    // Percorrer as músicas na página
    for (int i = 0; i < ap.n; i++) {
      // Verifica se a letra da música na posição i é igual à letra fornecida
      if (ap.r[i].getLetra().equals(letra)) {
        // Música encontrada, removendo
        System.out.println("Removendo música com letra: " + ap.r[i]); // Imprime a música sendo removida
        ap = retira(ap.r[i], ap, diminuiu); // Chama o método 'retira' para remover a música da página
        break; // Interrompe o loop, pois encontramos e removemos a música
      }
    }

    // Recursivamente percorre os filhos da página
    for (int i = 0; i <= ap.n; i++) {
      ap.p[i] = removerPorLetra(letra, ap.p[i], diminuiu); // Chama o método recursivamente para os filhos
    }

    return ap; // Retorna a página, que pode ter sido alterada após a remoção
  }

  // Função que carrega músicas de um arquivo CSV e insere na árvore B
  public static void carregarMusicasDoCSV(ArvoreB acervo, String caminhoArquivoCSV) throws IOException {
    BufferedReader reader = null; // Declaração do leitor para ler o arquivo CSV
    String linha; // Variável para armazenar cada linha lida do arquivo CSV

    try {
      // Abrir o arquivo CSV para leitura
      reader = new BufferedReader(new FileReader(caminhoArquivoCSV));

      // Ignorar o cabeçalho do arquivo CSV, caso ele exista
      reader.readLine(); // Comente esta linha se o arquivo não tiver cabeçalho

      // Ler o arquivo linha por linha
      while ((linha = reader.readLine()) != null) {
        // Dividir a linha usando a vírgula como delimitador
        String[] dados = linha.split(",");

        // Verificar se a linha está bem formatada
        if (dados.length == 4) { // Verifica se a linha tem 4 campos, que são: artista, chave, nome da música, e
                                 // letra
          // Obter os dados e fazer o tratamento necessário (remover espaços extras, por
          // exemplo)
          String artista = dados[0].trim();
          int chave = Integer.parseInt(dados[1].trim()); // Converte a chave para inteiro
          String nomeMusica = dados[2].trim();
          String letra = dados[3].trim();

          // Criar uma instância de Musica com os dados obtidos do CSV
          Musica musica = new Musica(chave, artista, nomeMusica, letra);

          // Inserir a música na árvore B (acervo)
          acervo.insere(musica);
        }
      }

      // Exibe mensagem indicando que o carregamento das músicas foi bem-sucedido
      System.out.println("Músicas carregadas do arquivo com sucesso!");

    } catch (IOException e) {
      // Em caso de erro ao ler o arquivo CSV, exibe a mensagem de erro
      System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
    } finally {
      // Fecha o BufferedReader para liberar recursos, garantindo que ele seja fechado
      // mesmo em caso de erro
      if (reader != null) {
        reader.close();
      }
    }
  }

  // Função que mostra os resultados da busca de músicas
  public static void mostrarResultado(Musica[] resultado) {
    // Verifica se o resultado da busca é nulo ou está vazio
    if (resultado == null || resultado.length == 0) {
      System.out.println("Nenhuma música encontrada."); // Exibe mensagem caso não tenha resultado
      return;
    }

    // Exibe todas as músicas encontradas, uma por uma
    for (Musica musica : resultado) {
      System.out.println(musica); // Exibe a música (presumindo que a classe Musica sobrescreva o método toString)
    }
  }
}