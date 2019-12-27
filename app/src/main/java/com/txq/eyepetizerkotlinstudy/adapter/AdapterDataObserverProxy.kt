package com.txq.eyepetizerkotlinstudy.adapter

import android.support.v7.widget.RecyclerView

/** Paging库添加HeaderView、FootView
 * 另类的实现方式
 * https://juejin.im/post/5caa0052f265da24ea7d3c2c
 *
 * Created by tang_xqing on 2019/12/12.
 */
class AdapterDataObserverProxy() : RecyclerView.AdapterDataObserver() {

    private var mAdapterObserver: RecyclerView.AdapterDataObserver? = null
    private var mHeadeViewCount: Int = 0
    private var mFooterViewCount: Int = 0

    constructor(
        observer: RecyclerView.AdapterDataObserver,
        headeViewCount: Int = 0,
        footerViewCount: Int = 0
    ) : this() {
        mAdapterObserver = observer
        mHeadeViewCount = headeViewCount
        mFooterViewCount = footerViewCount
    }

    private fun getCustomView() = mHeadeViewCount + mFooterViewCount

    override fun onChanged() {
        mAdapterObserver?.onChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        mAdapterObserver?.onItemRangeChanged(positionStart+getCustomView(), itemCount)
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        mAdapterObserver?.onItemRangeChanged(positionStart+getCustomView(), itemCount, payload)
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        mAdapterObserver?.onItemRangeInserted(positionStart+getCustomView(), itemCount)
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        mAdapterObserver?.onItemRangeMoved(fromPosition+getCustomView(), toPosition+getCustomView(), itemCount)
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        mAdapterObserver?.onItemRangeRemoved(positionStart+getCustomView(), itemCount)
    }
}