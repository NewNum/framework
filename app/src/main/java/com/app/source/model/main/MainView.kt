package com.app.source.model.main

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.source.model.Contract
import kotlinx.android.synthetic.main.activity_main.*


class MainView(activity: MainActivity, presenter: Contract.MainPresenter) :
    Contract.MainView(activity, presenter) {
    override fun loadError(code: Int, message: String) {
        srlMain.isRefreshing = false
        // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
        // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
        // errorMessage是会显示到loadMoreView上的，用户可以看到。
        rvMain.loadMoreError(code, message)
    }

    override fun notifyData(newDataSize: Int) {
        srlMain.isRefreshing = false
        // notifyItemRangeInserted()或者notifyDataSetChanged().
        if (presenter.list.size == 0) {
            rvMain.visibility = View.GONE
            ivNothing.visibility = View.VISIBLE
        } else {
            rvMain.visibility = View.VISIBLE
            ivNothing.visibility = View.GONE
        }
        if (presenter.pageIndex == 1) {
            rvMain.adapter?.notifyDataSetChanged()
        } else {
            rvMain.adapter?.notifyItemRangeInserted(
                presenter.list.size - newDataSize,
                newDataSize
            )
        }
        // 数据完更多数据，一定要掉用这个方法。
        // 第一个参数：表示此次数据是否为空。
        // 第二个参数：表示是否还有更多数据。
        rvMain.loadMoreFinish(newDataSize == 0, newDataSize == 10)
    }

    init {
        srlMain.setOnRefreshListener {
            presenter.pageIndex = 0
            presenter.getArticleList()
        }

        rvMain.layoutManager = LinearLayoutManager(context)
//        rvMain.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        rvMain.adapter = MainArticleAdapter(presenter.list)
        rvMain.useDefaultLoadMore()
        rvMain.setLoadMoreListener {
            presenter.pageIndex += 1
            presenter.getArticleList()
        }
    }

}