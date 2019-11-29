package com.qinglianyun.eyepetizerkotlinstudy

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.utils.BottomNavigationViewUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.*
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

class MainActivity : BaseActivity<IMainView, MainPresenter>(), IMainView {
    private lateinit var mBnvMain: BottomNavigationView
    private lateinit var mTvTitle: TextView
    private lateinit var mIvSearch: ImageView

    private lateinit var mFragmentList: ArrayList<Fragment>
    private var selectPosition: Int = -1;
    private var mFragmentTitle: Array<String> = arrayOf("首页", "发现", "热门", "我的")

    override fun getLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        mBnvMain = findViewById(R.id.bnv_main)
        mTvTitle = findViewById(R.id.tv_main_title)
        mIvSearch = findViewById(R.id.iv_main_search)
        BottomNavigationViewUtils.disableShiftMode(mBnvMain)
    }

    override fun initListeners() {
        mBnvMain.setOnNavigationItemSelectedListener { item ->
            itemSelectById(item.itemId)
            true
        }

        mIvSearch.setOnClickListener {
            // 跳转到搜索页面
            SearchActivity.startAction(this, mIvSearch)
//            SearchActivity.startAction(this)
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
        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        mTvTitle.setText(mFragmentTitle.get(selectPosition))
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
        setToolbarTitle()
    }
}
