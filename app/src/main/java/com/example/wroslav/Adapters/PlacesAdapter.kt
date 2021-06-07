package com.example.wroslav.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.wroslav.dataClasses.MyPlace

import com.example.wroslav.R
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso


class PlacesAdapter(private val list: List<MyPlace>, private val listener: OnPlacesSelected) :
    RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: MyPlace = list[position]
        holder.bind(place)
        holder.itemView.setOnClickListener { listener.OnPlacesSelected(place) }
    }

    override fun getItemCount(): Int = list.size
    inner class PlacesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_image_layout, parent, false)) {
        private var pTitle: TextView? = null
        private var pImageView: ImageView? = null
        private var pDistance: TextView? = null
        private var pCategory: Chip? = null


        init {
            pTitle = itemView.findViewById(R.id.card_title)
            pImageView = itemView.findViewById(R.id.pImageView)
           // pDistance = itemView.findViewById(R.id.card_distance)
            pCategory = itemView.findViewById(R.id.card_category)
        }

        fun bind(place: MyPlace) {
            pTitle?.text = place.pName
            Picasso.get().load(place.pImages[0]).into(pImageView)
            //pImageView?.background = place.pImage
            //pDistance?.text=place.pLocation
            pCategory?.text = place.pCategory

        }

    }

    interface OnPlacesSelected {
        fun OnPlacesSelected(myPlace: MyPlace)
    }

}

