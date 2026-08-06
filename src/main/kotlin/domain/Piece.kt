package domain

import kotlinx.serialization.Serializable

@Serializable
data class Piece(
    var pieceId: Int,
    val catalogueId: String,
    val title: String,
    val composer: Composer,
    val instrumentation: String
)