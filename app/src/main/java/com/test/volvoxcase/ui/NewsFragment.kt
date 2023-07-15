package com.test.volvoxcase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.volvoxcase.R
import com.test.volvoxcase.adapter.NewsAdapter
import com.test.volvoxcase.databinding.FragmentNewsBinding
import com.test.volvoxcase.model.Article
import com.test.volvoxcase.model.NewsResponse
import com.test.volvoxcase.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.test.volvoxcase.model.Result

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsList: MutableList<Article> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsFetch()
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                fragmentNewsFetch()
                swipeRefreshLayout.isRefreshing = false
            }
        }

    }

    fun fragmentNewsFetch() {
        newsViewModel.viewModelScope.launch {

            val newsReponse = newsViewModel.newsResponseState

            newsReponse.collectLatest {
                when (it) {
                    is Result.Success -> {
                        val data = it.data
                        data?.articles?.forEach {
                            newsList.add(it)
                        }
                        newsAdapter = NewsAdapter(newsList) {
                            val bundle = Bundle()
                            bundle.apply {
                                putString("url", newsList[it].url)
                            }
                            findNavController().navigate(
                                R.id.action_newsFragment_to_detailFragment,
                                bundle
                            )
                        }
                        binding.apply {
                            newsRecylerview.layoutManager = LinearLayoutManager(context).apply {
                                isSmoothScrollbarEnabled = true
                            }
                            newsRecylerview.adapter = newsAdapter
                            progressBar.visibility = View.GONE
                        }

                    }
                    is Result.Loading -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                    is Result.Error -> {
                        val message = it.message
                        println("error $message")
                    }
                }
            }
        }
    }

}