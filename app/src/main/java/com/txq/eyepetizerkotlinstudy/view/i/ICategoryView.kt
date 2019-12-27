package com.txq.eyepetizerkotlinstudy.view.i

import com.txq.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.CategoryBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface ICategoryView : IBaseView {
    fun getDataSuccess(result: MutableList<CategoryBean>)
    fun getDataFail(code: Int, msg: String)

}