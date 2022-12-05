package com.ko610.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Setting : Table("Setting") {
    val userId = reference("User_id", User)
    val nickName = varchar("nickname", 20)
    val icon = varchar("icon", 32)
    val email = varchar("email", 20).nullable()
    val school = varchar("school", 13)
    val range = integer("range").default(10)
}