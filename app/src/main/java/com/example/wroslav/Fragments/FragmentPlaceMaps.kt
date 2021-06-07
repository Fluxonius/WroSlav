package com.example.wroslav.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wroslav.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "latitude"
private const val ARG_PARAM2 = "longitude"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPlaceMaps.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPlaceMaps : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var latitude: Double? = null
    private var longitude: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble(ARG_PARAM1)
            longitude = it.getDouble(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_place_maps, container, false)
        // needed to get the map to display immediately

        mapFragment = childFragmentManager.findFragmentById(R.id.placeMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        // Add a marker in Sydney and move the camera
        val place = LatLng(latitude!!, longitude!!)
        googleMap!!.addMarker(MarkerOptions().position(place).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
        googleMap.setMinZoomPreference(12f)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentPlaceMaps.
         */
        var mapFragment: SupportMapFragment? = null

        //val TAG: String = MapFragment::class.java.simpleName
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Double, param2: Double) =
            FragmentPlaceMaps().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, param1)
                    putDouble(ARG_PARAM2, param2)
                }
            }
    }
}
