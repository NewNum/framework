package com.app.framework

import android.app.Application
import android.content.Context
import com.hjq.toast.ToastUtils

/**
 * @author Administrator
 * @date 2019/11/4.
 */
class App : Application() {
    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        ToastUtils.init(this)
    }
}