package com.huxh.mvvmdemo

import android.app.Application
import android.content.ContextWrapper
import com.fmt.launch.starter.TaskDispatcher
import com.huxh.mvvmdemo.tasks.InitKoInTask

lateinit var mApplication: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
        //启动器进行异步初始化
        TaskDispatcher.init(this)
        TaskDispatcher.createInstance()
//            .addTask(InitBuGlyTask())
            .addTask(InitKoInTask())
//            .addTask(InitLiveEventBusTask())
//            .addTask(InitSmartRefreshLayoutTask())
            .start()
    }
}


object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)
