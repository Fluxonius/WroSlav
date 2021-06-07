package com.example.wroslav.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import com.example.wroslav.R
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.photo_entry.view.*


class GalleryAdapter(photosList: ArrayList<String>,name:String) : BaseAdapter() {
    private var photosList = ArrayList<String>()
    private var name:String? = null
    init {
        this.photosList = photosList
        this.name=name
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val photo = this.photosList[position]

        var inflater =
            parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.photo_entry, null)
        Picasso.get().load(photo).into(view.photoView)




        view.setOnClickListener {
            StfalconImageViewer.Builder(parent.context, photosList) { view, image ->
                Picasso.get().load(image).into(view)
            }.withStartPosition(position).withOverlayView(setupTextView(name!!,parent.context)).show()
        }
        return view
    }
    private fun setupTextView(string: String, context: Context):TextView{
        val textView= TextView(context)
        textView.apply {
            gravity= CENTER_HORIZONTAL
            text=string
            setPadding(0,150,0,0)
        }

        return textView
    }

    override fun getItem(position: Int): Any = photosList[position]


    override fun getItemId(position: Int): Long = position.toLong()


    override fun getCount(): Int = photosList.size

}