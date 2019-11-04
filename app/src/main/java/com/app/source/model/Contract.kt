package com.app.source.model

import com.app.framework.mvp.BasePresenter
import com.app.framework.mvp.BaseView
import com.app.source.model.main.MainActivity
import com.app.source.response.Data


class Contract {
    interface MainPresenter : BasePresenter {
        fun getArticleList()
        var pageIndex: Int
        val list:MutableList<Data>
    }

    abstract class MainView(activity: MainActivity, presenter: MainPresenter) :
        BaseView<MainPresenter>(activity, presenter) {
        abstract fun notifyData(newDataSize: Int)
        abstract fun loadError(code: Int, message: String)
    }
}