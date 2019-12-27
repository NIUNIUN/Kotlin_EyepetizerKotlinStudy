package com.txq.eyepetizerkotlinstudy.view.i

import com.txq.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/11.
 */
interface IFollowView : IBaseView {

    fun getFollowDataSuc(result: HomeBean.IssueListBean)
    fun getFollowDataFail(code: Int, msg: String)

}