package com.ko610.plugins

import com.ko610.models.Setting
import com.ko610.models.User
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
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
                val user = call.receive<PostUser>()
                transaction {
                    addLogger(StdOutSqlLogger)

                    val id = User.insertAndGetId {
                        it[name] = user.name
                        it[birthday] = LocalDate.parse(user.birthday)
                        it[sex] = user.sex
                        it[introduction] = user.introduction
                        it[type] = 1
                    }

                    Setting.insert {
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
        delete("/user/{id?}") {
            try {
                val strId = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.NotFound)
                val intId = strId.toInt()
                val count = transaction {
                    addLogger(StdOutSqlLogger)

                    Setting.deleteWhere { Setting.userId eq intId }
                    User.deleteWhere { User.id eq intId }
                }

                if (count != 1)
                    call.respond(HttpStatusCode.NotFound)
                else
                    call.respond(HttpStatusCode.ResetContent)
            } catch (ex: NumberFormatException) {
                call.respond(HttpStatusCode.NotFound)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        get("/user/{id?}") {
            try {
                val strId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound)
                val intId = strId.toInt()
                val userProfile = transaction {
                    addLogger(StdOutSqlLogger)

                    val user = User.select { User.id eq intId }.single()
                    val setting = Setting.select { Setting.userId eq intId }.single()

                    GetUser(
                        name = user[User.name],
                        birthday = user[User.birthday].toString(),
                        sex = user[User.sex],
                        introduction = user[User.introduction],
                        type = user[User.type],
                        coin = user[User.coin],
                        nickname = setting[Setting.nickname],
                        icon = setting[Setting.icon],
                        email = setting[Setting.email],
                        school = setting[Setting.school],
                        range = setting[Setting.range],
                    )
                }
                call.respondText(Json.encodeToString(userProfile), ContentType.Text.Plain, HttpStatusCode.OK)
            } catch (ex: NumberFormatException) {
                call.respond(HttpStatusCode.NotFound)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}


@Serializable
data class PostUser(
    val name: String,
    val birthday: String,
    val sex: Int,
    val introduction: String,
    val nickname: String,
    val icon: String,
    val email: String?,
    val school: String
)

@Serializable
data class GetUser(
    val name: String,
    val birthday: String,
    val sex: Int,
    val introduction: String,
    val type: Int,
    val coin: Int,
    val nickname: String,
    val icon: String,
    val email: String?,
    val school: String,
    val range: Int,
)

