package com.huxh.mvvmdemo

import android.app.Application
import android.content.ContextWrapper

lateinit var mApplication: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }
}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)
