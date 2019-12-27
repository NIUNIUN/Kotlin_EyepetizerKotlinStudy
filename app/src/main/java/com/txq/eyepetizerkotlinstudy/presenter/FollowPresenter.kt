package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.model.FollowModel
import com.txq.eyepetizerkotlinstudy.view.i.IFollowView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FollowPresenter(view: IFollowView) : BasePresenter<IFollowView>(view) {

    private val mModel: FollowModel by lazy {
        FollowModel()
    }

    fun getFollowData() {
        println("接口请求 获取关注数据")
        mModel.getFollowData(object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                baseView?.getFollowDataSuc(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getFollowDataFail(error.code, error.message)
            }
        })
    }

    fun getFollowMoreData(url: String) {
        mModel.getFollowDataMore(url, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                baseView?.getFollowDataSuc(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getFollowDataFail(error.code, error.message)
            }
        })
    }
}