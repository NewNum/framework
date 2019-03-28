package com.huxh.net.response

/**
 * @author huxh
 * @date 2019/3/27.
 */
data class LoginData(
        val chapterTops: List<String>,
        val collectIds: List<String>,
        val email: String,
        val icon: String,
        val id: Int,
        val password: String,
        val token: String,
        val type: Int,
        val username: String
)