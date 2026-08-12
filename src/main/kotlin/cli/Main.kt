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
            "4" -> listPiecesByComposerCli(library)
            "5" -> listPiecesByInstrumentationCli(library)
            "6" -> searchPiecesByTitleCli(library)
            "7" -> removeComposerCli(library)
            "8" -> removePieceCli(library)
            "9" -> removeItemCli(library)
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
    val pieceId = readlnOrNull()?.trim()?.toIntOrNull()
    if (pieceId == null) {
        println("Invalid piece ID.\n")
        return
    }

    val piece = library.findPieceById(pieceId)
    if (piece == null) {
        println("No piece with ID $pieceId found. Add piece first.\n")
        return
    }

    println("Name of edition (optional):")
    val editionInput = readlnOrNull()?.trim().orEmpty()
    val edition = editionInput.ifBlank { null }

    println("Format (book/paper etc.):")
    val format = readlnOrNull()?.trim().orEmpty()

    val item = PhysicalItem(
        id = 0,
        pieceId = pieceId,
        edition = edition,
        format = format
    )

    library.addPhysicalItem(item)
    println("Item added successfully\n\n")
}

private fun listPiecesByComposerCli(library: MusicLibrary) {
    println("Name the composer:")
    val composerName = readln().trim()
    println("Pieces found:\n")
    val pieces = library.findPiecesByComposer(composerName)
    if(pieces.isEmpty()) {
        println("Could not find any pieces!")
        return
    }
    for (piece in pieces) {
        println("${piece.composer.name}: ${piece.title}, ${piece.catalogueId} for ${piece.instrumentation}")
    }
    println("End of list\n")
}

private fun listPiecesByInstrumentationCli(library: MusicLibrary) {
    println("Name the instrumentation:")
    val instrumentation = readln().trim()
    println("Pieces found:\n")
    val pieces = library.findPiecesByInstrumentation(instrumentation)
    if(pieces.isEmpty()) {println(
        "Could not find any pieces!")
        return
    }
    for (piece in pieces) {
        println("${piece.composer.name}: ${piece.title}, ${piece.catalogueId} for ${piece.instrumentation}")
    }
    println("End of list\n")
}

private fun searchPiecesByTitleCli(library: MusicLibrary) {
    println("Name the title:")
    val title = readln().trim()
    println("Pieces found:\n")
    val pieces = library.findPiecesByTitle(title)
    if(pieces.isEmpty()) {println(
        "Could not find any pieces!")
        return
    }
    for (piece in pieces) {
        println("${piece.composer.name}: ${piece.title}, ${piece.catalogueId} for ${piece.instrumentation}")
    }
    println("End of list\n")
}

private fun removeComposerCli(library: MusicLibrary) {
    println("Name the composer:")
    val composerName = readlnOrNull()?.trim().orEmpty()
    if(library.removeComposer(composerName)){
        println("$composerName removed!")
        return
    }
    println("Error, could not remove $composerName!")
}

private fun removePieceCli(library: MusicLibrary) {
    println("Name the piece:")
    val title = readln().trim()
    var pieces = library.findPiecesByTitle(title)
    if(pieces.isEmpty()) { //No pieces found
        println("Could not find $title, check spelling")
        return
    }
    if(pieces.size >= 2) { //Several pieces found
        println("More than one piece found, enter id of the correct one to remove")
        for (piece in pieces) {
            println("id: ${piece.pieceId} - ${piece.composer.name}: ${piece.title}, ${piece.catalogueId} for ${piece.instrumentation}")
        }
        val chosenId = readlnOrNull()?.trim()?.toIntOrNull()
        if (chosenId == null) {
            println("Invalid ID.\n")
            return
        }
        val piece = library.findPieceById(chosenId) //Finding correct piece
        if(piece == null) {
            println("Could not find piece, check id")
            return
        }
        pieces = listOf(piece)
    }
    if(library.removePiece(pieces.first().pieceId)){
        println("Piece removed!")
        return
    }
    println("Could not remove $title")
}

private fun removeItemCli(library: MusicLibrary) {
    println("\nName the title:")
    val title = readlnOrNull()?.trim().orEmpty()

    var pieces = library.findPiecesByTitle(title)
    if (pieces.isEmpty()) { //No pieces found
        println("Could not find '$title', check spelling\n")
        return
    }

    if (pieces.size >= 2) { //Several pieces found
        println("More than one piece found, enter id of the correct one")
        for (piece in pieces) {
            println("id: ${piece.pieceId} - ${piece.composer.name}: ${piece.title}, ${piece.catalogueId} for ${piece.instrumentation}")
        }
        val chosenId = readlnOrNull()?.trim()?.toIntOrNull()
        if (chosenId == null) {
            println("Invalid ID.\n")
            return
        }
        val piece = library.findPieceById(chosenId)
        if (piece == null) {
            println("Could not find piece, check id\n")
            return
        }
        pieces = listOf(piece)
    }

    val items = library.findItemsForPiece(pieces.first().pieceId)
    if (items.isEmpty()) {
        println("Could not find any items for '$title'!\n")
        return
    }

    val itemId: Int = if (items.size == 1) {
        items.first().id
    } else {
        println("More than one item found, enter id of the correct one")
        for (item in items) {
            println("id: ${item.id} - Edition: ${item.edition}, Format: ${item.format}")
        }
        val chosenItemId = readlnOrNull()?.trim()?.toIntOrNull()
        if (chosenItemId == null) {
            println("Invalid item ID.\n")
            return
        }
        chosenItemId
    }

    if (library.removeItem(itemId)) {
        println("Item removed!\n")
        return
    }
    println("Could not remove item!\n")
}