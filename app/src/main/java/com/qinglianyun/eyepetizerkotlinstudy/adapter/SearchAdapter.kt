package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/28.
 */
class SearchAdapter(
    ctx: Context,
    lists: MutableList<String>,
    rvView: RecyclerView
) : BaseClickAdapter<String, SearchAdapter.SearchViewHolder>(
    ctx,
    lists,
    rvView
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        var inflate =
            LayoutInflater.from(mCtx).inflate(R.layout.item_hot_search_layout, parent, false)
        inflate.setOnClickListener(this)
        return SearchViewHolder(inflate)
    }

    fun setDataList(dataList: ArrayList<String>) {
        mList.clear()
        mList.addAll(dataList.toMutableList())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TextUtils.setText(holder.tvTitle, mList.get(position))
    }

    class SearchViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView

        init {
            tvTitle = itemView?.findViewById(R.id.item_tv_search) as TextView
        }
    }
}