package com.qinglianyun.base.view

import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by tang_xqing on 2019/11/6.
 */
abstract class BaseFragment<I : IBaseView, T : BasePresenter<I>> : Fragment(), IBaseView {
    lateinit var mActivity: BaseActivity<I, T>
    var mPresenter: T? = null

    var mHandler: MainHandler = MainHandler(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = activity as BaseActivity<I, T>
        var view = inflater!!.inflate(getLayoutId(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresenter()
        mPresenter?.let { it.onViewCreate() }
        initViews()
        initData()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onViewStart()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.let { it.onViewStop() }
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.onViewPause()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onViewResume()
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        mPresenter?.onViewDestory()
        super.onDestroy()
    }

    override fun handleMessage(message: Message) {

    }

    override fun sendMessage(what: Int, obj: Any) {

    }

    override fun sendMessage(what: Int, obj: Any, delayTime: Long) {

    }

    override fun sendMessage(what: Int, obj: Any, arg1: Int, arg2: Int) {
    }

    abstract fun getLayoutId(): Int
    abstract fun initPresenter()
    abstract fun initViews()
    abstract fun initListeners()
    abstract fun initData()
}