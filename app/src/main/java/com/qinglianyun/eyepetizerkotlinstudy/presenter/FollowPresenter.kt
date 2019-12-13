package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.FollowModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFollowView
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