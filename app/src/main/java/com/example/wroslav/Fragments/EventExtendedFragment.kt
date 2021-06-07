package com.example.wroslav.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wroslav.Adapters.TabPagerAdapter
import com.example.wroslav.dataClasses.Events

import com.example.wroslav.R
import com.example.wroslav.Util.Extensions
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event_extended.*
import kotlinx.android.synthetic.main.fragment_place_extended.*
import java.text.DateFormatSymbols

class EventExtendedFragment : Fragment() {
    private var event: Events? = null
    private var ext: Extensions = Extensions()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: EventExtendedFragmentArgs =
            EventExtendedFragmentArgs.fromBundle(requireArguments())
        event = args.event
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_extended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureTabLayout()
        setupFields()
    }

    fun getMonth(month: Int): String {
        return DateFormatSymbols().months[month - 1]
    }

    private fun setupFields() {
        eeAdress.text = ext.getAddress(event!!.eLocation, this.context)
        eeTitle.text = event!!.eTitle
        eeTime.text = event!!.eTime
        eeCategory.text = event!!.eCategory
        eeNrMonth.text = event!!.eDate.split(".")[0]
        eeMonth.text = getMonth(event!!.eDate.split(".")[1].toInt()).substring(0, 3)
        Picasso.get().load(event!!.eImage).into(eeImageView)
        eeBack_btn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun configureTabLayout() {
        val description =
            FragmentPlaceDescription.newInstance(event!!.eBody)
        val maps =
            FragmentPlaceMaps.newInstance(event!!.eLocation.latitude, event!!.eLocation.longitude)
        val adapter = TabPagerAdapter(this)
        adapter.addFragment(description)
        adapter.addFragment(maps)
        eeFragment_container.adapter = adapter
        eeFragment_container.isUserInputEnabled = false

        TabLayoutMediator(event_tab_layout, eeFragment_container) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.content_description)
                1 -> resources.getString(R.string.content_maps)
                else -> ""
            }
        }.attach()
    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment EventExtendedFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: Events) =
//            EventExtendedFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(EVENT, param1)
//
//                }
//            }
//    }
}
