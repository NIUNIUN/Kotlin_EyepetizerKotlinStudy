package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IRankView : IBaseView {
    fun requestRankListSuc(result: HotBean)
    fun requestRankListFail(code: Int, msg: String)
}