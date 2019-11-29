package com.qinglianyun.base.view

import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity

/**
 * Created by tang_xqing on 2019/11/12.
 */
abstract class BaseActivity<I : IBaseView, T : BasePresenter<I>> : AppCompatActivity(), IBaseView {
    val mActivity: AppCompatActivity by lazy {
        this
    }

    lateinit var mPresenter: T

    val mHandler: MainHandler = MainHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())

        initPresenter()
        mPresenter?.onViewCreate()
        initViews()
        initData()
        initListeners()

    }

    abstract fun getLayoutView(): Int
    abstract fun initPresenter()
    abstract fun initViews()
    abstract fun initListeners()
    abstract fun initData()

    override fun sendMessage(what: Int, obj: Any) {

    }

    override fun sendMessage(what: Int, obj: Any, delayTime: Long) {

    }

    override fun sendMessage(what: Int, obj: Any, arg1: Int, arg2: Int) {

    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onViewStart()
    }

    override fun onRestart() {
        super.onRestart()
        mPresenter?.onViewRestart()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.onViewPause()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onViewResume()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onViewStop()
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        mPresenter?.onViewDestory()
        super.onDestroy()
    }

    override fun handleMessage(message: Message) {

    }
}
