package com.qinglianyun.eyepetizerkotlinstudy.view.i

import com.qinglianyun.base.view.IBaseView
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoTb

/**
 * Created by tang_xqing on 2019/12/4.
 */
interface IVideoCacheView : IBaseView {
    fun getAllVideoCacheSuc(result: MutableList<VideoTb>)
    fun getAllVideoCacheFail(code:Int,msg:String)
}