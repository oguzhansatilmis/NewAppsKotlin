package com.test.volvoxcase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.test.volvoxcase.R
import com.test.volvoxcase.databinding.NewsitemBinding
import com.test.volvoxcase.model.Article
import com.test.volvoxcase.ui.DetailFragment
import com.test.volvoxcase.ui.NewsFragment

class NewsAdapter(private val newsList: List<Article>, private val onClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: NewsitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.binding.apply {
            newsTitle.text = newsList[position].title
            newsDescription.text = newsList[position].description

            if (newsList[position].urlToImage.isNullOrEmpty()) {
                newsImage.setBackgroundResource(R.drawable.news_item_rectangle)

            } else {
                Glide.with(holder.itemView)
                    .load(newsList[position].urlToImage)
                    .into(newsImage)

            }

            itemCl.setOnClickListener {
                onClickListener(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}