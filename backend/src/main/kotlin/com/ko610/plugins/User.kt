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
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.NumberFormatException
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
                val userSetting = call.receive<Setting>()
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
                        it[nickname] = userSetting.nickname
                        it[icon] = userSetting.icon
                        it[email] = userSetting.email
                        it[school] = userSetting.school
                    }
                }
                call.respond(HttpStatusCode.Created)
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        delete("/user/{id?}") {
            try {
                val strId = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.NotFound)
                val intId = strId.toInt()
                val count = transaction {
                    addLogger(StdOutSqlLogger)

                    com.ko610.models.Setting.deleteWhere { com.ko610.models.Setting.userId eq intId }
                    com.ko610.models.User.deleteWhere { com.ko610.models.User.id eq intId }
                }

                if(count != 1)
                    call.respond(HttpStatusCode.NotFound)
                else
                    call.respond(HttpStatusCode.ResetContent)
            } catch (ex: NumberFormatException) {
                call.respond(HttpStatusCode.NotFound)
            } catch (ex: Exception){
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        get("/user/{id?}") {
            try {
                println("test")
                val strId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound)
                val intId = strId.toInt()
                val userProfile = transaction {
                    addLogger(StdOutSqlLogger)

                    com.ko610.models.User.select { com.ko610.models.User.id eq intId }.map{ it.toUser() }.toString()
                }
                call.respondText(userProfile)
            } catch (ex: NumberFormatException) {
                call.respond(HttpStatusCode.NotFound)
            } catch (ex: Exception){
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}

fun ResultRow.toUser() = User(
    name = this[com.ko610.models.User.name],
    birthday = this[com.ko610.models.User.birthday].toString(),
    sex = this[com.ko610.models.User.sex],
    introduction = this[com.ko610.models.User.introduction],
)

@Serializable
data class User(
    val name: String,
    val birthday: String,
    val sex: Int,
    val introduction: String,
)

data class Setting(
    val icon: String,
    val nickname: String,
    val email: String?,
    val school: String,
)