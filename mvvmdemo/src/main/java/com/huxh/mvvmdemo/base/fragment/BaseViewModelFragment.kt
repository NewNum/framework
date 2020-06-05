package com.huxh.mvvmdemo.base.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.base.viewmodel.ErrorState
import com.fmt.github.base.viewmodel.LoadState
import com.fmt.github.base.viewmodel.SuccessState
import com.huxh.mvvmdemo.utils.toast

abstract class BaseViewModelFragment : BaseFragment() {

    protected abstract val viewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelAction()
    }


    private fun initViewModelAction() {
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

    open fun handleError() {}

}