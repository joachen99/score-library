package data.database

import domain.ScoreListItem
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.io.readSqlQuery

interface ScoreLRepository{
    fun showAllScores(): List<ScoreListItem>
}

class ScoreRepositoryImplementation(
    private val dbConfig: DatabaseConfig = DatabaseConfig()
): ScoreLRepository {
    override fun showAllScores(): List<ScoreListItem>{
        val query = """
            SELECT
                c.name AS composer_name,
                p.title AS piece_title,
                pi.edition AS edition,
                pi.format AS format
            FROM physicalitem AS pi
            INNER JOIN piece AS p
                ON pi.pieceid = p.pieceid
            INNER JOIN composer AS c
                ON p.composer = c.name
            ORDER BY c.name, p.title, pi.edition
        """.trimIndent()
        val frame = DataFrame.readSqlQuery(
            dbConfig.dbConfig,
            query
        )
        return frame.rows().map { row ->
            ScoreListItem(
                composerName = row["composer_name"] as String,
                pieceTitle = row["piece_title"] as String,
                edition = row["edition"] as String?,
                format = row["format"] as String?
            )
        }
    }
}