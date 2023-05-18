package pt.ulusofona.lp2.deisiGreatGame;

public class Abyss1 extends Abismo {

    public Abyss1(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
        this.descricao = "Cuidado Abismo:\nErro de lógica\nNão atua se tiver as Tools: testes unitarios e Ajuda do Professor";
    }

    @Override
    void castigoAbyss(Programmer player) {
        if (!player.checkTemAbismoNaPosicao()) {
            // tool id = 2 : testes unitarios e id = 5 : Ajuda do Professor
            // é aplicado o castigo ao player
            contadorAux++;
            if ((!player.verificarTool(2)) && (!player.verificarTool(5))) {
                player.cairNoAbismo();
                int posAtual = player.getPosicaoAtual();
                player.alterarPos(posAtual - (GameManager.getNrDado() / 2));
                player.sairDoAbismo();
            } else {
                // é removido o castigo ao player
                if (player.verificarTool(2)) {
                    player.removerTool(2);
                } else if (player.verificarTool(5)) {
                    player.removerTool(5);
                }
            }
        }
    }
}