package com.example.news.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.SnapHelper
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.FragmentBookMarkBinding
import com.example.news.ui.adapter.NewsAdapter
import com.example.news.viewModel.BookMarkViewModel
import com.google.android.material.snackbar.Snackbar


class BookMarkFragment : Fragment() {

    val bookMarkViewModel : BookMarkViewModel by lazy {
        ViewModelProvider(this)[BookMarkViewModel::class.java]
    }

    private lateinit var binding : FragmentBookMarkBinding
    private var bookMarkAdapter = NewsAdapter(true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_mark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookMarkBinding.bind(view)


        bookMarkViewModel.getBookMarks()?.observe(viewLifecycleOwner,Observer{ articles ->
            initRec(articles)
        })

        val onItemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = bookMarkAdapter.differ.currentList[position]
                bookMarkViewModel.deleteBookMark(article)
                Snackbar.make(view,"Article has deleted successfully",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        bookMarkViewModel.addBookMark(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(onItemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.bookMarkRec)
        }

        bookMarkAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putParcelable("article",article)
                putBoolean("articleSavedOrNot",true)
            }
            Navigation.findNavController(requireView())
                .navigate(R.id.action_bookMarkFragment_to_postDetails,bundle)
        }

    }

    private fun initRec(bookMarkNews: List<ArticlesItem>?) {
        bookMarkAdapter.differ.submitList(bookMarkNews)
        binding.bookMarkRec.apply {
          adapter = bookMarkAdapter
          layoutManager = LinearLayoutManager(requireContext())
        }
    }


}