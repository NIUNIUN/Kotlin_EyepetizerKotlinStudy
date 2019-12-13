package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.CategoryModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ICategoryView
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