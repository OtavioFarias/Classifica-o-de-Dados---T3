package arvoreB;

import java.io.*;

public class Musica { // Atributos privados que armazenam os dados de uma música
    private int chave;
    private String artista;
    private String nomeMusica;
    private String letra;

    // Construtor
    public Musica(int chave, String artista, String nomeMusica, String letra) {
        this.chave = chave;
        this.artista = artista;
        this.nomeMusica = nomeMusica;
        this.letra = letra;
    }

    // Método para comparar por chave
    public int compara(Musica item) {
        return Integer.compare(this.chave, item.chave);
    }

    // Método para comparar por artista
    public int comparaArtista(Musica item) {
        return this.artista.compareToIgnoreCase(item.artista); // Ignora maiúsculas/minúsculas
    }

    // Método para comparar por letra
    public int comparaLetra(Musica item) {
        return this.letra.compareToIgnoreCase(item.letra); // Ignora maiúsculas/minúsculas
    }

    // Método para comparar por nome da música
    public int comparaNomeMusica(Musica item) {
        return this.nomeMusica.compareToIgnoreCase(item.nomeMusica); // Ignora maiúsculas/minúsculas
    }

    // Método para alterar a chave
    public void alteraChave(int chave) {
        this.chave = chave;
    }

    // Método para recuperar a chave
    public int recuperaChave() {
        return this.chave;
    }

    // Representação em String
    @Override
    public String toString() {
        return "Chave: " + this.chave + ", Artista: " + this.artista +
               ", Música: " + this.nomeMusica + ", Letra: " + this.letra;
    }

    // Métodos de entrada e saída em arquivo
    public void gravaArq(RandomAccessFile arq) throws IOException {
        arq.writeInt(this.chave);
        arq.writeUTF(this.artista);
        arq.writeUTF(this.nomeMusica);
        arq.writeUTF(this.letra);
    }
    
    public void leArq(RandomAccessFile arq) throws IOException {
        this.artista = arq.readUTF();
        this.chave = arq.readInt();
        this.nomeMusica = arq.readUTF();
        this.letra = arq.readUTF();
    }

    // Tamanho do registro (estimado)
    public static int tamanho() {
        // 4 bytes para chave + estimativa de 3 strings de 50 caracteres cada (UTF-16 usa 2 bytes por char)
        return 4 + (50 * 2) * 3;
    }

    // Getters para os atributos
    public String getLetra() {
        return this.letra;
    }

    public String getArtista() {
        return this.artista;
    }

    public String getNomeMusica() {
        return this.nomeMusica;
    }
}
