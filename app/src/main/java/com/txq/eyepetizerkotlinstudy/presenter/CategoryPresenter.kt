package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.model.CategoryModel
import com.txq.eyepetizerkotlinstudy.view.i.ICategoryView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.CategoryBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
class CategoryPresenter(view: ICategoryView) : BasePresenter<ICategoryView>(view) {
    private val mModel: CategoryModel by lazy {
        CategoryModel()
    }

    fun requestData() {
        mModel?.getCategory(object : ICallback<MutableList<CategoryBean>> {
            override fun onSuccess(result: MutableList<CategoryBean>) {
                baseView?.getDataSuccess(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getDataFail(error.code, error.message)
            }
        })
    }
}