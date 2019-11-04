package com.app.framework.net

import com.app.framework.net.base.BaseResponse
import com.app.source.response.HomeArticleData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/article/list/{pageIndex}/json")
    fun articleList(@Path("pageIndex") pageIndex: Int): Observable<BaseResponse<HomeArticleData>>

}

