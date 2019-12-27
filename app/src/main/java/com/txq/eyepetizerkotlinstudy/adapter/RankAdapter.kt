package com.txq.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.txq.base.adapter.BaseClickAdapter
import com.txq.base.utils.TextUtils
import com.txq.eyepetizerkotlinstudy.R
import com.txq.base.utils.GlideUtils
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean

/**
 * Created by tang_xqing on 2019/11/25.
 */
class RankAdapter(
    ctx: Context,
    dataList: MutableList<HotBean.ItemListBean.DataBean>,
    rvView: RecyclerView
) :
    BaseClickAdapter<HotBean.ItemListBean.DataBean, RankAdapter.RankViewHolder>(
        ctx,
        dataList,
        rvView
    ) {

    fun setDataList(dataList: MutableList<HotBean.ItemListBean.DataBean>) {
        mList.clear()
        mList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        var view = LayoutInflater.from(mCtx).inflate(R.layout.item_rank_layout, parent, false)
        view.setOnClickListener(this)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        var dataBean = mList.get(position)
        var minute = dataBean.duration.div(60)
        var second = dataBean.duration.minus(minute.times(60) as Long)
        var realMinute: String
        var realSecond: String

        if (minute < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }

        if (second < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }

        GlideUtils.display(mCtx, dataBean.cover?.feed as String, holder.ivPhoto)
        TextUtils.setText(holder.tvTitle, dataBean?.title as String)
        var detail = "${dataBean.category} / $realMinute'$realSecond\""
        TextUtils.setText(holder.tvDetail, detail)
    }

    class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var ivPhoto: ImageView
        lateinit var tvTitle: TextView
        lateinit var tvDetail: TextView

        init {
            itemView?.run {
                ivPhoto = findViewById(R.id.item_iv_photo)
                tvTitle = findViewById(R.id.item_tv_title)
                tvDetail = findViewById(R.id.item_tv_time)
            }
        }
    }
}