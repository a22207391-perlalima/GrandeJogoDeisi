package pt.ulusofona.lp2.deisiGreatGame;

class Abyss7 extends Abismo {

    public Abyss7(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nBlue Screen of Death\nNão possui nenhuma Tool de ajuda";
    }

    @Override
    void castigoAbyss(Programmer player) {
        // neste abismo n é aplicado nenhum castigo
        if (!player.checkTemAbismoNaPosicao()) {
            contadorAux++;
            player.cairNoAbismo();
            player.retirarPlayer(); // o jogador perde imediatamente o jogo ("Derrotado")
            player.sairDoAbismo();
        }
    }
}