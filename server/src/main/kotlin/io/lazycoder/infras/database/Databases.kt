package io.lazycoder.infras.database

import io.ktor.server.application.Application
import io.lazycoder.infras.user.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val config = environment.config

    val dbType = config.property("ktor.database.type").getString()

    val dbConfigPath = "ktor.database.${dbType}"

    val dbUrl = config.property("$dbConfigPath.url").getString()
    val dbDriver = config.property("$dbConfigPath.driver").getString()
    val dbUser = config.property("$dbConfigPath.user").getString()
    val dbPassword = config.property("$dbConfigPath.password").getString()

    val database = Database.Companion.connect(
        url = dbUrl, driver = dbDriver, user = dbUser, password = dbPassword
    )

//    transaction(database) {
//        SchemaUtils.create(Users)
//    }
}