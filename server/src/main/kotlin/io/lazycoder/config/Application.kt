package io.lazycoder.config

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import io.lazycoder.infras.administration.configureAdministration
import io.lazycoder.infras.database.configureDatabases
import io.lazycoder.infras.frameworks.configureKoin
import io.lazycoder.infras.http.configureHTTP
import io.lazycoder.infras.monitoring.configureMonitoring
import io.lazycoder.infras.security.configureSecurity
import io.lazycoder.infras.serialization.configureSerialization
import io.lazycoder.infras.templating.configureTemplating
import io.lazycoder.presentation.route.configureRouting

fun main(args: Array<String>) {
    val dotenv = dotenv()
    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }
    EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureTemplating()
    configureDatabases()
    configureKoin()
    configureAdministration()
    configureRouting()
}