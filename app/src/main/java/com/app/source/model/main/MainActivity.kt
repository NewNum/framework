package com.app.source.model.main

import android.os.Bundle
import com.app.framework.R
import com.app.framework.common.ui.BaseActivity
import com.app.framework.net.base.BaseObserver
import com.app.framework.net.utils.RxUtils
import com.app.source.model.Contract
import com.app.source.response.Data
import com.app.source.response.HomeArticleData
import com.hjq.toast.ToastUtils


class MainActivity : BaseActivity(), Contract.MainPresenter {
    override val list = mutableListOf<Data>()

    override fun getArticleList() {
        addSubscribe(apiService.articleList(pageIndex)
            .compose(RxUtils.SchedulerTransformer())
            .filter { mainView != null }
            .subscribeWith(object : BaseObserver<HomeArticleData>() {
                override fun onSuccess(data: HomeArticleData?) {
                    if (pageIndex == 0) {
                        list.clear()
                    }
                    val elements = data?.datas ?: emptyList()
                    list.addAll(elements)
                    mainView.notifyData(elements.size)
                }
                override fun onFailure(code: Int, message: String) {
                    super.onFailure(code, message)
                    mainView.loadError(code, message)
                }
            })
        )
    }

    override var pageIndex: Int = 0

    private lateinit var mainView: Contract.MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainView = MainView(this, this)
        getArticleList()
    }

    override fun finish() {
        val mNowTime = System.currentTimeMillis()//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            ToastUtils.show("再按一次退出程序")
            mPressedTime = mNowTime
        } else {//退出程序
            super.finish()
            ToastUtils.cancel()
        }
    }

    private var mPressedTime = 0L

}
