package com.example.wroslav.dataClasses

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

class Events(
    val eTitle: String,
    val eImage: String,
    val eLocation: GeoPoint,
    val eBody: String,
    val eCategory: String,
    val eDate: String,
    val eTime: String,
    val eRating: Int
) : Serializable
