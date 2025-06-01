package io.lazycoder.presentation.route

import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.sse.SSE
import io.lazycoder.domain.user.UserService
import io.lazycoder.presentation.user.userRoutes
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (!bodyText.startsWith("Hello"))
                ValidationResult.Invalid("Body text should start with 'Hello'")
            else ValidationResult.Valid
        }
    }
    install(Resources)
    install(SSE)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.Companion.InternalServerError
            )
        }
    }
    routing {
        get<Articles> { article ->
            // Get all articles ...
            call.respond("List of articles sorted starting from ${article.sort}")
        }
//        sse("/hello") {
//            send(ServerSentEvent("world"))
//        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
        staticResources("/", "/web")

    }
    val userService: UserService by inject()
    routing {
        userRoutes(userService)
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")