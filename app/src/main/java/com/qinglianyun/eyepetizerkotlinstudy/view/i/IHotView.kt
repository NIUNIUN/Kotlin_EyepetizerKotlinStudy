package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IHotView : IBaseView {

    fun getDataListSuccess(result: TabInfoBean)
    fun getDataListFail(code: Int, msg: String)

}