package com.example.wroslav.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wroslav.Adapters.EventAdapter
import com.example.wroslav.Adapters.EventAdapter2
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.R
import com.example.wroslav.ViewModels.EventsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.chip_eGroup
import kotlinx.android.synthetic.main.fragment_events.eCategoryHeader
import kotlinx.android.synthetic.main.fragment_events.e_recycler_view
import kotlinx.android.synthetic.main.fragment_events.eb_recycler_view
import kotlinx.android.synthetic.main.fragment_events.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_PARAM1 = "events"

class FragmentEvents : Fragment() {
    private var eventsAdapter:EventAdapter2?=null
    private lateinit var listener: EventAdapter.OnEventsSelected


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Done", "EventsFragment")
        return inflater.inflate(R.layout.fragment_events, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventAdapter.OnEventsSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnEventsSelected.")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        val model: EventsViewModel by viewModels()
        val toolbar=requireActivity().findViewById<Toolbar>(R.id.toolbarEventsFragment)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        fun setupView(list:ArrayList<Events>){
            eventsAdapter = EventAdapter2(list, listener)

            e_recycler_view.apply {
                ViewCompat.setNestedScrollingEnabled(this, true)
                layoutManager = LinearLayoutManager(
                    activity, LinearLayoutManager.HORIZONTAL,
                    false
                )
                val pattern="dd.MM.yyyy"
                adapter = EventAdapter(list.sortedBy {LocalDate.parse(it.eDate.split(" ")[0],
                    DateTimeFormatter.ofPattern(pattern))}, listener)
            }
            eb_recycler_view.apply {
                ViewCompat.setNestedScrollingEnabled(this, true)
                layoutManager = LinearLayoutManager(activity)
                adapter = EventAdapter2(list, listener)
            }


            chip_eGroup.apply {
                val  setOfFilters: HashSet<String> = HashSet()
                for (events in list.distinctBy { it.eCategory }) {
                    val chip = Chip(activity)
                    val drawable = ChipDrawable.createFromAttributes(
                        context, null, 0,
                        R.style.Widget_MaterialComponents_Chip_Choice
                    )
                    chip.setChipDrawable(drawable)
                    chip.text = events.eCategory
                    chip.autofillId
//                    chip.setOnCheckedChangeListener { buttonView, isChecked ->
//                        if (isChecked)
//                            setOfFilters.add(buttonView.text as String)
//                        else
//                            setOfFilters.remove(buttonView.text as String)
//                        model.filter(setOfFilters)
//
//                    }
                    chip_eGroup.addView(chip)
                }
            }
            chip_eGroup.setOnCheckedChangeListener { chipGroup, checkedId ->

                val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text
                if(titleOrNull!=null){eb_recycler_view.adapter = EventAdapter2(list.filter{it.eCategory==titleOrNull} as ArrayList<Events>,listener)
                    eCategory2Header.text=titleOrNull
                e_recycler_view.visibility=View.GONE
                eCategoryHeader.visibility=View.GONE} else {
                    eCategory2Header.text=getString(R.string.content_popular)
                eb_recycler_view.adapter = EventAdapter2(list, listener)
                    e_recycler_view.visibility=View.VISIBLE
                    eCategoryHeader.visibility=View.VISIBLE
                }
            }
        }

        model.getEvents()!!.observe(this.viewLifecycleOwner, Observer { newList ->
            setupView(newList)
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                eventsAdapter!!.filter.filter(newText)
                e_recycler_view.visibility=View.GONE
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }
}










