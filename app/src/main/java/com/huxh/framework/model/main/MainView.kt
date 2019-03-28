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

package com.huxh.framework.model.main

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.huxh.framework.R
import com.huxh.framework.model.login.LoginActivity
import com.huxh.framework.utils.SPUtil
import com.huxh.net.response.ListData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * @author huxh
 * @date 2019/3/27.
 */
class MainView : Contract.MainView, NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {
    override fun stopRefresh() {
        srlMain.isRefreshing = false
    }

    override fun linkData(data: List<ListData.DatasBean>) {
        rvMain.layoutManager = LinearLayoutManager(context)
        rvMain.adapter = MainAdapter(data)
    }

    override fun onRefresh() {
        presenter.loadData()
    }

    override fun notifyData() {
        rvMain.adapter?.notifyDataSetChanged()
    }

    override fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun isDrawerOpen() = drawer_layout.isDrawerOpen(GravityCompat.START)

    override fun onOptionsItemSelected(item: MenuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
    }

    constructor(activity: MainActivity, presenter: Contract.MainPresenter) : super(
            activity,
            presenter
    ) {
        setActionBar(toolbar)
        setTitle(R.string.title_activity_main)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                activity,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val nav_header_view = nav_view.getHeaderView(0)
        nav_header_view.findViewById<ImageView>(R.id.imageView).setOnClickListener(this::onClick)
        srlMain.setOnRefreshListener(this)

    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.imageView -> {
                if (SPUtil.getLoginStatus()) {
                    toast("已经登录")
                } else {
                    context.startActivity(Intent(context,LoginActivity::class.java))
                }
            }
        }
    }
}