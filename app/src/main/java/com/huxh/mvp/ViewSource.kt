/*
 * Copyright © Yan Zhenjie
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
package com.huxh.mvp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.huxh.framework.R

/**
 * Created by YanZhenjie on 2017/12/8.
 */
class ViewSource(view: View) : Source<View>(view) {

    private var mActionBar: Toolbar? = null
    private var mActionBarIcon: Drawable? = null
    private var mMenuItemSelectedListener: Source.MenuClickListener? = null

    override fun bind(target: Any) {
        val toolbar = source.findViewById<Toolbar>(R.id.toolbar)
        setActionBar(toolbar)
    }

    override fun setActionBar(actionBar: Toolbar) {
        this.mActionBar = actionBar
        mActionBar?.setOnMenuItemClickListener { item ->
            mMenuItemSelectedListener?.onMenuClick(item)
            true
        }
        mActionBar?.setNavigationOnClickListener {
            mMenuItemSelectedListener?.onHomeClick()
        }
        mActionBarIcon = mActionBar?.navigationIcon
    }

    override val menuInflater: SupportMenuInflater
        @SuppressLint("RestrictedApi")
        get() = SupportMenuInflater(if (mActionBar != null) mActionBar!!.context else context)

    override val menu: Menu?
        get() = mActionBar?.menu

    override fun setMenuClickListener(selectedListener: Source.MenuClickListener) {
        this.mMenuItemSelectedListener = selectedListener
    }

    override fun setDisplayHomeAsUpEnabled(showHome: Boolean) {
        if (showHome) {
            mActionBar?.navigationIcon = mActionBarIcon
        } else {
            mActionBar?.navigationIcon = null
        }
    }

    override fun setHomeAsUpIndicator(@DrawableRes icon: Int) {
        setHomeAsUpIndicator(ContextCompat.getDrawable(context, icon))
    }

    override fun setHomeAsUpIndicator(icon: Drawable?) {
        this.mActionBarIcon = icon
        mActionBar?.navigationIcon = icon
    }

    override fun setTitle(title: CharSequence) {
        mActionBar?.title = title
    }

    override fun setTitle(@StringRes title: Int) {
        mActionBar?.setTitle(title)
    }

    override fun setSubTitle(title: CharSequence) {
        mActionBar?.subtitle = title
    }

    override fun setSubTitle(@StringRes title: Int) {
        mActionBar?.setSubtitle(title)
    }

    override val context: Context
        get() = source.context

    override val hostView: View
        get() = source

    override fun closeInputMethod() {
        val focusView = hostView.findFocus()
        if (focusView != null) {
            val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager?.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }

    override fun unbind() {}

}