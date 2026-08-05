package cli

import domain.Composer
import service.MusicLibrary

fun main(){
    val library = MusicLibrary()

    println()
    println("========================================")
    println("         Welcome to score-library       ")
    println("========================================")

    while (true) {
        println(
            """
            
            +--------------------------------------+
            |                 Menu                 |
            +--------------------------------------+
            1) Add composer
            2) Add piece
            3) Add physical item
            4) List pieces by composer
            5) List pieces by instrumentation
            6) Search pieces by title
            7) Remove composer
            8) Remove piece
            9) Remove item
            0) Exit
            """
        )

        println("Choose an option:\n")
        val choice = readln()

        when (choice) {
            "1" -> addComposerCli(library)
            "0" -> {
                println("Goodbye!")
                return
            }
            else -> println("Unknown option: $choice")
        }
    }
}

private fun addComposerCli(library: MusicLibrary) {
    println("\nAdding composer\n")
    println("Name of composer:\n")
    val name = readlnOrNull()?.trim().orEmpty()
    println("Birthyear:\n")
    val birthyear = readlnOrNull()?.trim()?.toIntOrNull() ?: 0
    println("Deathyear:\n")
    val deathyear = readlnOrNull()?.trim()?.toIntOrNull() ?: 0
    println("Nationality:\n")
    val nationality = readlnOrNull()?.trim().orEmpty()
    val composer = Composer(
        name = name,
        birthYear = birthyear,
        deathYear = deathyear,
        nationality = nationality
    )
    library.addComposer(composer)
    println(composer.name + " added successfully\n\n")
}