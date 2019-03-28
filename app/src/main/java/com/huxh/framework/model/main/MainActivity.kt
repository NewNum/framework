/*
 * Copyright © Hu Xinhui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huxh.framework.model.main

import android.os.Bundle
import com.huxh.framework.R
import com.huxh.framework.common.ui.BaseActivity
import com.huxh.net.base.BaseObserver
import com.huxh.net.response.ListData
import com.huxh.net.utils.RxUtils

class MainActivity : BaseActivity(), Contract.MainPresenter {

    private val data = mutableListOf<ListData.DatasBean>()
    override fun loadData() {
        addSubscribe(apiService.getArticleList(pageNum)
                .compose(RxUtils.SchedulerTransformer())
                .filter({ articleListData -> mainView != null })
                .subscribeWith(object : BaseObserver<ListData>(
                        mainView, mainView.getString(R.string.failed_to_obtain_article_list)
                ) {
                    override fun onSuccess(listData: ListData?) {
                        data.addAll(listData?.datas!!)
                        mainView.notifyData()
                    }

                    override fun onComplete() {
                        super.onComplete()
                        mainView.stopRefresh()
                    }
                }))
    }

    private lateinit var mainView: Contract.MainView


    private var pageNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainView = MainView(this, this)
        mainView.linkData(data)
        loadData()
    }

    override fun bye() {
        if (mainView.isDrawerOpen()) {
            mainView.closeDrawer()
        } else {
            super.bye()
        }
    }

}
