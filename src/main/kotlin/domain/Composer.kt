package domain

import kotlinx.serialization.Serializable

@Serializable
data class Composer (
    val name: String,
    val birthYear: Int? = null,
    val deathYear: Int? = null,
    val nationality: String? = null
)