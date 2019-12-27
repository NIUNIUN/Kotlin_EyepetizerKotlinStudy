package com.txq.eyepetizerkotlinstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.txq.base.adapter.BaseClickAdapter
import com.txq.base.utils.RecyclerViewUtils
import com.txq.base.utils.TextUtils
import com.txq.eyepetizerkotlinstudy.R
import com.txq.base.utils.GlideUtils
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

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

    private var onFollowListener: (position: Int) -> Unit = {}
    private var onJumpListener: (view: View, data: HomeBean.IssueListBean.ItemListBean) -> Unit =
        { view, data -> }


    fun setListenerFollow(listener: (Int) -> Unit) {
        onFollowListener = listener
    }

    fun setListenerJump(listener: (View, HomeBean.IssueListBean.ItemListBean) -> Unit) {
        onJumpListener = listener
    }

    fun setDataList(result: MutableList<HomeBean.IssueListBean.ItemListBean>) {
        mList.addAll(result)
        notifyDataSetChanged()
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
                        onJumpListener.invoke(view, data)
                    }
                })
            }

            tvFocus.setOnClickListener { onFollowListener.invoke(position) }
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


    /**
     * 传统写法（java写法）步骤：
     * 1、声明interface
     * 2、定义一个接口对象
     * 3、定义一个方法，用来外部调用，给接口对象赋值
     * 4、调用接口对象的方法，外部回调（及时响应）
     *
     *
     * kotlion 高阶函数写法步骤： （既然要使用lambda表达式，那么方法的参数必须要是函数） 优势：没有接口定义，没有匿名内部类
     * 1、定义变量--函数   （相当于声明interface、定义接口对象）
     * 2、定义方法，用于外部调用，给变量赋值
     * 3、调用函数变量，外部回调方法（及时响应）
     */
}