package pt.ulusofona.lp2.deisiGreatGame;

import java.util.*;

public class Programmer {

    private int id;
    private String nome;
    private int posicao;
    private ProgrammerColor cor;
    private String favLanguage;
    private boolean estado;

    // New
    private HashMap<Integer, Castigo> tools;
    private ArrayList<Integer> ultimaPos;
    private boolean temAbismo;

    public Programmer() {
        this.favLanguage = "";
        newProgrammer();
    }

    public Programmer(int id, String nome, ProgrammerColor cor, String favLanguage) {
        this.id = id;
        this.nome = nome;
        this.favLanguage = favLanguage;
        this.cor = cor;
        newProgrammer();
    }

    private void newProgrammer() {
        this.posicao = 1;
        this.estado = true;
        ordenarLinguagensPreferidas();
        this.tools = new HashMap<>();
        this.ultimaPos = new ArrayList<>();
        this.temAbismo = false;
    }

    public int getId() {
        return id;
    }

    public void obterId(int id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public ProgrammerColor getColor() {
        return cor;
    }

    public void setCor(ProgrammerColor color) {
        this.cor = color;
    }

    public int getPosicaoAtual() {
        return posicao;
    }

    public String getEstadoEmJogoOuDerrotado() {

        if (estado) {
            return "Em Jogo";
        }
        return "Derrotado";
    }

    public boolean getEstado() {
        return estado;
    }

    public String getFavLanguage() {
        return favLanguage;
    }

    public void setFavLanguage(String favouriteLanguages) {
        this.favLanguage = favouriteLanguages;
    }

    private void ordenarLinguagensPreferidas() {

        StringBuilder textoArranjadinho = new StringBuilder();

        ArrayList<String> aux = new ArrayList<>(Arrays.asList(this.favLanguage.split(";")));
        Collections.sort(aux);

        for (String s : aux) {
            textoArranjadinho.append(s).append(";").append(" ");
        }

        textoArranjadinho.delete(textoArranjadinho.length() - 2, textoArranjadinho.length());
        this.favLanguage = textoArranjadinho.toString();
    }

    public int tamanhoLinguagensFav() {

        ArrayList<String> list = new ArrayList<>(List.of(favLanguage.split(";")));
        return list.size();
    }

    public void alterarPos(int posicao) {
        this.posicao = posicao;
    }

    public void somaPos(int pos) {
        this.posicao += pos;
    }

    public void recuarPos(int pos) {
        this.posicao = posicao - pos;
    }

    public void lastPos(int posicao) {
        ultimaPos.add(posicao);
    }

    public String classificarTools(boolean temAbismo) {

        if (tools == null || tools.isEmpty()) {
            return "No tools";
        }

        StringBuilder playerTools = new StringBuilder();

        ArrayList<Castigo> allTools = new ArrayList<>(this.tools.values());

        for (Castigo castigo : allTools) {
            String ferramentaAux;

            if (temAbismo) {
                ferramentaAux = castigo.getPosicao() + "";
            } else {
                ferramentaAux = castigo.toString();
            }

            playerTools.append(ferramentaAux).append(";");
        }
        playerTools.delete(playerTools.length() - 1, playerTools.length());

        return playerTools.toString();
    }

    public String toString2() {
        return id + " | " + nome + " | " + posicao + " | " + classificarTools(true) + " | " + favLanguage + " | " + getEstadoEmJogoOuDerrotado();
    }

    public boolean verificarTool(int idFerramenta) {
        return tools.containsKey(idFerramenta);
    }

    public void adicionaTool(Castigo ferramenta) {
        tools.put(ferramenta.getId(), ferramenta);
    }

    public void removerTool(int idFerramenta) {
        tools.remove(idFerramenta);
    }

    public void retirarPlayer() {
        estado = false;
    }

    public boolean checkTemAbismoNaPosicao() {
        return temAbismo;
    }

    public void cairNoAbismo() {
        temAbismo = true;
    }

    public void sairDoAbismo() {
        temAbismo = false;
    }

    public int getUltimaPosicaoPisada() {
        return ultimaPos.get(ultimaPos.size() - 1);
    }

    public int get2UltimaPosicaoPisada() {
        return ultimaPos.get(ultimaPos.size() - 2);
    }


    public List<String> linguagensPreferidasArrayList() {
        return new ArrayList<>(List.of(favLanguage.split("; ")));
    }


    public String toString() {
        return id + " | " + nome + " | " + posicao + " | " + classificarTools(false) + " | " + favLanguage + " | " + getEstadoEmJogoOuDerrotado();
    }
}