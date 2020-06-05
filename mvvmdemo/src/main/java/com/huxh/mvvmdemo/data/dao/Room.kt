package com.huxh.mvvmdemo.data.dao

import androidx.room.Room
import com.huxh.mvvmdemo.AppContext

private const val DB_NAME = "open_github_db"

val room = Room.databaseBuilder(AppContext, AppDataBase::class.java, DB_NAME).build()

val articleDao = room.getArticleDao()