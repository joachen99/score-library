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

    // searching
    fun getAllComposers(): List<Composer> = composers.values.toList()
    fun getAllPieces(): List<Piece> = pieces.values.toList()
    fun getAllItems(): List<PhysicalItem> = items.values.toList()

    fun findPiecesByComposer(name: String): List<Piece> =
        pieces.values.filter { it.composer.name.contains(name, ignoreCase = true) }

    fun findItemsForPiece(pieceId: String): List<PhysicalItem> =
        items.values.filter { pieceId in it.pieceIds }

    fun findPiecesByInstrumentation(instrumentation: String): List<Piece> =
        pieces.values.filter { it.instrumentation == instrumentation }

    fun findPiecesByTitle(titleFragment: String): List<Piece> =
        pieces.values.filter { it.title.contains(titleFragment, ignoreCase = true) }
}