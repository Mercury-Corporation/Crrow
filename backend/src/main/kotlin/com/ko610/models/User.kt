package com.ko610.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object User : IntIdTable("User", "id") {
    val name = varchar("name", 15)
    val birthday = date("birthday")
    val sex = integer("sex")
    val introduction = varchar("introduction", 200)
    val banLevel = integer("ban_level")
    val type = integer("type")
    val coin = integer("coin")
}