package io.lazycoder.infras.templating

import io.ktor.server.application.Application
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlin.random.Random
import kotlinx.html.body
import kotlinx.html.table
import kotlinx.html.tbody

fun Application.configureTemplating() {
    routing {
        val random = Random(System.currentTimeMillis())

        get("/") {
            call.respondHtml {
                leaderboardPage(random)
            }
        }

        get("/more-rows") {
            call.respondHtml {
                body {
                    table {
                        tbody {
                            randomRows(random)
                        }
                    }
                }
            }
        }
    }
}
