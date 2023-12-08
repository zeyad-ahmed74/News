package com.example.news.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.NewResponse
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.ui.adapter.PostVerAdapter
import com.example.news.viewModel.SearchViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.launch

class SearchFragment : Fragment() , PostVerAdapter.RecyclerViewListener{
    val searchViewModel : SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    lateinit var binding:FragmentSearchBinding
    lateinit var Searchedadapter: PostVerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        // sendIntent
        binding.searchInputLayout.setEndIconOnClickListener {
            if(binding.searchInputLayout.editText?.text?.equals("") == true){
                Toast.makeText(requireContext(),"Please fill the Input", Toast.LENGTH_LONG).show()
            }else{
                  lifecycleScope.launch {
                      searchViewModel.intentChannel.send(SearchIntent.SearchForNews(
                          binding.searchInputLayout.editText?.text.toString()))
                  }
            }
        }
       renderRec()
    }

    private fun renderRec() {
        lifecycleScope.launch {
            searchViewModel.viewState.collect{
                when(it){
                    is SearchViewState.Error -> it.error
                    SearchViewState.Loading -> setShimmer()
                    is SearchViewState.SpecificNews -> initRec(it.newsResponse)
                }
            }
        }
    }

    private fun initRec(newsResponse: NewResponse?) {
      stopShimmer(binding.recViewShimmerLayout)
      Searchedadapter = PostVerAdapter(newsResponse,this)
      binding.searchedPostsRec.apply {
          adapter = Searchedadapter
          layoutManager = LinearLayoutManager(requireContext())
      }
        Log.d("eeeeee",""+ newsResponse?.articles?.get(0)?.title)
    }

    override fun onItemClicked(article: ArticlesItem?){
        Navigation.findNavController(requireView())
            .navigate(SearchFragmentDirections.actionSearchFragmentToPostDetails(article!!))
    }

    private fun setShimmer(){
        binding.recViewShimmerLayout.startShimmer()
    }

    private fun stopShimmer(shimmerLayout: ShimmerFrameLayout){
        shimmerLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
    }
}
