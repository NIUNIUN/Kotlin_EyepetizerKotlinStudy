package com.txq.base.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by tang_xqing on 2019/11/25.
 */
abstract class BaseClickAdapter<B, VH : RecyclerView.ViewHolder>(
    ctx: Context,
    lists: MutableList<B>,
    rvView: RecyclerView
) : RecyclerView.Adapter<VH>(),
    View.OnClickListener, View.OnLongClickListener {
    protected var mList: MutableList<B>
    protected var mCtx: Context
    protected var mRvView: RecyclerView
    private var mClickListener: OnRvClickListener<B>? = null
    private var mLongClickListener: OnRvLongClickListener<B>? = null

    init {
        mList = lists
        mCtx = ctx
        mRvView = rvView
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }


    override fun onLongClick(v: View?): Boolean {
        mLongClickListener?.let {
            v?.let {
                mRvView?.let {
                    var position = it.getChildAdapterPosition(v)
                    mList?.let {
                        var data: B = it.get(position)
                        mLongClickListener?.onItemLongClick(v, data, position)
                    }
                }
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        mClickListener?.let {
            v?.let {
                mRvView?.let {
                    var position = it.getChildAdapterPosition(v)
                    mList?.let {
                        var data: B = it.get(position)
                        mClickListener?.onItemClick(v, data, position)
                    }
                }
            }
        }
    }


    fun setRvClickListener(listener: OnRvClickListener<B>) {
        mClickListener = listener
    }

    fun setRvLongClickListener(listener: OnRvLongClickListener<B>) {
        mLongClickListener = listener
    }

    interface OnRvClickListener<T> {
        fun onItemClick(view: View, data: T, position: Int)
    }

    interface OnRvLongClickListener<T> {
        fun onItemLongClick(view: View, data: T, position: Int)
    }
}