package com.example.news.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.FragmentPostDetailsBinding


class PostDetailsFragment : Fragment() {

    private var _binding : FragmentPostDetailsBinding?=null
    private val binding get() = _binding!!

    private var articlesItem:ArticlesItem?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       articlesItem = PostDetailsFragmentArgs.fromBundle(requireArguments()).article
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostDetailsBinding.bind(view)
        binding.apply {
            newsDate.text = MyUtils.dateFormat(articlesItem?.publishedAt)
            newsImage.load(articlesItem?.urlToImage)
            newsTitle.text = articlesItem?.title
            newsDec.text = articlesItem?.description.toString()
            numOfComments.text = "685"
            numOfSeen.text = "1532"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}