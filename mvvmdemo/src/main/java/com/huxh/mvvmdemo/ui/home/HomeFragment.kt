package com.huxh.mvvmdemo.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huxh.mvvmdemo.R
import com.huxh.mvvmdemo.base.fragment.BaseDataBindingViewModelFragment
import com.huxh.mvvmdemo.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : BaseDataBindingViewModelFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.rvHome.layoutManager = LinearLayoutManager(requireContext())
//        db.rvHome.adapter =//todo 首页数据展示
        viewModel.text.observe(viewLifecycleOwner, Observer {
//            db.textHome.text = it
        })

    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

}
