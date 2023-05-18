package pt.ulusofona.lp2.deisiGreatGame;

abstract class Ferramenta extends Castigo {

    public Ferramenta(String titulo, int id, String icone, boolean estaNumAbismo) {
        super(titulo, id, icone, estaNumAbismo);
    }

    @Override
    void castigoAbyss(Programmer player) {
        player.adicionaTool(this);
    }
}