package domain

data class Piece(
    val id: Int,
    val title: String,
    val composer: Composer,
    val opus: Int? = null,
    val catalogue: Int? = null,
    val instrumentation: String
)