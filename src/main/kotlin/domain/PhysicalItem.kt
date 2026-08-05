package domain

import kotlinx.serialization.Serializable

@Serializable
data class PhysicalItem (
    val id: String,
    val pieceId: String,
    val edition: Edition? = null,
    val format: String //Format meaning book, loose paper etc.
)