package com.example.news.ui.searchscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.NewResponse
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.ui.adapter.NewsAdapter
import com.example.news.ui.homescreen.PostFragment
import com.example.news.ui.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.news.viewModel.BookMarkViewModel
import com.example.news.viewModel.SearchViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment()  {


    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    private val bookMarkViewModel: BookMarkViewModel by lazy {
        ViewModelProvider(this)[BookMarkViewModel::class.java]
    }
    private lateinit var binding: FragmentSearchBinding
    private var searchedAdapter  = NewsAdapter()
    private var articles : List<ArticlesItem>? = null
    private var articleExistOrNot:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)


        var job: Job? = null
        binding.searchEditText.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        lifecycleScope.launch {
                            searchViewModel.intentChannel.send(
                                SearchIntent.SearchForNews(
                                    editable.toString()
                                )
                            )
                        }

                    }


                }

            }
        }

        searchedAdapter.setOnItemClickListener {article ->
            checkExistingArticle(article)
            val bundle = Bundle().apply {
                putParcelable("article",article)
                putBoolean("articleSavedOrNot",articleExistOrNot)
            }
            Navigation.findNavController(requireView())
                .navigate(R.id.action_searchFragment_to_postDetails,bundle)
        }


        searchedAdapter.setOnBookMarkClicked {article ->
            checkExistingArticle(article)
            if(articleExistOrNot){
                Toast.makeText(requireContext(),"Article Already Saved", Toast.LENGTH_LONG).show()
            }else{
                bookMarkViewModel.addBookMark(article)
                Snackbar.make(requireView(),"Article Saved Successfully", Snackbar.LENGTH_LONG).show()
            }
        }
        observe()
        renderRec()

    }



    private fun renderRec() {
        lifecycleScope.launch {
            searchViewModel.viewState.collect {
                when (it) {
                    is SearchViewState.Error -> it.error
                    SearchViewState.Loading -> setShimmer()
                    is SearchViewState.SpecificNews -> initRec(it.newsResponse)
                }
            }
        }
    }

    private fun initRec(newsResponse: NewResponse?) {
        searchedAdapter.differ.submitList(newsResponse?.articles)
        stopShimmer(binding.recViewShimmerLayout)
        binding.searchedPostsRec.apply {
            adapter = searchedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        Log.d("eeeeee", "" + newsResponse?.articles?.get(0)?.title)
    }

    private fun setShimmer() {
        binding.recViewShimmerLayout.startShimmer()
    }

    private fun stopShimmer(shimmerLayout: ShimmerFrameLayout) {
        shimmerLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
    }

    private fun observe(){
        bookMarkViewModel.getBookMarks()?.observe(viewLifecycleOwner, Observer {
            articles = it
        })
    }

    private fun checkExistingArticle(articleItem: ArticlesItem?):Boolean{
        for (i in articles?.indices!!){
            if(articles!![i].url == articleItem?.url){
                articleExistOrNot = true
                break
            }
        }
        return articleExistOrNot
    }


}