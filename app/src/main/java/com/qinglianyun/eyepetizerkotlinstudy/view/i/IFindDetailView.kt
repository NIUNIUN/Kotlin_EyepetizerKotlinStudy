package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/29.
 */
interface IFindDetailView : IBaseView {

    fun getCategoryDetailSuc(result: MutableList<HomeBean.IssueListBean.ItemListBean>)

    fun getCategoryDetailFail(code: Int, msg: String)
}