package pt.ulusofona.lp2.deisiGreatGame;

public class Abyss0 extends Abismo {

    public Abyss0(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nErro Sintaxe\nNão atua se tiver as Tools: IDE e Ajuda do Professor";
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 4 : IDE e id = 5 : Ajuda do Professor
            // é aplicado o castigo ao player
            contadorAux++;
            if ((!player.verificarTool(4)) && (!player.verificarTool(5))) {
                player.cairNoAbismo();
                player.recuarPos(1);
                player.sairDoAbismo();
            } else {
                // é removido o castigo ao player
                if (player.verificarTool(4)) {
                    player.removerTool(4);
                } else if (player.verificarTool(5)) {
                    player.removerTool(5);
                }
            }
        }
    }

}