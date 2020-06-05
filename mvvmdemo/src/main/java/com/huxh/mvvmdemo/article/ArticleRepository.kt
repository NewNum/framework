package com.huxh.mvvmdemo.article

import com.huxh.mvvmdemo.article.dao.Article
import com.huxh.mvvmdemo.article.dao.ArticleDao
import com.huxh.mvvmdemo.article.net.ArticleApi

class ArticleRepository(private val articleApi: ArticleApi,private val articleDao: ArticleDao) {

}