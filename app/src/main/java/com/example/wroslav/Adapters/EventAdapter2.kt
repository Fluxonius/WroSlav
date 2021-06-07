package com.example.wroslav.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.R
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class EventAdapter2(
    private val list: ArrayList<Events>,
    private val listener: EventAdapter.OnEventsSelected
) : RecyclerView.Adapter<EventAdapter2.EventViewHolder>(),Filterable {
    private val listFull:List<Events> = ArrayList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val events: Events = list!![position]
        holder.bind(events)
        holder.itemView.setOnClickListener { listener.onEventsSelected(events) }
    }

    override fun getItemCount(): Int = list!!.size
    inner class EventViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_event_big, parent, false)) {
        private var eTitle: TextView? = null
        private var eImage: ImageView? = null

        // private var eDate: TextView?= null
        private var eCategory: TextView? = null
        private var eTime: TextView? = null
        private var eMonth: TextView? = null
        private var eNrDay: TextView? = null

        init {
            eTitle = itemView.findViewById(R.id.card_eTitleb)
            eImage = itemView.findViewById(R.id.card_eImageb)
            // eDate= itemView.findViewById(R.id.eDate)
            eCategory = itemView.findViewById(R.id.card_eCategoryb)
            eTime = itemView.findViewById(R.id.card_eTimeb)
            eMonth = itemView.findViewById(R.id.Monthb)
            eNrDay = itemView.findViewById(R.id.nrMonthb)
        }

        fun getMonth(month: Int): String {
            return DateFormatSymbols().months[month - 1]
        }

        fun bind(events: Events) {
            eTitle?.text = events.eTitle
            Picasso.get().load(events.eImage).into(eImage)
            eNrDay?.text = events.eDate.split(".")[0]
            eMonth?.text = getMonth(events.eDate.split(".")[1].toInt()).substring(0, 3)
            // eDate?.text=places.pLocation
            eCategory?.text = events.eCategory
            eTime?.text = events.eTime

        }
    }

    override fun getFilter(): Filter {
        return listFilter
    }
    val listFilter:Filter=object :Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList:ArrayList<Events> = ArrayList()
            if(constraint==null|| constraint.isEmpty()){
                filteredList.addAll(listFull)
            }else{
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (item in listFull){
                    if(item.eTitle.toLowerCase(Locale.ROOT).contains(filterPattern)){
                        filteredList.add(item)
                    }
                }
            }
            val res=FilterResults()
            res.values=filteredList
            return res
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list.clear()
            list.addAll(results!!.values as List<Events>)
            notifyDataSetChanged()
        }

    }

}
