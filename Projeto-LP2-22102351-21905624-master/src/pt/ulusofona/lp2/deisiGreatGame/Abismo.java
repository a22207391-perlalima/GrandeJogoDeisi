package pt.ulusofona.lp2.deisiGreatGame;

abstract class Abismo extends Castigo {

    public Abismo(String titulo, int id, String imagem, boolean estaNumAbismo) {
        super(titulo, id, imagem, estaNumAbismo);
    }

    @Override
    void castigoAbyss(Programmer player) {
    }

}