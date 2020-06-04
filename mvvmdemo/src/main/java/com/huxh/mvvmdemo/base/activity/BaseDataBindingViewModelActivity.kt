package com.huxh.mvvmdemo.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 封装带有协程基类(DataBinding + ViewModel),使用代理类完成
 *
 */
abstract class BaseDataBindingViewModelActivity<DB : ViewDataBinding> : BaseViewModelActivity() {

    lateinit var db: DB

    override fun setContentLayout() {
        db = DataBindingUtil.setContentView(this, layoutId())
        db.lifecycleOwner = this
        initViewModelAction()
        onViewCreate()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.unbind()
    }

}