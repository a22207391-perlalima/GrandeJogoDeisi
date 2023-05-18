package pt.ulusofona.lp2.deisiGreatGame;

import java.util.List;

public abstract class Castigo {

    protected String titulo;
    protected int id;
    protected String imagem;
    protected String descricao;
    protected int posicao;
    protected boolean estaNumAbismo;
    protected int contadorAux;

    public Castigo(String titulo, int id, String imagem, boolean estaNumAbismo) {
        this.titulo = titulo;
        this.id = id;
        this.imagem = imagem;
        this.estaNumAbismo = estaNumAbismo;
        this.contadorAux = 0;
    }

    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public void obterPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getImagemTitulo() {
        return imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    abstract void castigoAbyss(Programmer player);

    void castigar(List<Programmer> players, int id) {
        for (Programmer p : players) {
            if (p.getId() == id) {
                castigoAbyss(p);
            }
        }
    }

    public boolean isEstaNumAbismo() {
        return estaNumAbismo;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}