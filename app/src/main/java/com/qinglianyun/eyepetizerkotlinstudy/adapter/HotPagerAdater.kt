package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotPagerAdater(fg: FragmentManager) : FragmentPagerAdapter(fg) {
    private var fragmentList = arrayListOf<Fragment>()
    fun setDataList(list: ArrayList<Fragment>) {
        fragmentList.addAll(list)
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList.get(p0)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}