package com.example.news.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.FragmentPostDetailsBinding
import com.example.news.ui.util.TimeAndDate
import com.example.news.viewModel.BookMarkViewModel
import com.google.android.material.snackbar.Snackbar


class PostDetailsFragment : Fragment() {

    private var _binding : FragmentPostDetailsBinding?=null
    private val binding get() = _binding!!

    private var articlesItem:ArticlesItem?= null
    private var articleExistingOrNot : Boolean= false

    private val args : PostDetailsFragmentArgs by navArgs()

    private val bookMarkViewModel : BookMarkViewModel by lazy {
        ViewModelProvider(this)[BookMarkViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    //   articlesItem = PostDetailsFragmentArgs.fromBundle(requireArguments()).article
        articlesItem = args.article
        articleExistingOrNot = args.articleSavedOrNot
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


        binding.progressPar.visibility = View.VISIBLE

        setArticleInfo()
        setWebView()


        binding.back.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_postDetails_to_postFragment)
        }

        binding.bookMark.setOnClickListener {
            if (articleExistingOrNot){
                Toast.makeText(requireContext(),"Article Already Saved", Toast.LENGTH_LONG).show()
            }else{
                bookMarkViewModel.addBookMark(articlesItem!!)
                Snackbar.make(requireView(), "Article Saved Successfully", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setWebView() {

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(articlesItem?.url!!)
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            if (isShown)
                binding.progressPar.visibility = View.INVISIBLE
        }
    }

    private fun setArticleInfo(){
        binding.apply {
            newsDate.text = TimeAndDate.dateFormat(articlesItem?.publishedAt)
            if (articlesItem?.urlToImage == null) {
                newsImage.load(R.drawable.not_found_image) {
                    crossfade(true)
                    crossfade(1000)
                }

                newsDate.setTextColor(resources.getColor(R.color.black))
                numOfComments.setTextColor(resources.getColor(R.color.black))
                numOfSeen.setTextColor(resources.getColor(R.color.black))
                commentsIcon.setColorFilter(resources.getColor(R.color.black))
                eyesIcon.setColorFilter(resources.getColor(R.color.black))
                back.setColorFilter(resources.getColor(R.color.black))
                share.setColorFilter(resources.getColor(R.color.black))

//                val unCheckedColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.black))
//                val CheckedColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.black))
//
//                bookMark.buttonTintList = unCheckedColor



            } else {
                newsImage.load(articlesItem?.urlToImage) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
            newsTitle.text = articlesItem?.title
            numOfComments.text = "685"
            numOfSeen.text = "1532"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}