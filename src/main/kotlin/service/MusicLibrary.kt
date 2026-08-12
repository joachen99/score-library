package service

import domain.Composer
import domain.PhysicalItem
import domain.Piece

class MusicLibrary {
    var composers = mutableMapOf<String, Composer>()
    var pieces = mutableMapOf<Int, Piece>()
    var items = mutableMapOf<Int, PhysicalItem>()

    private var nextPieceId = 0
    private var nextItemId = 0

    //Register
    fun addComposer(composer: Composer) {
        composers[composer.name] = composer
    }

    fun addPiece(piece: Piece) {
        piece.pieceId = nextPieceId++
        pieces[piece.pieceId] = piece
        addComposer(piece.composer)
    }

    fun addPhysicalItem(item: PhysicalItem) {
        item.id = nextItemId++
        items[item.id] = item
    }

    // getting all
    fun getAllComposers(): List<Composer> = composers.values.toList()
    fun getAllPieces(): List<Piece> = pieces.values.toList()
    fun getAllItems(): List<PhysicalItem> = items.values.toList()

    // searching
    fun findPiecesByComposer(name: String): List<Piece> =
        pieces.values.filter { it.composer.name.contains(name, ignoreCase = true) }

    fun findItemsForPiece(pieceId: Int): List<PhysicalItem> =
        items.values.filter { pieceId == it.pieceId }

    fun findPiecesByInstrumentation(instrumentation: String): List<Piece> =
        pieces.values.filter { it.instrumentation == instrumentation }

    fun findPiecesByTitle(title: String): List<Piece> =
        pieces.values.filter { it.title.contains(title, ignoreCase = true) }

    fun findPieceById(id: Int): Piece? = pieces[id]

    // removing
    fun removeComposer(name: String): Boolean {
        val composer = composers[name] ?: return false

        // we need to remove existing pieces for the composer
        val piecesToRemove = pieces.values.filter { it.composer.name == name }

        for (piece in piecesToRemove) {

            // we need to remove existing items for the piece
            val itemIdsToRemove = items.values
                .filter { it.pieceId ==  piece.pieceId }
                .map { it.id }
            itemIdsToRemove.forEach { items.remove(it) }
            pieces.remove(piece.pieceId)
        }
        composers.remove(name)
        return true
    }

    fun removePiece(pieceId: Int): Boolean {
        val piece = pieces[pieceId] ?: return false

        val itemIdsToRemove = items.values
            .filter { it.pieceId == pieceId }
            .map { it.id }

        itemIdsToRemove.forEach { items.remove(it) }

        pieces.remove(pieceId)
        return true
    }


    fun removeItem(id: Int): Boolean {
        return items.remove(id) != null
    }
}