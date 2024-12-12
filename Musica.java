package arvoreB;

import java.io.*;

public class Musica {
    // Atributos privados que armazenam os dados de uma música
    private int chave;              // Identificador único da música
    private String artista;         // Nome do artista
    private String nomeMusica;      // Nome da música
    private String letra;           // Letra da música

    // Construtor para inicializar uma instância de Musica com os dados fornecidos
    public Musica(int chave, String artista, String nomeMusica, String letra) {
        this.chave = chave;                // Define a chave única
        this.artista = artista;            // Define o artista
        this.nomeMusica = nomeMusica;      // Define o nome da música
        this.letra = letra;                // Define a letra da música
    }

    // Método para comparar músicas com base na chave
    public int compara(Musica item) {
        return Integer.compare(this.chave, item.chave); // Compara chaves numericamente
    }

    // Método para comparar músicas com base no nome do artista
    public int comparaArtista(Musica item) {
        return this.artista.compareToIgnoreCase(item.artista); // Ignora maiúsculas/minúsculas
    }

    // Método para comparar músicas com base na letra
    public int comparaLetra(Musica item) {
        return this.letra.compareToIgnoreCase(item.letra); // Ignora maiúsculas/minúsculas
    }

    // Método para comparar músicas com base no nome da música
    public int comparaNomeMusica(Musica item) {
        return this.nomeMusica.compareToIgnoreCase(item.nomeMusica); // Ignora maiúsculas/minúsculas
    }

    // Método para alterar a chave da música
    public void alteraChave(int chave) {
        this.chave = chave; // Atualiza o valor da chave
    }

    // Método para recuperar a chave da música
    public int recuperaChave() {
        return this.chave; // Retorna o valor da chave
    }

    // Sobrescreve o método toString para representar a música como uma string
    @Override
    public String toString() {
        return "Chave: " + this.chave + ", Artista: " + this.artista +
               ", Música: " + this.nomeMusica + ", Letra: " + this.letra;
    }

    // Método para gravar os dados da música em um arquivo binário
    public void gravaArq(RandomAccessFile arq) throws IOException {
        arq.writeInt(this.chave);           // Grava a chave como inteiro
        arq.writeUTF(this.artista);         // Grava o nome do artista como string
        arq.writeUTF(this.nomeMusica);      // Grava o nome da música como string
        arq.writeUTF(this.letra);           // Grava a letra como string
    }

    // Método para ler os dados da música de um arquivo binário
    public void leArq(RandomAccessFile arq) throws IOException {
        this.chave = arq.readInt();         // Lê a chave como inteiro
        this.artista = arq.readUTF();       // Lê o nome do artista como string
        this.nomeMusica = arq.readUTF();    // Lê o nome da música como string
        this.letra = arq.readUTF();         // Lê a letra como string
    }

    // Método estático que retorna o tamanho estimado de um registro de música
    public static int tamanho() {
        // 4 bytes para a chave + 3 strings de 50 caracteres cada (UTF-16 usa 2 bytes por caractere)
        return 4 + (50 * 2) * 3;
    }

    // Método para recuperar a letra da música
    public String getLetra() {
        return this.letra; // Retorna a letra
    }

    // Método para recuperar o nome do artista
    public String getArtista() {
        return this.artista; // Retorna o artista
    }

    // Método para recuperar o nome da música
    public String getNomeMusica() {
        return this.nomeMusica; // Retorna o nome da música
    }
}
