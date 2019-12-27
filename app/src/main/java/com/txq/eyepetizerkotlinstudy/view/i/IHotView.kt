package com.txq.eyepetizerkotlinstudy.view.i

import com.txq.base.view.IBaseView
import com.txq.eyepetizerkotlinstudy.bean.TabInfoBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IHotView : IBaseView {

    fun getDataListSuccess(result: TabInfoBean)
    fun getDataListFail(code: Int, msg: String)

}