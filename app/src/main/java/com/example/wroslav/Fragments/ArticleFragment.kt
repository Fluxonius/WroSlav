package com.example.wroslav.Fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wroslav.dataClasses.News

import com.example.wroslav.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARTICLE_DATA = "nData"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArticleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var article: News? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aTitle.text = article!!.header
        Picasso.get().load(article!!.image).into(aImageView)
        aBody.text = article!!.body
        aSource.text = article!!.source
        aPlace.text = article!!.location
        aDate.text = article!!.date
        nBack_btn.setOnClickListener { findNavController().popBackStack() }
    }
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ArticleFragmentArgs.fromBundle(requireArguments())
        article = args.article
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_article, container, false)

    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private const val ARTICLE = "nData"

        fun newInstance(news: News): ArticleFragment {
            val args = Bundle()
            args.putSerializable(ARTICLE, news)
            val fragment = ArticleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
