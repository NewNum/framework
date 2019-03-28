/*
 * Copyright © Hu Xinhui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huxh.framework.common.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author huxh
 * @date 2019/3/27.
 */
abstract class RecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder>() {

    var listener: RecyclerViewHolder.OnItemClickListener<T>? = null


    fun setOnItemClickListener(listener: RecyclerViewHolder.OnItemClickListener<T>) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder.createViewHolder(parent.context, parent, setItemLayout())

    @LayoutRes
    abstract fun setItemLayout(): Int


}