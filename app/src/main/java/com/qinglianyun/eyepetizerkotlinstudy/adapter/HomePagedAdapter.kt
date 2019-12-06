package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.arch.lifecycle.Observer
import android.arch.paging.*
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.eyepetizerkotlinstudy.MainActivity
import com.qinglianyun.eyepetizerkotlinstudy.MyApplication
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.viewmodels.HomeViewModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/6.
 */
class HomePagedAdapter(difCallback: DiffUtil.ItemCallback<HomeBean.IssueListBean.ItemListBean>) :
    PagedListAdapter<HomeBean.IssueListBean.ItemListBean, HomePagedAdapter.HomeViewHolder>(
        difCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePagedAdapter.HomeViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_layout, parent, false)
        return HomePagedAdapter.HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var dataBean = getItem(position)?.data
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

//        GlideUtils.display(mCtx, dataBean?.cover?.feed as String, holder.mIvPhoto)
//        GlideUtils.displayCircle(mCtx, dataBean?.author?.icon as String, holder.mIvImg)
        TextUtils.setText(holder.mTvTitle, dataBean?.title ?: "--")
        TextUtils.setText(holder.mTvTime, "发布于 $category / $realMinute:$realSecond")
    }

    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var mIvPhoto: ImageView
        lateinit var mIvImg: ImageView
        lateinit var mTvTitle: TextView
        lateinit var mTvTime: TextView

        init {
            itemView?.run {
                mIvPhoto = findViewById(R.id.item_iv_photo)
                mIvImg = findViewById(R.id.item_iv_img)
                mTvTime = findViewById(R.id.item_tv_time)
                mTvTitle = findViewById(R.id.item_tv_title)
            }
        }
    }

    companion object {
        public val difCallback = object :
            DiffUtil.ItemCallback<HomeBean.IssueListBean.ItemListBean>() {
            override fun areItemsTheSame(
                oldItem: HomeBean.IssueListBean.ItemListBean?,
                newItem: HomeBean.IssueListBean.ItemListBean?
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: HomeBean.IssueListBean.ItemListBean?,
                newItem: HomeBean.IssueListBean.ItemListBean?
            ): Boolean = oldItem == newItem
        }
    }
}

fun getList() {
    var activity = MainActivity()
    var adapter: HomePagedAdapter = HomePagedAdapter(HomePagedAdapter.difCallback)
    var viewmodel = HomeViewModel(MyApplication())
    viewmodel.getPagedListBuilder()
        .observe(activity, object : Observer<PagedList<HomeBean.IssueListBean.ItemListBean>> {
            override fun onChanged(t: PagedList<HomeBean.IssueListBean.ItemListBean>?) {
                adapter.submitList(t)
            }
        })
}