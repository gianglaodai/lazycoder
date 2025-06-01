package io.lazycoder.presentation.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.lazycoder.domain.user.User
import io.lazycoder.domain.user.UserService
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val id: Int?, val name: String, val age: Int)

fun Route.userRoutes(userService: UserService) {
    route("/users") {

        get("/") {
            val users = userService.getAll()
            call.respond(HttpStatusCode.OK, users)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = userService.getById(id)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, user)
            }
        }

        post {
            val userRequest = call.receive<UserRequest>()
            val id = userService.create(User(userRequest.id, userRequest.name, userRequest.age))
            call.respond(HttpStatusCode.Companion.Created, id)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val userRequest = call.receive<UserRequest>()
            userService.update(id, User(userRequest.id, userRequest.name, userRequest.age))
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}