package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean
import com.qinglianyun.eyepetizerkotlinstudy.model.RankModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IHotView

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