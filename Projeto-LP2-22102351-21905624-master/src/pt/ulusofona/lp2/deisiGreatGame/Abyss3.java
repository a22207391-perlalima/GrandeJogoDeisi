package pt.ulusofona.lp2.deisiGreatGame;

public class Abyss3 extends Abismo {

    public Abyss3(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nFile Not Found Exception\nNão atua se tiver as Tools: tratamento de excepções e Ajuda do Professor";
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 3 : tratamento de excepções id = 5 : Ajuda do Professor
            // é aplicado o castigo ao player
            contadorAux++;
            if ((!player.verificarTool(3)) && (!player.verificarTool(5))) {
                player.cairNoAbismo();
                player.recuarPos(3);
                player.sairDoAbismo();
            } else {
                // é removido o castigo ao player
                if (player.verificarTool(3)) {
                    player.removerTool(3);
                } else if (player.verificarTool(5)) {
                    player.removerTool(5);
                }
            }
        }
    }
}