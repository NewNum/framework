package com.app.framework.utils

import android.graphics.Color
import com.hjq.toast.style.ToastBlackStyle

/**
 * @author Administrator
 * @date 2019/5/27.
 */
class ToastStyle : ToastBlackStyle() {


    override fun getTextColor(): Int {
        return Color.parseColor("#333333")
    }

    override fun getTextSize() = 17F


    override fun getBackgroundColor(): Int {
        return Color.parseColor("#33000000")
    }

    override fun getPaddingTop() = 13
    override fun getPaddingBottom() = 13
    override fun getMaxLines(): Int {
        return 50
    }
}