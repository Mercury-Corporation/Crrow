package com.ko610.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

fun Application.userRouting() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(Routing)

    routing {
        get("/hello") {
            call.respondText("hello")
        }
        post("/user") {
            println("user")
            try {
                val user = call.receive<User>()
                call.respond(HttpStatusCode.Created, "your name is ${user.nickname}")
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.BadRequest, "bad request")
            }
        }
    }
}

@Serializable
data class User(
    val name: String,
    val birthday: String,
    val sex: Int,
    val introduction: String,
    val nickname: String,
    val icon: String,
    val email: String,
    val school: String
)