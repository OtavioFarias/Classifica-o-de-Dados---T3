### README - Organização de Músicas com Árvore B

---

#### **Descrição do Projeto**

Este projeto foi desenvolvido como parte do Trabalho Prático 3 (TP3) da disciplina **Classificação de Dados**. O objetivo foi criar um sistema para armazenar e gerenciar músicas de forma eficiente utilizando uma **Árvore B** como estrutura de dados. Inspirado no cenário de Julio, que desejava organizar suas músicas de maneira ágil e prática, o sistema implementa funcionalidades de busca, inserção e remoção, permitindo que ele encontre e organize sua trilha sonora favorita de forma rápida.

---

#### **Estrutura do Projeto**

O projeto está dividido em três partes principais:

1. **Classe `Musica`**
   - Representa o modelo de uma música, contendo os atributos:
     - `chave` (inteira): identificador único da música.
     - `artista` (String): nome do artista.
     - `nomeMusica` (String): título da música.
     - `letra` (String): letra da música.
   - Implementa métodos para:
     - Comparação de músicas por chave, artista, nome ou letra.
     - Serialização (leitura/gravação) em arquivos.
     - Representação de uma música em formato legível (método `toString`).

2. **Classe `ArvoreB`**
   - Implementa a estrutura de dados **Árvore B** para organizar as músicas.
   - Oferece métodos para:
     - Inserção, busca e remoção de músicas.
     - Impressão da estrutura da árvore.
     - Pesquisas específicas por chave, artista, nome ou letra.
     - Carregamento de músicas a partir de arquivos CSV.

3. **Classe `CriaArvoreB`**
   - Fornece uma interface de menu interativo para o usuário.
   - Funcionalidades disponíveis:
     - Inserir músicas manualmente.
     - Carregar músicas de um arquivo.
     - Pesquisar músicas por chave, artista, nome ou letra.
     - Remover músicas com base em diferentes critérios.
     - Visualizar a estrutura da árvore.

---

#### **Como Usar**

1. **Compilação**
   - Certifique-se de que possui o JDK instalado em sua máquina.
   - Compile as classes usando o comando:
     ```bash
     javac -d . *.java
     ```

2. **Execução**
   - Execute a classe principal (`CriaArvoreB`) com o comando:
     ```bash
     java arvore.CriaArvoreB
     ```

3. **Menu Interativo**
   - O programa exibirá um menu com as seguintes opções:
     - Inserir músicas manualmente.
     - Carregar músicas de um arquivo CSV.
     - Pesquisar ou remover músicas com base em diferentes critérios.
     - Visualizar a estrutura da Árvore B.

4. **Formato do Arquivo CSV**
   - Para carregar músicas de um arquivo, o arquivo deve estar no seguinte formato:
     ```
     chave,artista,nomeMusica,letra
     1,Beatles,Let it Be,When I find myself in times of trouble
     2,Queen,Bohemian Rhapsody,Is this the real life
     ```

---

#### **Requisitos do Sistema**

- **Linguagem:** Java
- **Bibliotecas/Recursos Usados:**
  - Classes de entrada e saída.
  - Estrutura de dados personalizada (Árvore B).

---

#### **Funcionalidades**

1. **Inserção**
   - Insere novas músicas, fornecendo os atributos necessários.
   - Suporta tanto inserção manual quanto por carregamento de arquivos.
     
2. **Inserção pro arquivo**
    - Na opção 2 do código é possível carregar o acervo de músicas seguindo o modelo apresentado anteriormente
    - Para carregar o mesmo basta digitar o nome do arquivo que deseja carregar
   
4. **Busca**
   - Pesquisas rápidas com base em:
     - Chave.
     - Nome do artista.
     - Nome da música.
     - Letra da música.

5. **Remoção**
   - Permite excluir músicas utilizando diferentes critérios:
     - Chave.
     - Nome do artista.
     - Nome da música.
     - Letra da música.

6. **Visualização**
   - Imprime a estrutura da Árvore B, mostrando como as músicas estão organizadas.

---

#### **Notas Importantes**

- A Árvore B foi escolhida por sua eficiência em operações de busca, inserção e remoção, garantindo desempenho consistente mesmo com um grande número de músicas.
- O código foi projetado para ser extensível, permitindo adicionar novos critérios de busca ou otimizações futuras.

---
