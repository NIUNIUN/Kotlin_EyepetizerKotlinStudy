package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.RankModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IRankView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
class RankPresenter(view: IRankView) : BasePresenter<IRankView>(view) {
    private val mModel: RankModel by lazy {
        RankModel()
    }

    fun requestRankList(apiUri: String) {
        mModel.requestRankList(apiUri, object : ICallback<HotBean> {
            override fun onSuccess(result: HotBean) {
                baseView?.requestRankListSuc(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.requestRankListFail(error.code, error.message)
            }
        })
    }
}