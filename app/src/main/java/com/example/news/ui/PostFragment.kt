package com.example.news.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.news.ui.adapter.PostVerAdapter
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.facebook.shimmer.ShimmerFrameLayout


class PostFragment : Fragment() ,PostVerAdapter.RecyclerViewListener {
    val postViewModel : PostViewModel by lazy {
        ViewModelProvider(this)[PostViewModel::class.java]
    }
    lateinit var postVerAdapter: PostVerAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter

    lateinit var binding : FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)


        sendIntents()
        renderRecNewsVer()

        binding.swipeRefreshLayout.setOnRefreshListener {
            postVerAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
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


    private fun renderRecNewsVer(){
        lifecycleScope.launch {
            postViewModel.viewState.collect{
                when(it){
                    is PostViewState.Error -> it.error
                    PostViewState.Loading -> setShimmer()
                    is PostViewState.EnglishNews ->initVerRec(it.newsResponse)
                    is PostViewState.ArabicNews -> initViewPager(it.newsResponse)
                }
            }
        }
    }

    private fun setShimmer(){
        binding.viewPagerShimmerLayout.startShimmer()
        binding.recViewShimmerLayout.startShimmer()
    }

    private fun stopShimmer(shimmerLayout:ShimmerFrameLayout){
        shimmerLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
    }

    private fun initVerRec(newsResponse:NewResponse){

     stopShimmer(binding.recViewShimmerLayout)

     postVerAdapter = PostVerAdapter(newsResponse,this)
     binding.postVerRec.apply {
         adapter = postVerAdapter
         layoutManager = LinearLayoutManager(requireContext())
      }
    }

    private fun initViewPager(newsResponse: NewResponse){

        stopShimmer(binding.viewPagerShimmerLayout)

        viewPagerAdapter = ViewPagerAdapter(newsResponse)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        binding.indicator.setViewPager(binding.viewPager)
    }

    override fun onItemClicked(article: ArticlesItem?) {
        Navigation.findNavController(requireView())
            .navigate(PostFragmentDirections.actionPostFragmentToPostDetails(article!!))
    }

}