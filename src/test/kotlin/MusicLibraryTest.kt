import domain.Composer
import domain.PhysicalItem
import domain.Piece
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import service.MusicLibrary

class MusicLibraryTest {
    @Test
    fun addComposer(){
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        library.addComposer(beethoven)

        assertEquals(1, library.composers.size)
        assertEquals("Beethoven", library.composers["Beethoven"]?.name)
    }

    @Test
    fun addPiece(){
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")
        val piece = Piece(
            pieceId = 0,
            catalogueId = "Op. 106",
            title = "Piano Sonata no. 29",
            composer = beethoven,
            instrumentation = "Piano"
        )

        library.addPiece(piece)

        val result = library.findPiecesByComposer("Beethoven")
        assertEquals(1, result.size)
        assertEquals(0, result.first().pieceId)
    }

    @Test
    fun findPiecesByInstrumentation() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece1 = Piece(0, "Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val piece2 = Piece(1, "Op. 3", "String Trio", beethoven, "Strings")

        library.addPiece(piece1)
        library.addPiece(piece2)

        val pianoPieces = library.findPiecesByInstrumentation("Piano")
        assertEquals(1, pianoPieces.size)
        assertEquals(0, pianoPieces.first().pieceId)
    }

    @Test
    fun findPiecesByTitle() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece1 = Piece(0, "Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val piece2 = Piece(1, "Op. 10", "String Quartet", beethoven, "Strings")

        library.addPiece(piece1)
        library.addPiece(piece2)

        val sonatas = library.findPiecesByTitle("Sonata")
        assertEquals(1, sonatas.size)
        assertEquals(0, sonatas.first().pieceId)
    }

    @Test
    fun removeComposerAlsoRemovesPiecesAndItems() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece1 = Piece(0, "Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val piece2 = Piece(1, "Op. 3", "String Trio", beethoven, "Strings")

        val item1 = PhysicalItem(0, pieceId = 0, format = "book")
        val item2 = PhysicalItem(1, pieceId = 1, format = "book")

        library.addComposer(beethoven)
        library.addPiece(piece1)
        library.addPiece(piece2)
        library.addPhysicalItem(item1)
        library.addPhysicalItem(item2)

        val removed = library.removeComposer("Beethoven")
        assertEquals(true, removed)
        assertEquals(0, library.composers.size)
        assertEquals(0, library.pieces.size)
        assertEquals(0, library.items.size)
    }

    @Test
    fun removePieceAlsoRemovesItemsButKeepsComposer() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece = Piece(0, "Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val item1 = PhysicalItem(0, pieceId = 0, format = "book")
        val item2 = PhysicalItem(0, pieceId = 0, format = "book")

        library.addComposer(beethoven)
        library.addPiece(piece)
        library.addPhysicalItem(item1)
        library.addPhysicalItem(item2)

        val removed = library.removePiece(0)

        assertEquals(true, removed)
        assertEquals(1, library.composers.size)
        assertEquals("Beethoven", library.composers["Beethoven"]?.name)
        assertEquals(0, library.pieces.size)
        assertEquals(0, library.items.size)
    }

    @Test
    fun removeItemOnlyRemovesTheItem() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")
        val piece = Piece(0, "Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val item = PhysicalItem(0, pieceId = 0, format = "book")

        library.addComposer(beethoven)
        library.addPiece(piece)
        library.addPhysicalItem(item)

        val removed = library.removeItem(0)
        assertEquals(true, removed)

        // Item borte
        assertEquals(0, library.items.size)

        // Stykket og komponisten eksisterer fortsatt
        assertEquals(1, library.pieces.size)
        assertEquals(1, library.composers.size)
    }

}