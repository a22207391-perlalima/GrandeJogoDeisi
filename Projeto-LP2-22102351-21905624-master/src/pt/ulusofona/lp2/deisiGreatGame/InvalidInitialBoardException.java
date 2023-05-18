package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception {
    String menssagem;
    Integer tipo;
    String typeId;

    InvalidInitialBoardException(String menssagem) {

        this.menssagem = menssagem;
        this.tipo = null;
        this.typeId = null;
    }

    InvalidInitialBoardException(String menssagem, int tipo, String typeId) {

        this.menssagem = menssagem;
        this.tipo = tipo;
        this.typeId = typeId;
    }


    public String getMessage() {
        return menssagem;
    }

    public boolean isInvalidAbyss() {

        return tipo != null && tipo == 0;
    }

    public boolean isInvalidTool() {

        return tipo != null && tipo == 1;
    }

    public String getTypeId() {

        if (isInvalidAbyss() || isInvalidTool()) {
            return typeId;
        } else {
            return null;
        }
    }
}