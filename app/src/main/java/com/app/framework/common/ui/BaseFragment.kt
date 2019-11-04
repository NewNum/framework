/*
 * AUTHOR：YanZhenjie
 *
 * DESCRIPTION：create the File, and add the content.
 *
 * Copyright © www.mamaqunaer.com. All Rights Reserved
 *
 */

package com.app.framework.common.ui

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.app.framework.common.callback.PermissionListener
import com.app.framework.mvp.BasePresenter
import com.app.framework.net.HttpModule
import com.app.framework.utils.LogUtils
import com.gyf.immersionbar.components.ImmersionFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * 应用程序中所有Fragment的基类。
 *
 * @author guolin,YanZhenjie
 */
abstract class BaseFragment : ImmersionFragment(), BasePresenter {

    private val TAG = "FragmentLife"

    override fun bye() {
        // TODO 回退Fragment，由开发者自己实现。
    }

    /**
     * Fragment中inflate出来的布局。
     */
    protected var rootView: View? = null


    private var compositeDisposable: CompositeDisposable? = null
    var apiService = HttpModule.instance.service

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG,"${this.javaClass.simpleName} : onDestroy")
        compositeDisposable?.clear()
    }

    protected fun addSubscribe(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtils.d(TAG,"${this.javaClass.simpleName} : onCreateView")
        rootView = inflater.inflate(setContentView(), container, false)
        /**初始化的时候去加载数据 */
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d(TAG,"${this.javaClass.simpleName} : onViewCreated")
        initView(view)
    }

    abstract fun setContentView(): Int


    /**
     * 获取设置的布局
     *
     * @return
     */
    protected fun getContentView(): View? {
        return rootView
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
    </T> */
    protected fun <T : View> findViewById(id: Int): T {

        return getContentView()?.findViewById<View>(id) as T
    }

    //////////////////////////////////////lazyload/////////////////////////////////////
    private var isFragmentVisible: Boolean = false
    private var isPrepared: Boolean = false
    private var isFirst = true

    //--------------------system method callback------------------------//

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d(TAG,"${this.javaClass.simpleName} : onActivityCreated")
        isPrepared = true
        initPrepare()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        LogUtils.d(TAG,"${this.javaClass.simpleName} : setUserVisibleHint")
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isFragmentVisible = true
            lazyLoad()
        } else {
            isFragmentVisible = false
            onInvisible()
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG,"${this.javaClass.simpleName} : onResume")
        if (userVisibleHint) {
            userVisibleHint = true
        }
    }


    //--------------------------------method---------------------------//

    /**
     * 懒加载
     */
    protected fun lazyLoad() {
        if (!isPrepared || !isFragmentVisible || !isFirst) {
            return
        }
        LogUtils.d(TAG,"${this.javaClass.simpleName} : initData")
        initData()
        isFirst = false
    }


    //--------------------------abstract method------------------------//

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    open fun initPrepare() {}

    /**
     * fragment被设置为不可见时调用
     */
    override fun onInvisible() {}

    /**
     * 这里获取数据，刷新界面
     */
    open fun initData() {}

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    open fun initView(view: View) {}

}
