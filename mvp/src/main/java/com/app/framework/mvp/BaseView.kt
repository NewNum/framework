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
package com.app.framework.mvp

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.hjq.toast.ToastUtils
import com.huxh.mvp.R
import kotlinx.android.extensions.LayoutContainer
import java.util.*

/**
 *
 * View of MVP.
 * Created by YanZhenjie on 2017/7/17.
 */
abstract class BaseView<Presenter : BasePresenter> : LayoutContainer {
    private lateinit var mSource: Source<*>
    lateinit var presenter: Presenter

    override val containerView: View?
        get() = mSource.hostView

    /**
     * Get menu inflater.
     */
    val menuInflater: MenuInflater
        get() = mSource.menuInflater

    val context: Context
        get() = mSource.context

    val resources: Resources
        get() = context.resources

    constructor(activity: AppCompatActivity, presenter: Presenter) : this(ActivitySource(activity), presenter) {}

    constructor(view: View, presenter: Presenter) : this(ViewSource(view), presenter) {}

    private constructor(source: Source<*>, presenter: Presenter) {
        this.mSource = source
        this.presenter = presenter
        this.mSource.bind(this)

        invalidateOptionsMenu()
        mSource.setMenuClickListener(object : Source.MenuClickListener {
            override fun onHomeClick() {
                presenter.bye()
            }

            override fun onMenuClick(item: MenuItem) {
                optionsItemSelected(item)
            }
        })

        presenter.lifecycle.addObserver(GenericLifecycleObserver { source, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                resume()
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                pause()
            } else if (event == Lifecycle.Event.ON_STOP) {
                stop()
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                destroy()
            }
        })
    }

    private fun resume() {
        onResume()
    }

    fun onResume() {}

    private fun pause() {
        onPause()
    }

    fun onPause() {}

    private fun stop() {
        onStop()
    }

    fun onStop() {}

    private fun destroy() {
        closeInputMethod()
        onDestroy()
        this.mSource.unbind()
    }

    fun onDestroy() {}

    /**
     * Set actionBar.
     */
    fun setActionBar(actionBar: Toolbar) {
        mSource.setActionBar(actionBar)
        invalidateOptionsMenu()
    }

    /**
     * ReCreate menu.
     */
    fun invalidateOptionsMenu() {
        val menu = mSource.menu
        if (menu != null) {
            onCreateOptionsMenu(menu)
        }
    }

    /**
     * Create menu.
     */
    open fun onCreateOptionsMenu(menu: Menu) {}

    private fun optionsItemSelected(item: MenuItem) {
        if (item.itemId == android.R.id.home) {
            if (!onInterceptToolbarBack()) {
                presenter.bye()
            }
        } else {
            onOptionsItemSelected(item)
        }
    }

    /**
     * When the menu is clicked.
     */
    open fun onOptionsItemSelected(item: MenuItem) {}

    /**
     * Intercept the return button.
     */
    fun onInterceptToolbarBack(): Boolean {
        return false
    }

    fun openInputMethod(view: View) {
        view.requestFocus()
        val manager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun closeInputMethod() {
        mSource.closeInputMethod()
    }

    fun setDisplayHomeAsUpEnabled(showHome: Boolean) {
        mSource.setDisplayHomeAsUpEnabled(showHome)
    }

    fun setHomeAsUpIndicator(@DrawableRes icon: Int) {
        mSource.setHomeAsUpIndicator(icon)
    }

    fun setHomeAsUpIndicator(icon: Drawable?) {
        mSource.setHomeAsUpIndicator(icon)
    }

    fun setTitle(title: String) {
        mSource.setTitle(title)
    }

    fun setTitle(@StringRes title: Int) {
        mSource.setTitle(title)
    }

    fun setSubTitle(title: String) {
        mSource.setSubTitle(title)
    }

    fun setSubTitle(@StringRes title: Int) {
        mSource.setSubTitle(title)
    }

    fun getText(@StringRes id: Int): CharSequence {
        return context.getText(id)
    }

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return context.getString(id, *formatArgs)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(mSource.context, id)
    }

    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(mSource.context, id)
    }

    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return resources.getStringArray(id)
    }

    fun getIntArray(@ArrayRes id: Int): IntArray {
        return resources.getIntArray(id)
    }

    fun showMessageDialog(@StringRes title: Int, @StringRes message: Int) {
        showMessageDialog(getText(title), getText(message))
    }

    fun showMessageDialog(@StringRes title: Int, message: CharSequence) {
        showMessageDialog(getText(title), message)
    }

    fun showMessageDialog(title: CharSequence, @StringRes message: Int) {
        showMessageDialog(title, getText(message))
    }

    fun showMessageDialog(title: CharSequence, message: CharSequence) {
        val alertDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.confirm) { dialog, which -> }
                .create()
        alertDialog.show()
    }

    fun showConfirmDialog(@StringRes title: Int, @StringRes message: Int, confirmClickListener: OnDialogClickListener) {
        showConfirmDialog(getText(title), getText(message), confirmClickListener)
    }

    fun showConfirmDialog(@StringRes title: Int, message: CharSequence, confirmClickListener: OnDialogClickListener) {
        showConfirmDialog(getText(title), message, confirmClickListener)
    }

    fun showConfirmDialog(title: CharSequence, @StringRes message: Int, confirmClickListener: OnDialogClickListener) {
        showConfirmDialog(title, getText(message), confirmClickListener)
    }

    fun showConfirmDialog(title: CharSequence, message: CharSequence, confirmClickListener: OnDialogClickListener) {
        val alertDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel) { dialog, which -> }
                .setPositiveButton(R.string.confirm) { dialog, which -> confirmClickListener.onClick(which) }
                .create()
        alertDialog.show()
    }

    fun showMessageDialog(
            @StringRes title: Int, @StringRes message: Int,
            cancelClickListener: OnDialogClickListener, confirmClickListener: OnDialogClickListener
    ) {
        showMessageDialog(getText(title), getText(message), cancelClickListener, confirmClickListener)
    }

    fun showMessageDialog(
            @StringRes title: Int, message: CharSequence,
            cancelClickListener: OnDialogClickListener, confirmClickListener: OnDialogClickListener
    ) {
        showMessageDialog(getText(title), message, cancelClickListener, confirmClickListener)
    }

    fun showMessageDialog(
            title: CharSequence, @StringRes message: Int,
            cancelClickListener: OnDialogClickListener, confirmClickListener: OnDialogClickListener
    ) {
        showMessageDialog(title, getText(message), cancelClickListener, confirmClickListener)
    }

    fun showMessageDialog(
            title: CharSequence, message: CharSequence,
            cancelClickListener: OnDialogClickListener, confirmClickListener: OnDialogClickListener
    ) {
        val alertDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel) { dialog, which -> cancelClickListener.onClick(which) }
                .setPositiveButton(R.string.confirm) { dialog, which -> confirmClickListener.onClick(which) }
                .create()
        alertDialog.show()
    }

    interface OnDialogClickListener {
        fun onClick(which: Int)
    }

    fun toast(message: CharSequence?) {
        ToastUtils.show(message)
    }

    fun toast(@StringRes message: Int) {
        ToastUtils.show(message)
    }

    private var progressdialog: AlertDialog? = null

    fun showLoading(message: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.loading_progressbar, null, false)
        val loadingText = view.findViewById<TextView>(R.id.loading_text)
        loadingText.setText(message)
        progressdialog = AlertDialog.Builder(context).setView(view).create()
        Objects.requireNonNull(progressdialog?.window)?.setBackgroundDrawableResource(android.R.color.transparent)
        if (!progressdialog!!.isShowing) {
            progressdialog?.show()
        }
    }

    fun showLoading() {
        showLoading("Loading")
    }

    fun hideLoading() {
        if (progressdialog != null && progressdialog!!.isShowing) {
            progressdialog?.dismiss()
        }
    }
}