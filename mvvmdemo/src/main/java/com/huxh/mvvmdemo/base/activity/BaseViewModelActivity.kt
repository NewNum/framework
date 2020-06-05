package com.huxh.mvvmdemo.base.activity

import androidx.lifecycle.Observer
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.base.viewmodel.ErrorState
import com.fmt.github.base.viewmodel.LoadState
import com.fmt.github.base.viewmodel.SuccessState
import com.huxh.mvvmdemo.utils.toast

abstract class BaseViewModelActivity : BaseActivity() {

    protected abstract val viewModel: BaseViewModel

    protected fun initViewModelAction() {
        viewModel.let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                when (stateActionState) {
                    LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        stateActionState.message?.apply {
                            toast(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }

    override fun setContentLayout() {
        setContentView(layoutId())
        initViewModelAction()
        onViewCreate()
        initData()
    }

    open fun handleError() {}


}