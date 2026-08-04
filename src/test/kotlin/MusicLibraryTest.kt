import domain.Composer
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
            id = "Op. 106",
            title = "Piano Sonata no. 29",
            composer = beethoven,
            instrumentation = "Piano"
        )

        library.addPiece(piece)

        val result = library.findPiecesByComposer("Beethoven")
        assertEquals(1, result.size)
        assertEquals("Op. 106", result.first().id)
    }

    @Test
    fun findPiecesByInstrumentation() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece1 = Piece("Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val piece2 = Piece("Op. 3", "String Trio", beethoven, "Strings")

        library.addPiece(piece1)
        library.addPiece(piece2)

        val pianoPieces = library.findPiecesByInstrumentation("Piano")
        assertEquals(1, pianoPieces.size)
        assertEquals("Op. 2", pianoPieces.first().id)
    }

    @Test
    fun findPiecesByTitle() {
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")

        val piece1 = Piece("Op. 2", "Piano Sonata no. 1", beethoven, "Piano")
        val piece2 = Piece("Op. 10", "String Quartet", beethoven, "Strings")

        library.addPiece(piece1)
        library.addPiece(piece2)

        val sonatas = library.findPiecesByTitle("Sonata")
        assertEquals(1, sonatas.size)
        assertEquals("Op. 2", sonatas.first().id)
    }
}