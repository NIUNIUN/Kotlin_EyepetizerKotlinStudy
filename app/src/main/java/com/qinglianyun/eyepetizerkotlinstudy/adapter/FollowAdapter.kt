package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.base.utils.GlideUtils
import com.qinglianyun.eyepetizerkotlinstudy.view.VideoDetailActivity
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FollowAdapter(
    ctx: Context,
    lists: MutableList<HomeBean.IssueListBean.ItemListBean>,
    rvView: RecyclerView
) : BaseClickAdapter<HomeBean.IssueListBean.ItemListBean, FollowAdapter.FollowViewHodler>(
    ctx,
    lists,
    rvView
) {

    fun setDataList(result: MutableList<HomeBean.IssueListBean.ItemListBean>) {
        result?.let {
            mList.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHodler {
        var inflate = LayoutInflater.from(mCtx).inflate(R.layout.item_follow_layout, parent, false)
        return FollowViewHodler(inflate)
    }

    override fun onBindViewHolder(holder: FollowViewHodler, position: Int) {

        var bean = mList.get(position).data?.header
        holder.run {
            bean?.run {
                GlideUtils.displayCircle(mCtx, icon, ivAvatar)

                TextUtils.setText(tvTitle, title)
                TextUtils.setText(tvDesc, description)

                RecyclerViewUtils.initHorizontalLayoutManager(rvData, mCtx)
            }
            mList.get(position).data?.let {
                var adater = FollowChildAdater(mCtx, it.itemList.toMutableList(), rvData)
                rvData.adapter = adater
                adater.setRvClickListener(object :
                    OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
                    override fun onItemClick(
                        view: View,
                        data: HomeBean.IssueListBean.ItemListBean,
                        position: Int
                    ) {
                        // 调转到视频播放页
                        jumpToVideo(view, data)
                    }
                })
            }
        }
    }

    fun jumpToVideo(view: View, data: HomeBean.IssueListBean.ItemListBean) {
        // 转换成VideoBean
        data.data?.run {
            var feed = cover?.feed as String
            var blurred = cover?.blurred
            var shared = consumption?.shareCount
            var reply = consumption?.replyCount
            var collect = consumption?.collectionCount
            var videoBean: VideoBean = VideoBean(feed,title,description,duration,playUrl,category,blurred,collect,shared,reply,System.currentTimeMillis())
            VideoDetailActivity.startAction(mCtx, videoBean, view)
        }
    }

    class FollowViewHodler(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var ivAvatar: ImageView
        lateinit var tvTitle: TextView
        lateinit var tvDesc: TextView
        lateinit var tvFocus: TextView
        lateinit var rvData: RecyclerView

        init {
            itemView?.run {
                ivAvatar = findViewById(R.id.item_iv_avatar)
                tvTitle = findViewById(R.id.item_tv_title)
                tvDesc = findViewById(R.id.item_tv_desc)
                tvFocus = findViewById(R.id.item_tv_focus)
                rvData = findViewById(R.id.item_rv_data)
            }
        }
    }
}