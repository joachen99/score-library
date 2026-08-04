package domain

import kotlinx.serialization.Serializable

@Serializable
data class Piece(
    val id: String,
    val title: String,
    val composer: Composer,
    val instrumentation: String
)