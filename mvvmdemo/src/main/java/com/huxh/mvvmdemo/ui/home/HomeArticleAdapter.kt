package com.huxh.mvvmdemo.ui.home

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.huxh.mvvmdemo.R
import com.huxh.mvvmdemo.article.dao.Article
import com.huxh.mvvmdemo.utils.RecyclerViewHolder

class HomeArticleAdapter : PagedListAdapter<Article, RecyclerViewHolder> {

    companion object {
        //  比较的行为
        private val DIFF_ARTICLE: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                // 一般是比较 唯一性的内容， ID
                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem.id == newItem.id
                }

                // 对象本身的比较
                override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }


    constructor() : super(DIFF_ARTICLE) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.Companion.createViewHolder(parent, R.layout.recycler_item_home_article)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}