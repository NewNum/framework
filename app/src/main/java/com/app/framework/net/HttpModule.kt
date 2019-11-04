/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.app.framework.net


import com.app.framework.App

import com.app.framework.net.interceptor.LoggingInterceptor
import com.app.framework.net.interceptor.NetCacheInterceptor
import com.app.framework.net.interceptor.OfflineCacheInterceptor
import com.app.framework.utils.ConfigManager

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpModule private constructor() {

    val service: ApiService
        get() = provideApiService(provideRetrofit(provideRetrofitBuilder(), provideOkHttpClient(provideOkHttpBuilder())))

    private fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, ConfigManager.BASE_URL)
    }

    private fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    private fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    private fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
/*        if (ConfigManager.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
//            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor(LoggingInterceptor())
        }
        //设置缓存
        val cacheFile = File(App.context.externalCacheDir, "network")
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong()) //50M
        builder.addNetworkInterceptor(NetCacheInterceptor())
        builder.addInterceptor(OfflineCacheInterceptor())
        builder.cache(cache)
        //设置超时
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.build()
            chain.proceed(request)
        }*/
        return builder.build()
    }

    private fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient, url: String): Retrofit {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    companion object {

        val instance = HttpModule()
    }
}
