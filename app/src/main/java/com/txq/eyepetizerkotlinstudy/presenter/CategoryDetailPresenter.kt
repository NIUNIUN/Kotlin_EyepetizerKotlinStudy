package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.model.CategoryModel
import com.txq.eyepetizerkotlinstudy.view.i.ICategoryDetailView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/29.
 */
class CategoryDetailPresenter(view: ICategoryDetailView) : BasePresenter<ICategoryDetailView>(view) {
    private val mModel: CategoryModel by lazy {
        CategoryModel()
    }

    fun getCategoryDetail(id: Long) {
        mModel.getCategoryDetailList(id, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                var temp: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
                result?.itemList?.forEach {
                    temp.add(it)
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