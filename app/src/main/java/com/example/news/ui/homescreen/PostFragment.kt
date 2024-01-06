package com.example.news.ui.homescreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.FragmentPostBinding
import com.example.news.viewModel.PostViewModel
import kotlinx.coroutines.launch
import com.example.news.data.model.NewResponse
import com.example.news.ui.adapter.ViewPagerAdapter
import com.example.news.ui.adapter.NewsAdapter
import com.example.news.ui.util.NetworkManager
import com.example.news.viewModel.BookMarkViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar


class PostFragment : Fragment() , ViewPagerAdapter.RecyclerClickListener{

    private val postViewModel: PostViewModel by lazy {
        ViewModelProvider(this)[PostViewModel::class.java]
    }
    private val bookMarkViewModel: BookMarkViewModel by lazy {
        ViewModelProvider(this)[BookMarkViewModel::class.java]
    }
    private var newsAdapter = NewsAdapter()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: FragmentPostBinding
    private var articles : List<ArticlesItem>? = null
    private var articleExistOrNot:Boolean = false
    private lateinit var builder : AlertDialog
    private var isConnected: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)

        createCustomDialog()
        val networkConnection = NetworkManager(requireContext())
        networkConnection.observe(viewLifecycleOwner){
            if(it){
                if (builder.isShowing){
                    builder.hide()
                }
                sendIntents()
                renderRecNewsVer()
                observe()
                listeners()
                binding.swipeRefreshLayout.setOnRefreshListener{
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            else
            {
                if(!builder.isShowing)
                    builder.show()
            }
        }
    }


    private fun listeners(){
        newsAdapter.setOnItemClickListener{article ->
            checkExistingArticle(article)
            val bundle = Bundle().apply {
                putParcelable("article",article)
                putBoolean("articleSavedOrNot",articleExistOrNot)
            }
            Navigation.findNavController(requireView())
                .navigate(R.id.action_postFragment_to_postDetails,bundle)
        }


        newsAdapter.setOnBookMarkClicked {article ->
            checkExistingArticle(article)
            if(articleExistOrNot){
                Toast.makeText(requireContext(),"Article Already Saved",Toast.LENGTH_LONG).show()
            }else{
                bookMarkViewModel.addBookMark(article)
                Snackbar.make(requireView(),"Article Saved Successfully",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun createCustomDialog() {
        val view = layoutInflater.inflate(R.layout.check_internet_dialog,null)
        builder = AlertDialog.Builder(requireContext(),R.style.CustomAlertDialog)
            .setView(view)
            .setCancelable(false)
            .create()
    }

    override fun onItemClick(articlesItem: ArticlesItem?){
        checkExistingArticle(articlesItem)
        Navigation.findNavController(requireView())
            .navigate(PostFragmentDirections.actionPostFragmentToPostDetails(articlesItem!!,articleExistOrNot))
    }

    override fun onBookMarkClicked(articlesItem: ArticlesItem?) {
       checkExistingArticle(articlesItem)
       if(articleExistOrNot){
           Toast.makeText(requireContext(),"Article Already Saved",Toast.LENGTH_LONG).show()

       }else{
           bookMarkViewModel.addBookMark(articlesItem!!)
           Snackbar.make(requireView(),"Article Saved Successfully",Snackbar.LENGTH_LONG).show()
       }
    }

    private fun sendIntents() {
        lifecycleScope.launch {
            postViewModel.intentChannel.send(PostIntent.GetEnglishNews)
        }
        lifecycleScope.launch {
            postViewModel.intentChannel.send(PostIntent.GetArabicNews)
        }
    }

    private fun renderRecNewsVer() {
        lifecycleScope.launch {
            postViewModel.viewState.collect {
                when (it) {
                    is PostViewState.Error -> it.error
                    PostViewState.Loading -> setShimmer()
                    is PostViewState.EnglishNews -> initVerRec(it.newsResponse)
                    is PostViewState.ArabicNews -> initViewPager(it.newsResponse)
                }
            }
        }
    }

    private fun setShimmer() {
        binding.viewPagerShimmerLayout.startShimmer()
        binding.recViewShimmerLayout.startShimmer()
    }

    private fun stopShimmer(shimmerLayout: ShimmerFrameLayout) {
        shimmerLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
    }

    private fun initVerRec(newsResponse: NewResponse?){
        newsAdapter.differ.submitList(newsResponse?.articles)
        stopShimmer(binding.recViewShimmerLayout)
        binding.postVerRec.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initViewPager(newsResponse: NewResponse){
        viewPagerAdapter = ViewPagerAdapter(newsResponse,this)
        stopShimmer(binding.viewPagerShimmerLayout)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        binding.indicator.setViewPager(binding.viewPager)
    }

    private fun observe(){
        bookMarkViewModel.getBookMarks()?.observe(viewLifecycleOwner, Observer {
            articles = it
        })
    }

    private fun checkExistingArticle(articleItem: ArticlesItem?):Boolean{
        for (i in articles?.indices!!) {
            if (articles!![i].url == articleItem?.url) {
                articleExistOrNot = true
                break
            }
        }
        return articleExistOrNot
    }

}