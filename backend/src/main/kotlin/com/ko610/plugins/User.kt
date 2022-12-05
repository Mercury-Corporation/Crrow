package com.ko610.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

fun Application.userRouting() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(Routing)

    routing {
        post("/user") {
            try {
                val user = call.receive<User>()
                transaction {
                    addLogger(StdOutSqlLogger)

                    val id = com.ko610.models.User.insertAndGetId {
                        it[name] = user.name
                        it[birthday] = LocalDate.parse(user.birthday)
                        it[sex] = user.sex
                        it[introduction] = user.introduction
                        it[type] = 1
                    }

                    com.ko610.models.Setting.insert {
                        it[userId] = id
                        it[nickname] = user.nickname
                        it[icon] = user.icon
                        it[email] = user.email
                        it[school] = user.school
                    }
                }
                call.respond(HttpStatusCode.Created)
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
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
    val email: String?,
    val school: String
)