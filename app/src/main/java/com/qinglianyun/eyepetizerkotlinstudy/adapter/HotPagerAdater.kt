package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotPagerAdater(fg: FragmentManager) : FragmentPagerAdapter(fg) {
    private var fragmentList = arrayListOf<Fragment>()
    private var title: Array<String> = arrayOf()

    fun setDataList(list: ArrayList<Fragment>,tabTitle:Array<String>) {
        fragmentList.addAll(list)
        title = tabTitle
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList.get(p0)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title.get(position)
    }
}