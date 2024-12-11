package arvore;

import java.io.*;

public class Musica implements Item {
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
  public int compara(Item it) {
    Musica item = (Musica) it;
    return Integer.compare(this.chave, item.chave);
  }

  // Método para comparar por artista
  public int comparaArtista(Item it) {
    Musica item = (Musica) it;
    return this.artista.compareToIgnoreCase(item.artista); // Ignora maiúsculas/minúsculas
  }

  // Método para comparar por letra
  public int comparaLetra(Item it) {
    Musica item = (Musica) it;
    return this.letra.compareToIgnoreCase(item.letra); // Ignora maiúsculas/minúsculas
  }

  // Método para comparar por nome da música
  public int comparaNomeMusica(Item it) {
    Musica item = (Musica) it;
    return this.nomeMusica.compareToIgnoreCase(item.nomeMusica); // Ignora maiúsculas/minúsculas
  }

  // Método para alterar a chave
  public void alteraChave(Object chave) {
    Integer ch = (Integer) chave;
    this.chave = ch.intValue();
  }

  // Método para recuperar a chave
  public Object recuperaChave() {
    return this.chave; // Integer não é mais necessário
  }

  // Representação em String (opcionalmente, pode incluir outros atributos)
  public String toString() {
    return "Chave: " + this.chave + ", Artista: " + this.artista +
        ", Música: " + this.nomeMusica;
  }

  // Métodos de entrada e saída em arquivo
  public void gravaArq(RandomAccessFile arq) throws IOException {
    arq.writeInt(this.chave);
  }

  public void leArq(RandomAccessFile arq) throws IOException {
    this.chave = arq.readInt();
  }

  // Tamanho do registro
  public static int tamanho() {
    return 4; // Supondo que seja o tamanho de "chave" em bytes
  }

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
