package com.qinglianyun.eyepetizerkotlinstudy.presenter

import android.text.TextUtils
import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.HomeModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IHomeView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
class HomePresenter(view: IHomeView) : BasePresenter<IHomeView>(view) {
    private var homeNextPageUrl: String? = null

    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    fun getHomeData() {
        if (TextUtils.isEmpty(homeNextPageUrl)) {
            getHomeFirstData()
        } else {
            getHomeMoreData()
        }
    }

    fun getHomeFirstData() {
        mModel.getHomeFirstData(1, object : ICallback<HomeBean> {
            override fun onSuccess(result: HomeBean) {
                homeNextPageUrl = result.nextPageUrl
                baseView?.getDataSuccess(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getDataFail(error.code, error.message)
            }
        })
    }

    fun getHomeMoreData() {
        homeNextPageUrl?.let {
            mModel.getHomeMoreData(it, object : ICallback<HomeBean> {
                override fun onSuccess(result: HomeBean) {
                    homeNextPageUrl = result.nextPageUrl
                    baseView?.getDataSuccess(result)
                }

                override fun onError(error: ErrorMessage) {
                    baseView?.getDataFail(error.code, error.message)
                }
            })
        }
    }
}