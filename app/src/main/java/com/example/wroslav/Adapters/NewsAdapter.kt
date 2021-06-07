package com.example.wroslav.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wroslav.dataClasses.News
import com.example.wroslav.databinding.CardLayoutBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val list: ArrayList<News>, private val listener: OnNewsSelected) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(), Filterable {
    private val listFull: List<News> = ArrayList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
        val cardLayoutBinding = CardLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(cardLayoutBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news: News = list[position]
        holder.cardLayoutBinding!!.article = news
        holder.itemView.setOnClickListener { listener.OnNewsSelected(news) }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(cardLayoutBinding: CardLayoutBinding) :
        RecyclerView.ViewHolder(cardLayoutBinding.root) {
        var cardLayoutBinding: CardLayoutBinding? = null

        init {
            this.cardLayoutBinding = cardLayoutBinding
        }
    }


    interface OnNewsSelected {
        fun OnNewsSelected(news: News)
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    val listFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<News> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(listFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (item in listFull) {
                    if (item.header.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val res = FilterResults()
            res.values = filteredList
            return res
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list.clear()
            list.addAll(results!!.values as List<News>)
            notifyDataSetChanged()
        }

    }

}
