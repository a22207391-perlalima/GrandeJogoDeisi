package pt.ulusofona.lp2.deisiGreatGame

fun router(): (CommandType) -> (GameManager, List<String>) -> String? = ::filtraComando

fun filtraComando(tipoComando: CommandType): (GameManager, List<String>) -> String? {
    return when (tipoComando) {
        CommandType.GET -> ::getComando
        CommandType.POST -> ::ativaPost
    }
}

fun getComando(manager: GameManager, listArgumentos: List<String>): String? {
    val detalhes = manager.getProgrammers(true)
    return when (listArgumentos[0]) {
        "PLAYER" -> acionaPlayer(detalhes, listArgumentos[1])
        "PLAYERS_BY_LANGUAGE" -> acionaPlayerLinguaFav(detalhes, listArgumentos[1])
        "POLYGLOTS" -> acionaPoligolotas(detalhes)
        else -> null
    }
}

fun ativaPost(manager: GameManager, listArgumentos: List<String>): String? {
    return when (listArgumentos[0]) {
        "MOVE" -> alteraPosJogador(manager, listArgumentos[1].toInt())
        else -> null
    }
}

fun acionaPlayer(programadores: List<Programmer>, listArgumentos: String): String {
    val auxProgramador = programadores
            .filter { listArgumentos in it.name }
    if (auxProgramador.isNotEmpty()) {
        auxProgramador.forEach { return it.toString() }
    }
    return "Inexistent player"
}

fun acionaPlayerLinguaFav(programadores: List<Programmer>, listArgumentos: String): String {
    val auxProgramador1 = programadores
            .filter { listArgumentos in it.linguagensPreferidasArrayList() }
    if (auxProgramador1.isNotEmpty()) {
        val nomesProgramadores = auxProgramador1.map { it.name }
        return nomesProgramadores.joinToString(",", "", "")
    }
    return ""
}

fun acionaPoligolotas(programadores: List<Programmer>): String {
    val auxProgramador2 =
            programadores.filter { it.tamanhoLinguagensFav() > 1 }
                    .sortedWith { p1, p2 -> p1.tamanhoLinguagensFav() - p2.tamanhoLinguagensFav() }
    return auxProgramador2.joinToString("\n", "", "") { "${it.name}:${it.tamanhoLinguagensFav()}" }
}


fun alteraPosJogador(manager: GameManager, posicoes: Int): String {
    manager.moveCurrentPlayer(posicoes)
    return manager.reactToAbyssOrTool() ?: "OK"
}