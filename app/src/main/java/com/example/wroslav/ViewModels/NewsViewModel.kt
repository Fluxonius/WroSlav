package com.example.wroslav.ViewModels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wroslav.dataClasses.News
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NewsViewModel : ViewModel() {

    val db = Firebase.firestore

    private var news: MutableLiveData<List<News>>? =
        null

    fun getNews(): LiveData<List<News>>? {
        if (news == null) {
            news =
                MutableLiveData()
            loadNews()
        }
        return news
    }

    private fun loadNews() {
        val newsList: ArrayList<News> = ArrayList()
        db.collection("News").get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val tytul = doc.getString("Tytul")
                    val imgUrl = doc.getString("ImageURL")
                    val location = doc.getString("Location")
                    val body = doc.getString("Body")
                    val source = doc.getString("Source")
                    val date = doc.getString("Data")
                    newsList.add(News(tytul!!, imgUrl!!, location!!, body!!, source!!, date!!))

                }
                news!!.value = newsList
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }


    }
}
