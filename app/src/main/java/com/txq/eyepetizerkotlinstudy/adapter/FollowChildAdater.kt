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
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FollowChildAdater(
    ctx: Context,
    lists: MutableList<HomeBean.IssueListBean.ItemListBean>,
    rvView: RecyclerView
) : BaseClickAdapter<HomeBean.IssueListBean.ItemListBean, FollowChildAdater.ViewHolder>(
    ctx,
    lists,
    rvView
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mCtx).inflate(R.layout.item_child_follow_layout, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = mList.get(position).data

        holder.run {
            data?.run {
                GlideUtils.display(mCtx, cover?.feed as String, ivImage)
                TextUtils.setText(tvTile, title as String)

                var minute = duration?.div(60)
                var second = duration?.minus(minute?.times(60) as Long)
                var realMinute: String = if (minute!! < 10) "0$minute" else minute.toString()
                var realSecond = if (second!! < 10) "0$second" else second.toString()

                var tag =
                    if (tags.size > 0) "${tags[0].name} / $realMinute '$realSecond''" else "$realMinute '$realSecond''"
                TextUtils.setText(tvTime, tag)
            }
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var ivImage: ImageView
        lateinit var tvTile: TextView
        lateinit var tvTime: TextView

        init {
            itemView?.run {
                ivImage = findViewById(R.id.item_iv_cover_feed)
                tvTile = findViewById(R.id.item_tv_title)
                tvTime = findViewById(R.id.item_tv_time)
            }
        }
    }
}