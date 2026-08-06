package cli

import domain.Composer
import domain.PhysicalItem
import domain.Piece
import service.MusicLibrary

fun main(){
    val library = MusicLibrary()

    println()
    println("========================================")
    println("         Welcome to score-library       ")
    println("========================================")

    while (true) {
        println(
            """
            
            +--------------------------------------+
            |                 Menu                 |
            +--------------------------------------+
            1) Add composer
            2) Add piece
            3) Add physical item
            4) List pieces by composer
            5) List pieces by instrumentation
            6) Search pieces by title
            7) Remove composer
            8) Remove piece
            9) Remove item
            0) Exit
            """
        )

        println("Choose an option:")
        when (val choice = readlnOrNull()) {
            "1" -> addComposerCli(library)
            "2" -> addPieceCli(library)
            "3" -> addItemCli(library)
            "0" -> {
                println("Goodbye!")
                return
            }
            else -> println("Unknown option: $choice")
        }
    }
}

private fun addComposerCli(library: MusicLibrary) {
    println("\nAdding composer\n")
    println("Name of composer:")
    val name = readlnOrNull()?.trim().orEmpty()

    println("Birthyear:")
    val birthyear = readlnOrNull()?.trim()?.toIntOrNull() ?: 0

    println("Deathyear:")
    val deathyear = readlnOrNull()?.trim()?.toIntOrNull() ?: 0

    println("Nationality:")
    val nationality = readlnOrNull()?.trim().orEmpty()

    val composer = Composer(
        name = name,
        birthYear = birthyear,
        deathYear = deathyear,
        nationality = nationality
    )

    library.addComposer(composer)
    println("Composer added successfully\n\n")
}

private fun addPieceCli(library: MusicLibrary) {
    println("\nAdding piece\n")
    println("Catalogue Id: ")
    val catalogueId = readlnOrNull()?.trim().orEmpty()

    println("Title: ")
    val title = readlnOrNull()?.trim().orEmpty()

    println("Composer: ")
    val name = readlnOrNull()?.trim().orEmpty()

    val composer = library.composers[name]
    if (composer == null) {
        println("No composer found, add composer first")
        return
    }

    println("Instrumentation: ")
    val instrumentation = readlnOrNull()?.trim().orEmpty()

    val piece = Piece(
        pieceId = 0,
        catalogueId = catalogueId,
        title = title,
        composer = composer,
        instrumentation = instrumentation,
    )

    library.addPiece(piece)
    println("Piece added successfully\n\n")
}

private fun addItemCli(library: MusicLibrary) {
    println("\nAdding item\n")
    println("PieceId:")
    val pieceId = readlnOrNull()?.trim()?.toIntOrNull() ?: 0

    println("Name of edition:")
    val edition = readlnOrNull()?.trim()

    println("Format: (book/paper etc.)")
    val format = readlnOrNull()?.trim()

    val item = PhysicalItem(
        id = 0,
        pieceId = pieceId,
        edition = edition,
        format = format
    )

    library.addPhysicalItem(item)
    println("Item added successfully\n\n")
}