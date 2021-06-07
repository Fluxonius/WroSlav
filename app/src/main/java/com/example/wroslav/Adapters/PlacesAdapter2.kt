package com.example.wroslav.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wroslav.dataClasses.MyPlace
import com.example.wroslav.R
import com.example.wroslav.Util.Extensions
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso


class PlacesAdapter2(
    private val list: List<MyPlace>,
    private val listener: PlacesAdapter.OnPlacesSelected
) : RecyclerView.Adapter<PlacesAdapter2.PlacesViewHolder>() {
    private var ext = Extensions()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val myPlace: MyPlace = list[position]
        holder.bind(myPlace)
        holder.itemView.setOnClickListener { listener.OnPlacesSelected(myPlace) }
    }

    override fun getItemCount(): Int = list.size
    inner class PlacesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_image_layout_big, parent, false)) {
        private var pTitle: TextView? = null
        private var pImageView: ImageView? = null
        private var pDistance: TextView? = null
        private var pCategory: Chip? = null
        private var pRating: TextView? = null



        init {
            pTitle = itemView.findViewById(R.id.card_pTitleb)
            pImageView = itemView.findViewById(R.id.card_pImageb)
            pDistance = itemView.findViewById(R.id.card_pAddressb)
            pCategory = itemView.findViewById(R.id.card_categoryB)
            pRating = itemView.findViewById(R.id.card_pRatingB)
        }

        fun bind(myPlace: MyPlace) {
            pTitle?.text = myPlace.pName
            Picasso.get().load(myPlace.pImages[0]).into(pImageView!!)
           // pDistance!!.text=ext.getAddress(myPlace.pLocation,context)
            pDistance!!.visibility=View.INVISIBLE
            //pImageView?.background=myPlace.pImage
            // pDistance?.text=ext.getAddress(myPlace.pLocation)
            pCategory?.text = myPlace.pCategory
            pRating?.text = "Rating: " + myPlace.pRating.toString()

        }

    }
}
