package com.txq.eyepetizerkotlinstudy.view.i

import com.txq.base.view.IBaseView
import com.txq.eyepetizerkotlinstudy.bean.db.VideoTb

/**
 * Created by tang_xqing on 2019/12/4.
 */
interface IVideoCacheView : IBaseView {
    fun getAllVideoCacheSuc(result: MutableList<VideoTb>)
    fun getAllVideoCacheFail(code:Int,msg:String)
}