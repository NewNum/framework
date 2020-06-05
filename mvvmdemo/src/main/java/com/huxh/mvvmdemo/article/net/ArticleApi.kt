package com.huxh.mvvmdemo.article.net

import retrofit2.http.GET

interface ArticleApi {

    @GET()
    suspend fun homeArticle()
}