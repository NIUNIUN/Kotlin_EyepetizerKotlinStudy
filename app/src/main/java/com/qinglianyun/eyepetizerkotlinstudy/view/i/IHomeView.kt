package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
interface IHomeView:IBaseView {

    fun getDataSuccess(dataBean:HomeBean)

    fun getDataFail(code:Int,msg:String)

}