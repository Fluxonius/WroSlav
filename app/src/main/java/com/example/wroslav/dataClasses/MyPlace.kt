package com.example.wroslav.dataClasses

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

data class MyPlace(
    val pName: String,
    val pImages: ArrayList<String>,
    val pLocation: GeoPoint,
    val pCategory: String,
    val pDesc: String,
    val pOH: String,
    val pRating: Long
) : Serializable
