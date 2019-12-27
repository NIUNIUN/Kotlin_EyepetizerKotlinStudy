package com.txq.eyepetizerkotlinstudy.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by tang_xqing on 2019/11/22.
 */
class BasePagerAdater(fg: FragmentManager, fragments: List<Fragment>, titles: List<String>) :
    FragmentPagerAdapter(fg) {
    private var fragmentList: List<Fragment>? = null
    private var mTitle: List<String>? = null

    init {
        fragmentList = fragments
        mTitle = titles
        if (this.fragmentList != null) {
            val ft = fg.beginTransaction()
            fragmentList?.forEach {
                ft.remove(it)
            }
            ft?.commitAllowingStateLoss()
            fg.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList!!.get(p0)
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle?.get(position) ?: null
    }
}