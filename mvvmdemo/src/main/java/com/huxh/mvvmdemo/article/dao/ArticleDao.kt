package com.huxh.mvvmdemo.article.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {
    @Query("select * from article")
    suspend fun getAll(): List<Article>

    @Insert
    suspend fun insertAll(vararg article: Article)
}