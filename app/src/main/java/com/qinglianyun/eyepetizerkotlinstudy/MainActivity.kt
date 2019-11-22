package com.qinglianyun.eyepetizerkotlinstudy

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.qinglianyun.base.utils.BottomNavigationViewUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.FoundFragment
import com.qinglianyun.eyepetizerkotlinstudy.view.HomeFragment
import com.qinglianyun.eyepetizerkotlinstudy.view.HotFragment
import com.qinglianyun.eyepetizerkotlinstudy.view.PersonalFragment
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

class MainActivity : BaseActivity<IMainView, MainPresenter>(), IMainView {
    private lateinit var mBnvMain: BottomNavigationView
    private lateinit var mFragmentList: ArrayList<Fragment>
    private var selectPosition: Int = -1;

    override fun getLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        mBnvMain = findViewById(R.id.bnv_main)
        BottomNavigationViewUtils.disableShiftMode(mBnvMain)
    }

    override fun initListeners() {
        mBnvMain.setOnNavigationItemSelectedListener { item ->
            itemSelectById(item.itemId)
            true
        }
    }

    override fun initData() {
        mFragmentList = arrayListOf()
        mFragmentList.add(HomeFragment.getInstance())
        mFragmentList.add(FoundFragment.getInstance())
        mFragmentList.add(HotFragment.getInstance())
        mFragmentList.add(PersonalFragment.getInstance())

        var transaction = supportFragmentManager.beginTransaction()
        for (fragment in mFragmentList) {
            transaction.add(R.id.fl_main, fragment)
        }
        transaction.commit()

        supportFragmentManager.beginTransaction().show(mFragmentList.get(0))
            .hide(mFragmentList.get(1))
            .hide(mFragmentList.get(2))
            .hide(mFragmentList.get(3)).commit()
        selectPosition = 0
    }

    private fun itemSelectById(viewId: Int) {
        when (viewId) {
            R.id.menu_item_home -> {
                if (0 == selectPosition) {
                    return
                }
                supportFragmentManager.beginTransaction().show(mFragmentList.get(0))
                    .hide(mFragmentList.get(selectPosition)).commit()
                selectPosition = 0
            }
            R.id.menu_item_found -> {
                if (1 == selectPosition) {
                    return
                }
                supportFragmentManager.beginTransaction().show(mFragmentList.get(1))
                    .hide(mFragmentList.get(selectPosition)).commit()
                selectPosition = 1
            }
            R.id.menu_item_hot -> {
                if (2 == selectPosition) {
                    return
                }
                supportFragmentManager.beginTransaction().show(mFragmentList.get(2))
                    .hide(mFragmentList.get(selectPosition)).commit()
                selectPosition = 2
            }
            R.id.menu_item_mine -> {
                if (3 == selectPosition) {
                    return
                }
                supportFragmentManager.beginTransaction().show(mFragmentList.get(3))
                    .hide(mFragmentList.get(selectPosition)).commit()
                selectPosition = 3
            }
        }
    }
}
