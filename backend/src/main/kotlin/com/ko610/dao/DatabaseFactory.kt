package com.ko610.dao

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val user = "root"
        val pass = "root"
        val url = "jdbc:mysql://localhost:3306/Crrow?useSSL=false"
        val driver = "com.mysql.jdbc.Driver"
        Database.connect(url = url, driver = driver, user = user, password = pass)
    }
}