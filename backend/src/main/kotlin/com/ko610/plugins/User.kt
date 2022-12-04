package com.ko610.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.userRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("hello")
        }
        post("/user") {
            val user = call.receive<User>()
            call.respondText("your name is ${user.nickname}")
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