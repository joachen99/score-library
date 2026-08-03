package domain

import kotlinx.serialization.Serializable

@Serializable
data class Edition(
    val publisher: String? = null,
    val yearPublished: Int? = null,
    val editor: Int? = null,
    val catalogueNumber: String? = null
)