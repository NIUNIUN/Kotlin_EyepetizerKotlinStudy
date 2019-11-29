package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IFoundView : IBaseView {
    fun getDataSuccess(result: MutableList<FindBean>)
    fun getDataFail(code: Int, msg: String)

}