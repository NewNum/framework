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

package com.huxh.framework.utils

import android.content.Context
import android.content.SharedPreferences
import com.huxh.framework.App

/**
 * @author huxh
 * @date 2019/3/27.
 */
object SPUtil {
    private val sp: SharedPreferences by lazy {
        App.context.getSharedPreferences(Constants.SP.NAME, Context.MODE_PRIVATE)
    }

    fun setLoginStatus(isLogin: Boolean) {
        sp.edit().putBoolean(Constants.SP.LOGIN_STATUS, isLogin).apply()
    }

    fun getLoginStatus(): Boolean {
        return sp.getBoolean(Constants.SP.LOGIN_STATUS, false)
    }

    fun setLoginAccount(account: String?) {
        sp.edit().putString(Constants.SP.ACCOUNT, account).apply()
    }

    fun getLoginAccount(): String? {
        return sp.getString(Constants.SP.ACCOUNT, "")
    }

    fun setNightMode(isNightMode: Boolean) {
        sp.edit().putBoolean(Constants.SP.NIGHT_MODE, isNightMode).apply()
    }

    fun isNightMode(): Boolean {
        return sp.getBoolean(Constants.SP.NIGHT_MODE, false)
    }
}