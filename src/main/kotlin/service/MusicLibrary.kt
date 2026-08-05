package service

import domain.Composer
import domain.PhysicalItem
import domain.Piece

class MusicLibrary {
    var composers = mutableMapOf<String, Composer>()
    var pieces = mutableMapOf<String, Piece>()
    var items = mutableMapOf<String, PhysicalItem>()

    //Register
    fun addComposer(composer: Composer) {
        composers[composer.name] = composer
    }

    fun addPiece(piece: Piece) {
        pieces[piece.id] = piece
        addComposer(piece.composer)
    }

    fun addPhysicalItem(item: PhysicalItem) {
        items[item.id] = item
    }

    // getting all
    fun getAllComposers(): List<Composer> = composers.values.toList()
    fun getAllPieces(): List<Piece> = pieces.values.toList()
    fun getAllItems(): List<PhysicalItem> = items.values.toList()

    // searching
    fun findPiecesByComposer(name: String): List<Piece> =
        pieces.values.filter { it.composer.name.contains(name, ignoreCase = true) }

    fun findItemsForPiece(pieceId: String): List<PhysicalItem> =
        items.values.filter { pieceId in it.pieceId }

    fun findPiecesByInstrumentation(instrumentation: String): List<Piece> =
        pieces.values.filter { it.instrumentation == instrumentation }

    fun findPiecesByTitle(titleFragment: String): List<Piece> =
        pieces.values.filter { it.title.contains(titleFragment, ignoreCase = true) }

    // removing
    fun removeComposer(name: String): Boolean {
        val composer = composers[name] ?: return false

        // need to remove existing pieces for the composer as well
        val piecesToRemove = pieces.values.filter { it.composer.name == name }

        for (piece in piecesToRemove) {
            val itemIdsToRemove = items.values
                .filter { it.pieceId == piece.id }
                .map { it.id }
            itemIdsToRemove.forEach { items.remove(it) }
            pieces.remove(piece.id)
        }
        composers.remove(name)
        return true
    }

    fun removePiece(id: String): Boolean {
        val piece = pieces[id] ?: return false

        // need to remove existing items for the piece as well
        val itemIdsToRemove = items.values
            .filter { it.pieceId == id }
            .map { it.id }
        itemIdsToRemove.forEach { items.remove(it) }
        pieces.remove(id)
        return true
    }

    fun removeItem(id: String): Boolean {
        return items.remove(id) != null
    }

}