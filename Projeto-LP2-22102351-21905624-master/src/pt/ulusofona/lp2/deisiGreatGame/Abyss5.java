package pt.ulusofona.lp2.deisiGreatGame;

class Abyss5 extends Abismo {

    public Abyss5(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        descricao = "Cuidado Abismo:\nDuplicated Code\nNão atua se tiver a Tool: Herança";
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 0 : Herança
            // é aplicado o castigo ao player
            contadorAux++;
            if (!player.verificarTool(0)) {
                player.cairNoAbismo();
                // recua até a ultima posição que estava
                player.alterarPos(player.getUltimaPosicaoPisada());
                player.sairDoAbismo();
            } else {
                // é removido o castigo
                if (player.verificarTool(0)) {
                    player.removerTool(0);
                }
            }
        }
    }
}