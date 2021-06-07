package com.example.wroslav.Util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.firebase.firestore.GeoPoint
import java.io.IOException
import java.util.*


class Extensions {
    fun getLocation(strAddress: String, geocoder: Geocoder): GeoPoint? {

        var address = listOf<Address>()
        val p1: GeoPoint

        address = geocoder.getFromLocationName(strAddress, 5)
        if (address == null) {
            return null
        }
        val location = address[0]
        location.latitude
        location.longitude
        p1 = GeoPoint((location.latitude), location.longitude)
        return p1


    }

    fun getAddress(geoPoint: GeoPoint, context: Context?): String {
        val geoCoder = Geocoder(
            context, Locale.getDefault()
        )

        val addresses =
            geoCoder.getFromLocation(
                geoPoint.latitude,
                geoPoint.longitude, 1
            )
        var add = ""
        if (addresses.size > 0) {
            add = addresses[0].getAddressLine(0)
        }
        return add
    }


}