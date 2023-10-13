//Gabriel Heyde Pintos
//Esta classe foi criada com o intuito de facilitar o controle do tabuleiro durante a partida do jogo de Batalha Naval, pois é possível através dos métodos de acceso(getters e setters) trazer informações como a sigla exibida nos tabuleiros dos jogadores e se aquela determinada posição já foi atingida anteriormente

public class Objeto {

    public final String getSigla = null;
    private String tipo;
    private String sigla;
    private boolean atingido;


    public Objeto(String tipo, String sigla) {
        this.tipo = tipo;
        this.sigla = sigla;
        atingido = false;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSigla() {
        return sigla;
    }

    public boolean isAtingido() {
        return atingido;
    }

    public void setAtingido(boolean atingido) {
        this.atingido = atingido;
    }

}