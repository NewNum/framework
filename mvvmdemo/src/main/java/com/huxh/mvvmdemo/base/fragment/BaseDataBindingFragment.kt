package com.huxh.mvvmdemo.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDataBindingFragment<DB : ViewDataBinding> : BaseFragment() {

    lateinit var db: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::db.isInitialized) {
            db = DataBindingUtil.inflate<DB>(inflater, layoutId(), container, false)
            // 让xml内绑定的LiveData和Observer建立连接，让LiveData能感知Activity的生命周期
            db.lifecycleOwner = this
        }
        return db.root
    }


    override fun onDestroy() {
        super.onDestroy()
        db.unbind()
    }
}