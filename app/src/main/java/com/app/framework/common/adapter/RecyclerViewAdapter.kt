

package com.app.framework.common.adapter

import androidx.annotation.LayoutRes
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewAdapter<T> : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewHolder>() {

    var listener: RecyclerViewHolder.OnItemClickListener<T>? = null


    fun setOnItemClickListener(listener: RecyclerViewHolder.OnItemClickListener<T>) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder.createViewHolder(parent.context, parent, setItemLayout())

    @LayoutRes
    abstract fun setItemLayout(): Int


}