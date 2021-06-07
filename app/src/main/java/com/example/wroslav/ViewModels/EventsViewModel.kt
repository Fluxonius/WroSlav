package com.example.wroslav.ViewModels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.Util.Extensions
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EventsViewModel : ViewModel() {
    private var ext = Extensions()
    val db = Firebase.firestore

    private var eventsAll: MutableLiveData<ArrayList<Events>>? =
        null
    private var eventsDisplayed: MutableLiveData<ArrayList<Events>>? =
        null

    fun getEvents(): LiveData<ArrayList<Events>>? {
        if (eventsAll == null) {
            eventsAll =
                MutableLiveData()
            eventsDisplayed = MutableLiveData()
            loadEvents()
            //filter(string)
        }
        return eventsAll
    }

    private fun loadEvents() {
        val eventsList: ArrayList<Events> = ArrayList()
        db.collection("Events").get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val eTitle: String? = doc.getString("eTitle")
                    val eImage: String? = doc.getString("eImage")
                    val eLocation: GeoPoint? = doc.getGeoPoint("eLocation")
                    val eBody: String? = doc.getString("eBody")
                    val eCategory: String? = doc.getString("eCategory")
                    val eDate: String? = doc.getString("eDate")
                    val eTime: String? = doc.getString("eTime")
                    val eRating: Long? = doc.getLong("eRating")

                    eventsList.add(
                        Events(
                            eTitle!!,
                            eImage!!,
                            eLocation!!,
                            eBody!!,
                            eCategory!!,
                            eDate!!,
                            eTime!!,
                            eRating!!.toInt()
                        )
                    )

                }
                eventsAll!!.value = eventsList
                eventsDisplayed!!.value = eventsList
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    fun filter(stringFilter: String) {
        val list:ArrayList<Events> = eventsAll!!.value!!
        list.filter { it.eCategory==stringFilter }
        eventsDisplayed!!.value=list

    }

}