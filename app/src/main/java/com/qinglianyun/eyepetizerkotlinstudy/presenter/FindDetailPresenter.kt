package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.FoundModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IFindDetailView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/29.
 */
class FindDetailPresenter(view: IFindDetailView) : BasePresenter<IFindDetailView>(view) {
    private val mModel: FoundModel by lazy {
        FoundModel()
    }

    fun getCategoryDetail(id: Long) {
        mModel.getCategoryDetailList(id, object : ICallback<HomeBean> {
            override fun onSuccess(result: HomeBean) {
                var temp: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
                result.issueList?.forEach {
                    it.itemList?.forEach {
                        temp.add(it)
                    }
                }
                baseView?.getCategoryDetailSuc(temp)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getCategoryDetailFail(error.code, error.message)
            }
        }
        )
    }
}