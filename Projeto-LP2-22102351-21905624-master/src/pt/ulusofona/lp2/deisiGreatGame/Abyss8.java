package pt.ulusofona.lp2.deisiGreatGame;

class Abyss8 extends Abismo {

    private Programmer playerPreso;

    public Abyss8(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nCiclo Infinito\nNão atua se tiver a Tool: Programação Funcional";
        this.playerPreso = new Programmer();
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 1 : Programação Funcional
            // é aplicado o castigo ao player
            contadorAux++;
            if (!player.verificarTool(1)) {
                player.cairNoAbismo();
                playerPreso.sairDoAbismo();
                playerPreso = player;
            } else {
                // é removido o castigo ao player
                if (player.verificarTool(1)) {
                    player.removerTool(1);
                }
            }
        }
    }
}