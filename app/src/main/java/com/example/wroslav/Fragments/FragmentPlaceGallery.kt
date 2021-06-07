package com.example.wroslav.Fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wroslav.Adapters.GalleryAdapter

import com.example.wroslav.R
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.fragment_place_gallery.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "imagesOfPlace"
private const val ARG_PARAM2 = "nameOfPlace"
/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPlaceGallery.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPlaceGallery() : Fragment() {
    private var images: ArrayList<String>? = null
    private var name: String? = null
    private var adapter: GalleryAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            images = it.getStringArrayList(ARG_PARAM1)
            name=it.getString(ARG_PARAM2)
            // param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        StfalconImageViewer.Builder(context, images!!) { view, image ->
//            Picasso.get().load(image).into(view)
//        }.show()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GalleryAdapter(images!!,name!!)
        gridView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentPlaceGallery.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ArrayList<String>,param2:String) =
            FragmentPlaceGallery().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, param1)
                    putString(ARG_PARAM2,param2)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }
}
