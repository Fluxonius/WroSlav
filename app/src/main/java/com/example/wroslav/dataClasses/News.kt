package com.example.wroslav.dataClasses

import java.io.Serializable


data class News(
    val header: String,
    val image: String,
    val location: String,
    val body: String,
    val source: String,
    val date: String
) : Serializable
