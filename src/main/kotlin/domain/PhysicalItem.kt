package domain

import kotlinx.serialization.Serializable

@Serializable
data class PhysicalItem (
    var id: Int,
    val pieceId: Int,
    val edition: String? = null,
    val format: String? //Format meaning book, loose paper etc.
)