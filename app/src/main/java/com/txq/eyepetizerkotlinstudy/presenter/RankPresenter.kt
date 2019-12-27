package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.model.RankModel
import com.txq.eyepetizerkotlinstudy.view.i.IRankView
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