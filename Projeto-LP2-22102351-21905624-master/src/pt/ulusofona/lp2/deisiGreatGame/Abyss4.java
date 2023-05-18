package pt.ulusofona.lp2.deisiGreatGame;

public class Abyss4 extends Abismo {

    public Abyss4(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nCrash (aka Rebentanço)\nNão possui nenhuma Tool de ajuda";
    }

    @Override
    void castigoAbyss(Programmer player) {
        // neste abismo n é aplicado nenhuma ferramenta
        if (!player.checkTemAbismoNaPosicao()) {
            contadorAux++;
            player.cairNoAbismo();
            player.alterarPos(1); // volta a casa inicial
            player.sairDoAbismo();
        }
    }
}