package pt.ulusofona.lp2.deisiGreatGame;

import java.util.*;
import javax.swing.*;
import java.io.*;

public class GameManager {

    private int nrTabuleiro;
    private int nrTurnos;
    private static int nrDado;

    private final TreeMap<Integer, Programmer> allPlayersInfo;
    private final HashMap<Integer, Castigo> abyssesEtools;
    private final HashMap<Integer, PosicoesPisadasTabuleiro> posPisadas;

    private Programmer player;
    private Programmer winner;

    public GameManager() {
        abyssesEtools = new HashMap<>();
        allPlayersInfo = new TreeMap<>();
        posPisadas = new HashMap<>();
        nrTabuleiro = 0;
        nrTurnos = 1;
    }

    private void clearGameManager() {
        abyssesEtools.clear();
        allPlayersInfo.clear();
        posPisadas.clear();
        nrTabuleiro = 0;
        nrTurnos = 1;
        winner = null;
    }

    public static int getNrDado() {
        return nrDado;
    }

    // 1
    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) throws InvalidInitialBoardException {

        createInitialBoard(playerInfo, worldSize);

        if (abyssesAndTools != null) {
            for (String[] textoLido : abyssesAndTools) {

                try {
                    int tipo = Integer.parseInt(textoLido[0]);
                    int id = Integer.parseInt(textoLido[1]);
                    int posicao = Integer.parseInt(textoLido[2]);
                    if (posicao <= 0 || posicao >= worldSize) {
                        throw new InvalidInitialBoardException("A posição do objeto abismo e da ferramenta devem " +
                                "respeitar os limites do tabuleiro");
                    }

                    Castigo castigoAbismoOuFerramenta = switch (tipo) {
                        case 0 -> switch (id) {
                            case 0 -> new Abyss0("Erro de sintaxe", 0, "syntax.png", true);
                            case 1 -> new Abyss1("Erro de lógica", 1, "logic.png", true);
                            case 2 -> new Abyss2("Exception", 2, "exception.png", true);
                            case 3 -> new Abyss3("File Not Found Exception", 3, "file-not-found-exception.png", true);
                            case 4 -> new Abyss4("Crash (aka Rebentanço)", 4, "crash.png", true);
                            case 5 -> new Abyss5("Duplicated Code", 5, "duplicated-code.png", true);
                            case 6 -> new Abyss6("Efeitos secundários", 6, "secondary-effects.png", true);
                            case 7 -> new Abyss7("Blue Screen of Death", 7, "bsod.png", true);
                            case 8 -> new Abyss8("Ciclo infinito", 8, "infinite-loop.png", true);
                            case 9 -> new Abyss9("Segmentation Fault", 9, "core-dumped.png", true);
                            default -> throw new InvalidInitialBoardException("O id de um abismo deve estar entre os parâmetros 0 e 9");
                        };
                        case 1 -> switch (id) {
                            case 0 -> new Tool0("Herança", 0, "inheritance.png", false);
                            case 1 -> new Tool1("Programação Funcional", 1, "functional.png", false);
                            case 2 -> new Tool2("Testes unitários", 2, "unit-tests.png", false);
                            case 3 -> new Tool3("Tratamento de Excepções", 3, "catch.png", false);
                            case 4 -> new Tool4("IDE", 4, "IDE.png", false);
                            case 5 -> new Tool5("Ajuda Do Professor", 5, "ajuda-professor.png", false);
                            default -> throw new InvalidInitialBoardException("O id de uma ferramenta deve estar " +
                                    "entre os parâmetros 0 e 5");
                        };
                        default -> throw new InvalidInitialBoardException("O tipo não é permitido");
                    };
                    castigoAbismoOuFerramenta.obterPosicao(posicao);
                    this.abyssesEtools.put(posicao, castigoAbismoOuFerramenta);

                } catch (NumberFormatException exception) {
                    throw new InvalidInitialBoardException("Os parâmetros inválidos");
                }
            }
        }
    }

    // 1.1
    public void createInitialBoard(String[][] playerInfo, int worldSize) throws InvalidInitialBoardException {

        clearGameManager();

        if (playerInfo.length >= 2 && playerInfo.length <= 4) {
            if (worldSize < playerInfo.length * 2) {
                throw new InvalidInitialBoardException("Tamanho inválido");
            }

            for (String[] textoLido : playerInfo) {

                if (textoLido[1] == null || textoLido[1].isEmpty()) {
                    throw new InvalidInitialBoardException("Nome do jogador inválido");
                }

                if (!textoLido[3].equals("Purple") && !textoLido[3].equals("Blue") && !textoLido[3].equals("Green") && !textoLido[3].equals("Brown")) {
                    throw new InvalidInitialBoardException("Cor do jogador inválida");
                }

                allPlayersInfo.put(Integer.parseInt(textoLido[0]), new Programmer(
                        Integer.parseInt(textoLido[0]),
                        textoLido[1].trim(),
                        ProgrammerColor.valueOf(textoLido[3].toUpperCase()),
                        textoLido[2]
                ));
            }
            this.nrTabuleiro = worldSize;
            player = allPlayersInfo.get(allPlayersInfo.firstKey());

            for (int i = 1; i <= worldSize; i++) {
                posPisadas.put(i, new PosicoesPisadasTabuleiro(i));
            }

        } else {
            throw new InvalidInitialBoardException("nº de jogadores inválido.\\nDevem existir entre 2 a 4 jogadores para iniciar");
        }
    }

    // 2
    public String getImagePng(int position) {

        if (position > nrTabuleiro || position < 0) {
            return null;
        }

        if (position == nrTabuleiro) {
            return "glory.png";
        }

        List<Programmer> programadores = getProgrammers(position);

        if (programadores != null && !programadores.isEmpty()) {
            return "player" + programadores.get(0).getColor().toString() + ".png";
        }

        if (abyssesEtools.containsKey(position)) {
            return abyssesEtools.get(position).getImagemTitulo();
        }

        return null;
    }

    // 2.2
    public String getTitle(int position) {

        if (position < 0 || position > nrTabuleiro) {
            return null;
        }

        Castigo abismoOuFerramenta = abyssesEtools.get(position);

        return abismoOuFerramenta != null ? abismoOuFerramenta.toString() : null;
    }

    // 3
    public List<Programmer> getProgrammers(boolean includeDefeated) {

        List<Programmer> allplayers = new ArrayList<>();

        //percorre toda info e permite aceder aos seus valores
        // o entrySet() permite criar um novo conjunto o usar um conj existente e armazenar os elementos do mapa nele.
        for (Map.Entry<Integer, Programmer> jogadoresEmQuestao : this.allPlayersInfo.entrySet()) {
            if (!includeDefeated) {
                if (jogadoresEmQuestao.getValue().getEstado()) {
                    allplayers.add(jogadoresEmQuestao.getValue());
                }
            } else {
                allplayers.add(jogadoresEmQuestao.getValue());
            }
        }

        return allplayers;
    }

    // 4
    public List<Programmer> getProgrammers(int position) {

        ArrayList<Programmer> programadores = new ArrayList<>();

        for (Programmer player : this.allPlayersInfo.values()) {
            if (player.getPosicaoAtual() == position) {
                programadores.add(player);
            }
        }

        return programadores;
    }

    // 4.1
    public String getProgrammersInfo() {

        StringBuilder construcaoDeTexto = new StringBuilder();

        for (Programmer programador : getProgrammers(false)) {
            construcaoDeTexto.append(programador.getName()).append(" : ").append(programador.classificarTools(false)).append(" | ");
        }

        construcaoDeTexto.delete(construcaoDeTexto.length() - 3, construcaoDeTexto.length());

        return construcaoDeTexto.toString();
    }

    // 5
    public int getCurrentPlayerID() {
        return player.getId();
    }

    public Programmer getGetCurrentPlayer() {
        return player;
    }

    // 6
    public boolean moveCurrentPlayer(int nrSpaces) {

        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        nrDado = nrSpaces;

        if (player.getEstado() && !player.checkTemAbismoNaPosicao()) {
            if (player.getPosicaoAtual() + nrSpaces > 1) {
                player.lastPos(player.getPosicaoAtual());
                if (player.getPosicaoAtual() + nrSpaces > nrTabuleiro) {
                    int backNrSpaces = (player.getPosicaoAtual() + nrSpaces) - nrTabuleiro;
                    player.alterarPos(nrTabuleiro - backNrSpaces);
                    posPisadas.get(nrTabuleiro - backNrSpaces).acrescentaPosPisadas();
                } else if (player.getPosicaoAtual() + nrSpaces == nrTabuleiro) {
                    winner = player;
                    posPisadas.get(nrTabuleiro).acrescentaPosPisadas();
                    player.somaPos(nrSpaces);
                } else {
                    posPisadas.get(player.getPosicaoAtual() + nrSpaces).acrescentaPosPisadas();
                    player.somaPos(nrSpaces);
                }
                allPlayersInfo.put(player.getId(), player);
                return true;
            }
        }
        return false;
    }

    // 6.1
    public String reactToAbyssOrTool() {

        Castigo castigo = null;

        if (abyssesEtools.containsKey(player.getPosicaoAtual())) {
            castigo = abyssesEtools.get(player.getPosicaoAtual());
            castigo.castigar(getProgrammers(player.getPosicaoAtual()), getCurrentPlayerID());
        }

        if (player.getEstado()) {
            List<Programmer> jogadoresAindaVivosEmJogo = getProgrammers(false);
            for (int i = 0; i < jogadoresAindaVivosEmJogo.size(); i++) {
                if (jogadoresAindaVivosEmJogo.get(i).getId() == getCurrentPlayerID()) {
                    if (i == jogadoresAindaVivosEmJogo.size() - 1) {
                        player = jogadoresAindaVivosEmJogo.get(0);
                    } else {
                        player = jogadoresAindaVivosEmJogo.get(i + 1);
                    }
                    break;
                }
            }
        } else {
            if (allPlayersInfo.lastKey() == player.getId()) {
                player = allPlayersInfo.get(allPlayersInfo.firstKey());
            } else {
                player = allPlayersInfo.get(allPlayersInfo.higherKey(player.getId()));
            }
        }

        nrTurnos++;
        if (castigo != null) {
            return castigo.getDescricao();
        } else {
            return null;
        }
    }

    // 7
    public boolean gameIsOver() {

        for (Programmer p : allPlayersInfo.values()) {
            if (p.getPosicaoAtual() == nrTabuleiro) {
                winner = p;
                return true;
            }
        }

        return false;
    }

    // 8
    public List<String> getGameResults() {

        List<String> results = new ArrayList<>();
        List<Programmer> losers = new ArrayList<>(allPlayersInfo.values());

        //Faz a comparação de cada posicaoAtual e ordena por ordem decrescente
        // depois compara os nomes e organiza por ordem alfabética
        losers.sort(Comparator.comparingInt(Programmer::getPosicaoAtual).reversed().thenComparing(Programmer::getName));

        results.add("O GRANDE JOGO DO DEISI");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(Integer.toString(nrTurnos));
        results.add("");
        results.add("VENCEDOR");
        results.add(winner.getName());
        results.add("");
        results.add("RESTANTES");

        for (Programmer p : losers) {
            if (p.getId() != winner.getId()) {
                results.add(p.getName() + " " + p.getPosicaoAtual());
            }
        }

        return results;
    }

    // 9
    public JPanel getAuthorsPanel() {

        JPanel dados = new JPanel();
        JLabel autores = new JLabel("*****************\n");
        JLabel autores1 = new JLabel("Data: 13/11/2021\n");
        JLabel autor3 = new JLabel("Projeto LP2 - 2º ano\n");
        JLabel autores2 = new JLabel("Feito:\n");
        JLabel autor1 = new JLabel("22102351; Catherine Prokhorov\n");
        JLabel autor2 = new JLabel("21905624; Perla Lima\n");
        JLabel autores3 = new JLabel("*****************\n");

        dados.add(autores);
        dados.add(autores1);
        dados.add(autor3);
        dados.add(autores2);
        dados.add(autor1);
        dados.add(autor2);
        dados.add(autores3);
        return dados;
    }

    // new
    public boolean saveGame(File file) {

        try {
            FileWriter ficheiroDeEscrita = new FileWriter(file);
            BufferedWriter bufferedEscrita = new BufferedWriter(ficheiroDeEscrita);
            bufferedEscrita.append(String.valueOf(nrTabuleiro))
                    .append("£")
                    .append(String.valueOf(nrTurnos))
                    .append("£")
                    .append(String.valueOf(getCurrentPlayerID()))
                    .append("\n");
            for (Programmer player : getProgrammers(true)) {
                bufferedEscrita.append("€ ")
                        .append(String.valueOf(player.getId()))
                        .append("|")
                        .append(player.getName())
                        .append("|")
                        .append(player.getFavLanguage())
                        .append("|")
                        .append(player.getColor().toString())
                        .append("\n");
            }
            bufferedEscrita.append("------").append("\n");
            for (Map.Entry<Integer, Castigo> entry : abyssesEtools.entrySet()) {
                int typeAbyssOrTool = (entry.getValue().isEstaNumAbismo()) ? 0 : 1;
                bufferedEscrita.append("% ")
                        .append(String.valueOf(typeAbyssOrTool))
                        .append("|")
                        .append(String.valueOf(entry.getValue().getId()))
                        .append("|")
                        .append(String.valueOf(entry.getKey()))
                        .append("\n");
            }
            bufferedEscrita.append("------").append("\n");
            for (Programmer programmer : getProgrammers(true)) {
                bufferedEscrita.append("! ")
                        .append(String.valueOf(programmer.getId()))
                        .append("|")
                        .append(String.valueOf(programmer.getPosicaoAtual()))
                        .append("|")
                        .append(programmer.classificarTools(true))
                        .append("|")
                        .append(programmer.getEstadoEmJogoOuDerrotado())
                        .append("\n");
            }
            bufferedEscrita.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadGame(File file) {

        try {

            FileReader ficheiroDeLeitura = new FileReader(file);
            BufferedReader bufferedLeitura = new BufferedReader(ficheiroDeLeitura);

            String linha = bufferedLeitura.readLine();
            String[] info = linha.split("£");
            int tabuleiro = Integer.parseInt(info[0]);
            int turnos = Integer.parseInt(info[1]);
            int idJogador = Integer.parseInt(info[2]);

            List<String[]> infoFirtPlayers = new ArrayList<>();
            String infoPlayerLinha;
            while ((infoPlayerLinha = bufferedLeitura.readLine()).contains("€")) {
                String[] programmerInfo = infoPlayerLinha.split("\\|");
                String[] player = new String[4];
                player[0] = programmerInfo[0].replace("€ ", "");
                player[1] = programmerInfo[1];
                player[2] = programmerInfo[2].replace("; ", ";");
                player[3] = programmerInfo[3];
                infoFirtPlayers.add(player);
            }

            List<String[]> listAbyssETools = new ArrayList<>();
            String AbyssOrToolLine;
            while ((AbyssOrToolLine = bufferedLeitura.readLine()).contains("%")) {
                String[] abyssOrToolInfo = AbyssOrToolLine.split("\\|");
                String[] abyssOrTool = new String[3];
                abyssOrTool[0] = abyssOrToolInfo[0].replace("% ", "");
                abyssOrTool[1] = abyssOrToolInfo[1];
                abyssOrTool[2] = abyssOrToolInfo[2];
                listAbyssETools.add(abyssOrTool);
            }

            String[][] playerInfo = new String[infoFirtPlayers.size()][4];
            String[][] abyssAndToolInfo = new String[listAbyssETools.size()][3];
            for (int i = 0; i < infoFirtPlayers.size(); i++) {
                playerInfo[i] = infoFirtPlayers.get(i);
            }
            for (int i = 0; i < listAbyssETools.size(); i++) {
                abyssAndToolInfo[i] = listAbyssETools.get(i);
            }

            try {
                createInitialBoard(playerInfo, tabuleiro, abyssAndToolInfo);
            } catch (InvalidInitialBoardException e) {
                return false;
            }

            String programmerDetailsLinha;
            while ((programmerDetailsLinha = bufferedLeitura.readLine()) != null) {
                String[] playerDetails = programmerDetailsLinha.split("\\|");
                Programmer programmer = allPlayersInfo.get(Integer.parseInt(playerDetails[0].replace("! ", "")));
                programmer.alterarPos(Integer.parseInt(playerDetails[1]));
                String[] ferramenta = playerDetails[2].split(";");
                for (String string : ferramenta) {
                    if (!string.equals("No tools")) {
                        programmer.adicionaTool(abyssesEtools.get(Integer.parseInt(string)));
                    }
                }

                if (playerDetails[3].equals("Derrotado")) {
                    programmer.retirarPlayer();
                }
            }

            this.nrTabuleiro = tabuleiro;
            nrTurnos = turnos;
            player = allPlayersInfo.get(idJogador);

            bufferedLeitura.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // extras
    public List<PosicoesPisadasTabuleiro> getPosPisadas() {
        return new ArrayList<>(posPisadas.values());
    }

    public List<Castigo> getAbyss() {

        ArrayList<Castigo> abismos = new ArrayList<>();
        for (Castigo castiguinho : abyssesEtools.values()) {
            if (castiguinho.isEstaNumAbismo()) {
                abismos.add(castiguinho);
            }
        }
        return abismos;
    }

}