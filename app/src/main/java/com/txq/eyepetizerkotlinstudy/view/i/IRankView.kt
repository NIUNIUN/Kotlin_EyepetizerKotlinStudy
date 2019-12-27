package com.txq.eyepetizerkotlinstudy.view.i

import com.txq.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IRankView : IBaseView {
    fun requestRankListSuc(result: HotBean)
    fun requestRankListFail(code: Int, msg: String)
}