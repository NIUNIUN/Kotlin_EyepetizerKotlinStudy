package com.qinglianyun.eyepetizerkotlinstudy.presenter

import android.content.Context
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.bean.db.VideoDbManager
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IVideoCacheView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tang_xqing on 2019/12/4.
 */
class VideoCachePresenter(view: IVideoCacheView) : BasePresenter<IVideoCacheView>(view) {

    fun getAllVideoCache(ctx: Context) {
        VideoDbManager.getInstance(ctx).getAllvideo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                baseView?.getAllVideoCacheSuc(it.toMutableList())
            })
    }
}