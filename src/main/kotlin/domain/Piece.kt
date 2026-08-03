package domain

data class Piece(
    val id: Int,
    val title: String,
    val composer: Composer,
    val instrumentation: String
)