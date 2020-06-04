package com.huxh.mvvmdemo.base.activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDataBindingActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var db: DB
    override fun setContentLayout() {
        db = DataBindingUtil.setContentView<DB>(this, layoutId())
        onViewCreate()
        initData()
    }

    abstract override fun onViewCreate()

    override fun onDestroy() {
        super.onDestroy()
        db.unbind()
    }

    @LayoutRes
    abstract override fun layoutId(): Int
}
