package com.huxh.mvvmdemo.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huxh.mvvmdemo.article.dao.ArticleDao
import com.huxh.mvvmdemo.article.dao.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}