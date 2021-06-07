package com.example.wroslav.Fragments


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wroslav.Adapters.NewsAdapter
import com.example.wroslav.MainActivity
import com.example.wroslav.R
import com.example.wroslav.ViewModels.NewsViewModel
import com.example.wroslav.dataClasses.News
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_news.*


/**
 * A simple [Fragment] subclass.
 */

class FragmentNews : Fragment() {
    private var newsAdapter:NewsAdapter?=null
    private lateinit var listener: NewsAdapter.OnNewsSelected
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is NewsAdapter.OnNewsSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnNewsSelected.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: NewsViewModel by viewModels()

        val toolbar=requireActivity().findViewById<Toolbar>(R.id.toolbarNewsFragment)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        fun setupView(list:ArrayList<News>){
            newsAdapter=NewsAdapter(list, listener)


            n_recycler_view.apply {
                ViewCompat.setNestedScrollingEnabled(this, true)
                layoutManager = LinearLayoutManager(activity)
                adapter = newsAdapter
            }

            chip_group.apply {
                for (news in list.distinctBy { it.location }) {
                    val chip = Chip(activity)
                    val drawable = ChipDrawable.createFromAttributes(
                        context, null, 0,
                        R.style.Widget_MaterialComponents_Chip_Choice
                    )
                    chip.setChipDrawable(drawable)
                    chip.text = news.location
                    chip.autofillId
                    chip_group.addView(chip)
                }
            }
            chip_group.setOnCheckedChangeListener { chipGroup, checkedId ->

                val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text
                if(titleOrNull==null){n_recycler_view.adapter = NewsAdapter(list,listener)
                } else
                    n_recycler_view.adapter = NewsAdapter(list.filter{it.location==titleOrNull} as ArrayList<News>, listener)
            }

        }
        model.getNews()!!.observe(this.viewLifecycleOwner, Observer { newList ->
            setupView(newList as ArrayList<News>)
        })

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newsAdapter!!.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }



}






