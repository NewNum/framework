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

package com.huxh.framework.model.main

import android.text.Html
import android.text.TextUtils
import android.view.View
import com.huxh.framework.R
import com.huxh.framework.common.adapter.RecyclerViewAdapter
import com.huxh.framework.common.adapter.RecyclerViewHolder
import com.huxh.net.response.ListData
import kotlinx.android.synthetic.main.recycle_item_main.*

/**
 * @author huxh
 * @date 2019/3/27.
 */
class MainAdapter(val data: List<ListData.DatasBean>) : RecyclerViewAdapter<ListData.DatasBean>() {
    override fun setItemLayout() = R.layout.recycle_item_main
    override fun getItemCount() = data.size

    override fun onBindViewHolder(helper: RecyclerViewHolder, position: Int) {
        val item = data[position]
        helper.tv_article_title.text = Html.fromHtml(item.title).toString()
        helper.tv_article_author.text = item.author
//                .setImageResource(R.id.iv_article_like, if (item.isCollect()) R.drawable.ic_like else R.drawable.ic_like_not)
        if (!TextUtils.isEmpty(item.chapterName)) {
            val classifyName = item.superChapterName + " / " + item.chapterName
            helper.tv_article_chapterName.text = classifyName
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.tv_article_niceDate.text = item.niceDate
        }
        helper.tv_article_top.setVisibility(if (item.type == 1) View.VISIBLE else View.GONE)

        helper.tv_article_fresh.setVisibility(if (item.isFresh) View.VISIBLE else View.GONE)

        if (item.tags!!.isNotEmpty()) {
            helper.tv_article_tag.text = item.tags?.get(0)?.name
            helper.tv_article_tag.setVisibility(View.VISIBLE)
        } else {
            helper.tv_article_tag.setVisibility(View.GONE)
        }

        if (!TextUtils.isEmpty(item.envelopePic)) {
            helper.iv_article_thumbnail.setVisibility(View.VISIBLE)
//                    Glide.load(mContext, item.getEnvelopePic(), iv_article_thumbnail)
        } else {
            helper.iv_article_thumbnail.setVisibility(View.GONE)
        }

//        helper.addOnClickListener(R.id.tv_article_chapterName)
//        helper.addOnClickListener(R.id.iv_article_like)
//        helper.addOnClickListener(R.id.tv_article_tag)

    }
}