package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.bean.TabInfoBean
import com.txq.eyepetizerkotlinstudy.model.RankModel
import com.txq.eyepetizerkotlinstudy.view.i.IHotView

/**
 * Created by tang_xqing on 2019/11/26.
 */
class HotPresenter(view: IHotView) : BasePresenter<IHotView>(view) {

    private val mModel: RankModel by lazy {
        RankModel()
    }

    fun getRankList() {
        mModel.getRankList(object : ICallback<TabInfoBean> {
            override fun onSuccess(result: TabInfoBean) {
                baseView?.getDataListSuccess(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getDataListFail(error.code, error.message)
            }
        })
    }

}