package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.CategoryModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ICategoryDetailView
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