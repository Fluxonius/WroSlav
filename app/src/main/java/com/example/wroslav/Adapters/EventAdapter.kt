package com.example.wroslav.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.R
import com.squareup.picasso.Picasso
import java.text.DateFormatSymbols


class EventAdapter(private val list: List<Events>?, private val listener: OnEventsSelected) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_event_small, parent, false)) {
        private var eTitle: TextView? = null
        private var eImage: ImageView? = null

        // private var eDate: TextView?= null
        private var eCategory: TextView? = null
        private var eTime: TextView? = null
        private var eMonth: TextView? = null
        private var eNrDay: TextView? = null


        init {
            eTitle = itemView.findViewById(R.id.card_eTitle)
            eImage = itemView.findViewById(R.id.card_eImage)
            // eDate= itemView.findViewById(R.id.eDate)
            eCategory = itemView.findViewById(R.id.card_eCategory)
            eTime = itemView.findViewById(R.id.card_eTime)
            eMonth = itemView.findViewById(R.id.Month)
            eNrDay = itemView.findViewById(R.id.nrMonth)
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

    interface OnEventsSelected {
        fun onEventsSelected(events: Events)
    }

}


