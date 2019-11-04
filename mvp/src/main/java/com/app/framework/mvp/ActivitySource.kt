/*
 * AUTHOR：YanZhenjie
 *
 * DESCRIPTION：create the File, and add the content.
 *
 * Copyright © www.mamaqunaer.com. All Rights Reserved
 *
 */
package com.app.framework.mvp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception


/**
 * Created by YanZhenjie on 2017/12/8.
 */
internal class ActivitySource(activity: AppCompatActivity) : Source<AppCompatActivity>(activity) {

    override val hostView: View

    private var mActionBar: Toolbar? = null
    private var mActionBarIcon: Drawable? = null
    private var mMenuItemSelectedListener: Source.MenuClickListener? = null

    override val menuInflater: MenuInflater
        @SuppressLint("RestrictedApi")
        get() = SupportMenuInflater(if (mActionBar != null) mActionBar?.context else context)

    override val menu: Menu?
        get() = mActionBar?.menu

    override val context: Context
        get() = source

    init {
        hostView = activity.findViewById(android.R.id.content)
//        mActionBarIcon = AppCompatResources.getDrawable(activity.applicationContext, R.drawable.ic_icon_back)
    }

    override fun bind(target: Any) {
        try {
            val toolbar = source.findViewById<Toolbar>(source.resources.getIdentifier("toolbar", "id", source.packageName))
            if (toolbar != null) setActionBar(toolbar)
        } catch (e: Exception) {

        }
    }

    override fun setActionBar(actionBar: Toolbar) {
        this.mActionBar = actionBar
        val activity = source
        setTitle(activity.title)
        mActionBar?.setOnMenuItemClickListener { item ->
            mMenuItemSelectedListener?.onMenuClick(item)
            true
        }
        mActionBar?.setNavigationOnClickListener {
            mMenuItemSelectedListener?.onHomeClick()
        }
        mActionBarIcon = mActionBar?.navigationIcon /*?: AppCompatResources.getDrawable(
            activity.applicationContext,
            R.drawable.ic_icon_back
        )*/
    }

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

    override fun closeInputMethod() {
        val activity = source
        val focusView = activity.currentFocus
        if (focusView != null) {
            val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }

    override fun unbind() {}
}