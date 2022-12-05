package com.ko610

import com.ko610.dao.DatabaseFactory
import com.ko610.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = run {
    DatabaseFactory.init()
    EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
