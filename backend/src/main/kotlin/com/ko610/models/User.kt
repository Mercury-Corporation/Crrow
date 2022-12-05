package com.ko610.models

data class User(
    val id: Int,
    val name: String,
    val birthday: String,
    val sex: Int,
    val introduction: String,
    val ban_level: Int,
    val type: Int,
    val coin: Int
)