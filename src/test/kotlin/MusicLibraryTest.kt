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
    }

    @Test
    fun addPiece(){
        val library = MusicLibrary()
        val beethoven = Composer("Beethoven", 1770, 1827, "German")
        val piece = Piece(
            id = "Op 106",
            title = "Piano Sonata no. 29",
            composer = beethoven,
            instrumentation = "Piano"
        )
        library.addPiece(piece)
        val result = library.findPiecesByComposer("Beethoven")
        assertEquals(1, result.size)
        assertEquals("Op. 106", result.first().id)
    }
}