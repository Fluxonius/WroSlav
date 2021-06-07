package com.example.wroslav.Fragments


//import com.example.wroslav.dataClasses.MyPlace
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wroslav.Adapters.PlacesAdapter
import com.example.wroslav.Adapters.PlacesAdapter2
import com.example.wroslav.dataClasses.MyPlace
import com.example.wroslav.R
import com.example.wroslav.ViewModels.PlacesViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_places.*
import kotlinx.android.synthetic.main.fragment_places.categoryHeader
import kotlinx.android.synthetic.main.fragment_places.chip_group
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class FragmentPlaces : Fragment() {

    private lateinit var listener: PlacesAdapter.OnPlacesSelected

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Done", "PlacesFragment")
        return inflater.inflate(R.layout.fragment_places, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PlacesAdapter.OnPlacesSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnPlacesSelected.")
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: PlacesViewModel by viewModels()
        fun setupView(list:ArrayList<MyPlace>){

        pb_recycler_view.apply {
            ViewCompat.setNestedScrollingEnabled(this, true)
            layoutManager = LinearLayoutManager(activity)
            adapter = PlacesAdapter2(list.sortedByDescending { it.pRating }, listener)
        }
        p_recycler_view.apply {
            ViewCompat.setNestedScrollingEnabled(this, true)
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = PlacesAdapter(list, listener)
        }

        chip_group.apply {
            for (places in list.distinctBy { it.pCategory }) {

                val chip = Chip(activity)
                val drawable = ChipDrawable.createFromAttributes(
                    context, null, 0,
                    R.style.Widget_MaterialComponents_Chip_Choice
                )
                chip.setChipDrawable(drawable)
                chip.text = places.pCategory
                chip.autofillId

                chip_group.addView(chip)
            }
        }
        chip_group.setOnCheckedChangeListener { chipGroup, checkedId ->

            val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text
            if(titleOrNull!=null){pb_recycler_view.adapter = PlacesAdapter2(list.filter{it.pCategory==titleOrNull} as ArrayList<MyPlace>,listener)
                category2Header.text=titleOrNull
            p_recycler_view.visibility=View.GONE
            categoryHeader.visibility=View.GONE
            } else {
                category2Header.text=getString(R.string.content_popular)
                pb_recycler_view.adapter = PlacesAdapter2(list, listener)
                p_recycler_view.visibility=View.VISIBLE
                categoryHeader.visibility=View.VISIBLE
            }
        }}

        model.getPlaces()!!.observe(this.viewLifecycleOwner, Observer { newList ->
            setupView(newList as ArrayList<MyPlace>)

        })


    }

}







