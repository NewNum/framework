/*
 *     (C) Copyright 2019, Hu Xinhui.
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

package com.huxh.framework.common.ui;


import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.huxh.mvp.BasePresenter;
import com.huxh.net.ApiService;
import com.huxh.net.HttpModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity implements BasePresenter {

    private CompositeDisposable compositeDisposable;
    public ApiService apiService = HttpModule.getInstance().getService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .init();
    }

    @Override
    public void bye() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

}
