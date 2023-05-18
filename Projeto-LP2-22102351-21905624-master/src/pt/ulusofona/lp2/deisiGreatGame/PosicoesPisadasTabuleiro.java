package pt.ulusofona.lp2.deisiGreatGame;

public class PosicoesPisadasTabuleiro {

    private int posicao;
    private int contador;

    public PosicoesPisadasTabuleiro(int posicao) {
        this.posicao = posicao;
        this.contador = 0;
    }

    public void acrescentaPosPisadas() {
        contador++;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getPosPisadas() {
        return contador;
    }
}