package data.database

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.kotlinx.dataframe.io.DbConnectionConfig

class DatabaseConfig {
    private val env = dotenv()
    val url = env["DATABASE_URL"]
    val username = env["DATABASE_USER"]
    val password = env["DATABASE_PASSWORD"]
    val dbConfig = DbConnectionConfig(url, username, password)
}