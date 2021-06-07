package com.example.wroslav.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wroslav.Adapters.TabPagerAdapter
import com.example.wroslav.R
import com.example.wroslav.Util.Extensions
import com.example.wroslav.dataClasses.MyPlace
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_place_extended.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val PLACE = "pData"


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPlaceExtended.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPlaceExtended : Fragment() {
    // TODO: Rename and change types of parameters
    private var myPlace: MyPlace? = null
    private var ext:Extensions= Extensions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args=FragmentPlaceExtendedArgs.fromBundle(requireArguments())
        myPlace=args.place
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    configureTabLayout()
setupFields()
//        val toolbar=requireActivity().findViewById<MaterialToolbar>(R.id.toolbarPlace)
//        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)




    }
    private fun setupFields(){

        Picasso.get().load(myPlace!!.pImages[0]).into(peImageView)
        peCategory.text=myPlace!!.pCategory
        peTitle.text=myPlace!!.pName
        peAdress.text=ext.getAddress(myPlace!!.pLocation,this.requireContext())
        peTime.text=myPlace!!.pOH
        peBack_btn.setOnClickListener { findNavController().popBackStack()}


    }
    private fun configureTabLayout() {

        val gallery =
            FragmentPlaceGallery.newInstance(myPlace!!.pImages,myPlace!!.pName)
        val description =
            FragmentPlaceDescription.newInstance(myPlace!!.pDesc)
        val maps =
            FragmentPlaceMaps.newInstance(myPlace!!.pLocation.latitude,myPlace!!.pLocation.longitude)
        val adapter = TabPagerAdapter(this)
        adapter.addFragment(description)
        adapter.addFragment(gallery)
        adapter.addFragment(maps)
        peFragment_container.adapter = adapter
        peFragment_container.isUserInputEnabled = false


        TabLayoutMediator(tab_layout, peFragment_container) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.content_description)
                1 -> resources.getString(R.string.content_galery)
                2 -> resources.getString(R.string.content_maps)
                else -> ""
            }
        }.attach()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_extended, container, false)
    }

//    companion object {
//        private const val PLACE = "pData"
//
//        fun newInstance(myPlace: MyPlace): FragmentPlaceExtended {
//            val args = Bundle()
//            args.putSerializable(PLACE, myPlace)
//            val fragment = FragmentPlaceExtended()
//            fragment.arguments = args
//            return fragment
//        }
//    }
}
