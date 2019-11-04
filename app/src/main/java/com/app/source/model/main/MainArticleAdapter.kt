package com.app.source.model.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.framework.R
import com.app.framework.common.adapter.RecyclerViewHolder
import com.app.source.response.Data
import kotlinx.android.synthetic.main.recycler_item_main.*

class MainArticleAdapter(private val list: List<Data>) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.Companion.createViewHolder(parent, R.layout.recycler_item_main)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = list[position]
        holder.tvTitle.text = data.title
    }

}
