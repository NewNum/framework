/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.app.framework.net.base

import androidx.annotation.CallSuper
import android.text.TextUtils

import com.app.framework.mvp.BaseView
import com.app.framework.R
import com.app.framework.net.utils.CommonUtils
import com.app.framework.net.exception.ServerException

import com.app.framework.utils.LogUtils
import com.app.framework.net.exception.OtherException
import io.reactivex.observers.ResourceObserver
import retrofit2.HttpException


abstract class BaseObserver<T> : ResourceObserver<BaseResponse<T>> {

    private var mView: BaseView<*>? = null
    private var mErrorMsg: String = ""

    protected constructor(view: BaseView<*>?) {
        this.mView = view
    }

    protected constructor()

    protected constructor(errorMsg: String) {
        this.mErrorMsg = errorMsg
    }

    protected constructor(view: BaseView<*>, errorMsg: String) {
        this.mView = view
        this.mErrorMsg = errorMsg
    }

    abstract fun onSuccess(data: T?)
    @CallSuper
    open fun onSuccessAllResponse(response: BaseResponse<T>) {
        onSuccess(response.data)
    }

    @CallSuper
    open fun onFailure(code: Int, message: String) {
        mView?.toast(message)
    }

    override fun onStart() {
        LogUtils.d(TAG, "onStart")
        mView?.showLoading()
    }

    override fun onNext(baseResponse: BaseResponse<T>) {
        mView?.hideLoading()
        if (baseResponse.errorCode == 0) {
            LogUtils.d(TAG, "onSuccess,${baseResponse.data}")
            onSuccessAllResponse(baseResponse)
        } else {
            LogUtils.d(TAG, "onFailure,${baseResponse.errorCode},${baseResponse.errorMsg}")
            onFailure(baseResponse.errorCode, baseResponse.errorMsg)
        }
    }

    override fun onComplete() {
        LogUtils.d(TAG, "onComplete")
        if (mView == null) {
            return
        }
        if (!CommonUtils.isNetworkConnected()) {
            mView?.toast(mView?.getString(R.string.network_error))
        }

    }

    override fun onError(e: Throwable) {
        LogUtils.d(TAG, "onError " + e.message)
        if (mView == null) {
            return
        }
        mView?.hideLoading()
        if (e is HttpException) {
            mView?.toast(mView?.getString(R.string.network_error))
        } else if (e is ServerException) {
            mView?.toast(e.message)
        } else if (e is OtherException) {
            mView?.toast(e.message)
        } else {
            if (!TextUtils.isEmpty(mErrorMsg)) {
                mView?.toast(mErrorMsg)
            }
            mView?.toast("error")
        }
    }

    companion object {
        private val TAG = "BaseObserver"
    }

}
