package com.example.wroslav.ViewModels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wroslav.dataClasses.MyPlace
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlacesViewModel : ViewModel() {
    val db = Firebase.firestore

    private var places: MutableLiveData<List<MyPlace>>? =
        null


    fun getPlaces(): LiveData<List<MyPlace>>? {
        if (places == null) {
            places =
                MutableLiveData()
            loadPlaces()
        }
        return places
    }

    private fun loadPlaces() {
        val placesList: ArrayList<MyPlace> = ArrayList()
        db.collection("Place").get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val pTitle: String? = doc.getString("pName")
                    val pImages: ArrayList<String>? = doc.get("pImages") as ArrayList<String>
                    val pLocation: GeoPoint? = doc.getGeoPoint("pLocation")
                    val pCategory: String? = doc.getString("pCategory")
                    val pDesc: String? = doc.getString("pDesc")
                    val pOH: String? = doc.getString("pOH")
                    val pRating: Long? = doc.getLong("pRating")

                    placesList.add(
                        MyPlace(
                            pTitle!!,
                            pImages!!,
                            pLocation!!,
                            pCategory!!,
                            pDesc!!,
                            pOH!!,
                            pRating!!
                        )
                    )

                }
                places!!.value = placesList
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}