package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.base.utils.GlideUtils
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/25.
 */
class HomeAdapter(
    ctx: Context,
    dataList: MutableList<HomeBean.IssueListBean.ItemListBean>,
    rvView: RecyclerView
) :
    BaseClickAdapter<HomeBean.IssueListBean.ItemListBean, HomeAdapter.HomeViewHolder>(
        ctx,
        dataList,
        rvView
    ) {

    fun setDataList(dataList: MutableList<HomeBean.IssueListBean.ItemListBean>) {
        mList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        var view = LayoutInflater.from(mCtx).inflate(R.layout.item_home_layout, parent, false)
        view.setOnClickListener(this)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        mList?.let {
            var dataBean = it.get(position).data
            var category = dataBean?.category
            var minute = dataBean?.duration?.div(60)
            var second = dataBean?.duration?.minus((minute?.times(60)) as Long)
            var realMinute: String
            var realSecond: String
            if (minute!! < 10) {
                realMinute = "0" + minute
            } else {
                realMinute = minute.toString()
            }
            if (second!! < 10) {
                realSecond = "0" + second
            } else {
                realSecond = second.toString()
            }

            GlideUtils.display(mCtx, dataBean?.cover?.feed as String, holder.mIvPhoto)
            GlideUtils.displayCircle(
                mCtx,
                dataBean?.author?.icon as String,
                holder.mIvImg,
                R.mipmap.default_avatar,
                R.mipmap.default_avatar
            )
            TextUtils.setText(holder.mTvTitle, dataBean?.title ?: "--")
            TextUtils.setText(holder.mTvTime, "发布于 $category / $realMinute:$realSecond")
        }
    }

    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var mIvPhoto: ImageView
        lateinit var mIvImg: ImageView
        lateinit var mTvTitle: TextView
        lateinit var mTvTime: TextView

        init {
            itemView?.run {
                mIvPhoto = findViewById(R.id.item_iv_photo)
                mIvImg = findViewById(R.id.item_iv_avatar)
                mTvTime = findViewById(R.id.item_tv_time)
                mTvTitle = findViewById(R.id.item_tv_title)
            }
        }
    }
}