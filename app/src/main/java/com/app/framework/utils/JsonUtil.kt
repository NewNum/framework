package com.app.framework.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author Administrator
 * @date 2019/9/26.
 */
object JsonUtil {
    private val gson = GsonBuilder().create()

    fun <T> json2Bean(json: String?, t: Class<*>): T? {
        if (json?.isBlank() == true) {
            return null
        }
        return gson.fromJson<T>(json, t)
    }

    fun json4Bean(bean: Any): String {
        return gson.toJson(bean)
    }

    /*inline fun <reified T : Any> Gson.fromJson(json: String): T =
        this.fromJson<T>(json, T::class.java)*/

    inline fun <reified T : Any> fromJson(json: String): T {
        return GsonBuilder().create().fromJson<T>(json, T::class.java)
    }
}