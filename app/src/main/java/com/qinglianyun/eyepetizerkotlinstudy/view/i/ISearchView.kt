package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/28.
 */
interface ISearchView : IBaseView {

    fun getHotWorldSuccess(result: ArrayList<String>)
    fun getHotWorldFail(code: Int, msg: String)

    fun getSearchByKeySuc(result: HomeBean.IssueListBean)
    fun getSearchByKeyFail(code: Int, msg: String)
}