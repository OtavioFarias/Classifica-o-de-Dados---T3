package arvore;

public interface Item {
  public int compara(Item it);

  public void alteraChave(Object chave);

  public Object recuperaChave();

  public String getLetra();

  public String getArtista();

  public String getNomeMusica();
}
