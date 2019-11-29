package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.FoundModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFoundView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
class FoundPresenter(view: IFoundView) : BasePresenter<IFoundView>(view) {
    private val mModel: FoundModel by lazy {
        FoundModel()
    }

    fun requestData() {
        mModel?.getCategory(object : ICallback<MutableList<FindBean>> {
            override fun onSuccess(result: MutableList<FindBean>) {
                baseView?.getDataSuccess(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getDataFail(error.code, error.message)
            }
        })
    }
}