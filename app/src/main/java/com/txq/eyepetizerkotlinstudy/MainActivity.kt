package com.txq.eyepetizerkotlinstudy

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.txq.base.utils.BottomNavigationViewUtils
import com.txq.base.view.BaseActivity
import com.txq.eyepetizerkotlinstudy.model.mainModule
import com.txq.eyepetizerkotlinstudy.presenter.MainPresenter
import com.txq.eyepetizerkotlinstudy.view.*
import com.txq.eyepetizerkotlinstudy.view.i.IMainView
import org.kodein.di.*
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<IMainView, MainPresenter>(), IMainView, KodeinAware {
    private lateinit var mBnvMain: BottomNavigationView
    private lateinit var mTvTitle: TextView
    private lateinit var mIvSearch: ImageView

    private lateinit var mFragmentList: ArrayList<Fragment>
    private var selectPosition: Int = -1;
    private var mFragmentTitle: Array<String> = arrayOf("首页", "发现", "热门", "我的")
    private var mPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private var mResultCodePer = 123

    val parentKodein by closestKodein()

    // retainedKodein：保证屏幕旋转时不会重新创建kodein
    override val kodein: Kodein by retainedKodein {
        // closestKodein() 相邻容器 ，也就是Application的kodein。  extend 继承Application的kodein，可以使用Application的依赖
        extend(parentKodein, copy = Copy.None)

        // 注入Kodein容器中的依赖
        import(mainModule)
        bind<Activity>() with instance(this@MainActivity)
    }

    override val kodeinTrigger: KodeinTrigger = KodeinTrigger()

    val homeFragment: HomeFragment by instance()
    val hotFragment: HotFragment by instance()
    val foundFragment: FoundFragment by instance()
    val personalFragment: PersonalFragment by instance(tag = "单例")

    override fun getLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        checkPermission()
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
//        mFragmentList.add(HomeFragment.getInstance())
//        mFragmentList.add(FoundFragment.getInstance())
//        mFragmentList.add(HotFragment.getInstance())
//        mFragmentList.add(PersonalFragment.getInstance())
        mFragmentList.add(homeFragment)
        mFragmentList.add(foundFragment)
        mFragmentList.add(hotFragment)
        mFragmentList.add(personalFragment)

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

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        // 读取存储卡权限
        var permission = ContextCompat.checkSelfPermission(this, mPermission)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(mPermission), mResultCodePer)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK && resultCode == mResultCodePer) {

        }
    }

//    // 返回相邻层的kodein容器，Activity获得的是Application层的Kodein
//    val parentKodein  by closestKodein()
//
//    override val kodein: Kodein by retainedKodein{
//        // extend将父容器放入Activity容器中，Activity层可以使用到父容器的Model
//        extend(parentKodein,copy = Copy.All)
//
//        // 注入需要用到的model
//        import(foundFragmentModel)
//        import(homeFragmentModel)
//
//        // 将model绑定到当前页
//        bind<Activity>() with instance(this@MainActivity)
//    }
//
//    val homeFragment:HomeFragment by instance()
    /**
     * 整体五步走
     * 1、继承KodeinAware
     * 2、定义model，进行绑定
     * 3、将Kodein容器交给Activity、Application，重写kodein
     * 4、Activity\Application注入所需model，并绑定
     * 5、委托，获得model中依赖实例。
     */
}