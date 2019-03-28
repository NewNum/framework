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

package com.huxh.net.base;

import android.text.TextUtils;
import android.util.Log;

import com.huxh.mvp.BaseView;
import com.huxh.framework.R;
import com.huxh.net.utils.CommonUtils;
import com.huxh.net.exception.ServerException;

import androidx.annotation.CallSuper;
import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;


public abstract class BaseObserver<T> extends ResourceObserver<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    private BaseView mView;
    private String mErrorMsg;

    protected BaseObserver(BaseView view) {
        this.mView = view;
    }

    protected BaseObserver(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    public abstract void onSuccess(T data);

    @CallSuper
    public void onFailure(int code, String message) {
        mView.toast(message);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        mView.showLoading();
    }

    @Override
    public final void onNext(BaseResponse<T> baseResponse) {
        mView.hideLoading();
        if (baseResponse.getErrorCode() == 0) {
            Log.d(TAG, "onSuccess");
            onSuccess(baseResponse.getData());
        } else {
            Log.d(TAG, "onFailure");
            onFailure(0, baseResponse.getErrorMsg());
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        if (mView == null) {
            return;
        }
        if (!CommonUtils.isNetworkConnected()) {
            mView.toast(mView.getString(R.string.network_error));
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError" + e.getMessage());
        if (mView == null) {
            return;
        }
        mView.hideLoading();
        if (e instanceof HttpException) {
            mView.toast(mView.getString(R.string.network_error));
        } else if (e instanceof ServerException) {
            mView.toast(e.toString());
        } else {
            if (!TextUtils.isEmpty(mErrorMsg)) {
                mView.toast(mErrorMsg);
            }
            mView.toast("error");
            Log.e(TAG, e.toString());
        }
    }

}
