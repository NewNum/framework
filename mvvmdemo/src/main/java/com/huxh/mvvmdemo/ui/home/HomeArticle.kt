package com.huxh.mvvmdemo.ui.home

import com.huxh.mvvmdemo.article.dao.Article
import com.huxh.mvvmdemo.data.model.BaseData

data class HomeArticle(val data: Data) : BaseData()
data class Data(
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val curPage: Int,
    val datas: List<Article>
)