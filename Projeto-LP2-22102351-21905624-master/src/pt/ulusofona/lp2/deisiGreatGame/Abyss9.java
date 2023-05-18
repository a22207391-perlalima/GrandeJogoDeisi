package pt.ulusofona.lp2.deisiGreatGame;

import java.util.List;

class Abyss9 extends Abismo {

    public Abyss9(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nSegmentation Fault\nNão possui nenhuma Tool de ajuda";
    }

    @Override
    public void castigoAbyss(Programmer player) {
        // neste abismo n é aplicado nenhuma ferramenta
        if (!player.checkTemAbismoNaPosicao()) {
            contadorAux++;
            player.cairNoAbismo();
            player.recuarPos(3);
            player.sairDoAbismo();
        }
    }

    @Override
    void castigar(List<Programmer> players, int id) {
        if (players.size() > 1) {
            for (Programmer p : players) {
                castigoAbyss(p);
            }
        }
    }
}