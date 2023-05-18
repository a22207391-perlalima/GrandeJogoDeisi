package pt.ulusofona.lp2.deisiGreatGame;

class Abyss6 extends Abismo {

    public Abyss6(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nEfeitos secundários\nNão atua se tiver a Tool: Programação Funcional";
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 1 : Programação Funcional
            // é aplicado o castigo ao player
            contadorAux++;
            if (!player.verificarTool(1)) {
                player.cairNoAbismo();
                // recua até a 2ª ultima posição que estava
                player.alterarPos(player.get2UltimaPosicaoPisada());
                player.sairDoAbismo();
            } else {
                // é removido o castigo
                if (player.verificarTool(1)) {
                    player.removerTool(1);
                }
            }
        }
    }
}