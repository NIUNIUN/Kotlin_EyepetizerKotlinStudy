package com.qinglianyun.eyepetizerkotlinstudy.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.utils.Utils
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.base.utils.GlideUtils
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/11.
 */
class FollowPagingAdapter(diffCallback: DiffUtil.ItemCallback<HomeBean.IssueListBean.ItemListBean>) :
    PagedListAdapter<HomeBean.IssueListBean.ItemListBean, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    private var mListener: OnFollowClickListener? = null

    fun setFollowClickListener(listener: OnFollowClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_follow_layout, parent, false)
        return when (viewType) {
            HEAD_VIEW_TYPE -> getHeaderHolder(parent.context)
            FOOT_VIEW_TYPE -> getFootHolder(parent.context)
            else -> ViewHolder(inflate)
        }
//        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            var bean = getItem(position)?.data?.header
            holder.run {
                bean?.run {
                    GlideUtils.displayCircle(
                        Utils.getContext(),
                        icon,
                        ivAvatar,
                        R.mipmap.default_avatar,
                        R.mipmap.default_avatar
                    )

                    TextUtils.setText(tvTitle, title)
                    TextUtils.setText(tvDesc, description)

                    RecyclerViewUtils.initHorizontalLayoutManager(rvData, Utils.getContext())
                }
                getItem(position)?.data?.let {
                    var adater =
                        FollowChildAdater(Utils.getContext(), it.itemList.toMutableList(), rvData)
                    rvData.adapter = adater
                    adater.setRvClickListener(object :
                        BaseClickAdapter.OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
                        override fun onItemClick(
                            view: View,
                            data: HomeBean.IssueListBean.ItemListBean,
                            position: Int
                        ) {

                            mListener?.let {
                                it.onClickChild(view, data)
                            }
                        }
                    })
                }
                tvFocus.setOnClickListener {
                    mListener?.run {
                        getItem(position)?.run {
                            onClickFollow(tvFocus, this)
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEAD_VIEW_TYPE
            (itemCount - 1) -> FOOT_VIEW_TYPE
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 2
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(AdapterDataObserverProxy(observer, 1, 1))
    }

    private fun getHeaderHolder(context: Context): RecyclerView.ViewHolder {
        var textView = TextView(context)
        textView.setText("                     ~~~我是顶部占位符 (✿◡‿◡)~~~")
        return object : RecyclerView.ViewHolder(textView) {
        }
    }

    private fun getFootHolder(context: Context): RecyclerView.ViewHolder {
        var textView = TextView(context)
        textView.setText("                   ~~~我是顶部占位符╰(*°▽°*)╯~~~")
        return object : RecyclerView.ViewHolder(textView) {
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
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

    companion object {

        const val HEAD_VIEW_TYPE = -2
        const val FOOT_VIEW_TYPE = -3

        val diffCallback = object : DiffUtil.ItemCallback<HomeBean.IssueListBean.ItemListBean>() {
            override fun areItemsTheSame(
                oldItem: HomeBean.IssueListBean.ItemListBean?,
                newItem: HomeBean.IssueListBean.ItemListBean?
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HomeBean.IssueListBean.ItemListBean?,
                newItem: HomeBean.IssueListBean.ItemListBean?
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnFollowClickListener {
        fun onClickFollow(view: View, data: HomeBean.IssueListBean.ItemListBean)
        fun onClickChild(view: View, data: HomeBean.IssueListBean.ItemListBean)
    }
}