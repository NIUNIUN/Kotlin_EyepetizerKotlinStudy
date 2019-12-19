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
import com.tt.lvruheng.eyepetizer.mvp.model.bean.CategoryBean

/**
 * Created by tang_xqing on 2019/11/26.
 */
class CategoryAdapter(
    ctx: Context, data: MutableList<CategoryBean>, rvView: RecyclerView
) : BaseClickAdapter<CategoryBean, CategoryAdapter.FoundViewHolder>(ctx, data, rvView) {

    fun setData(dataList: MutableList<CategoryBean>) {
        mList.clear()
        mList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundViewHolder {
        var view =
            LayoutInflater.from(mCtx).inflate(R.layout.item_category_layout, parent, false)
        view.setOnClickListener(this)
        return FoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoundViewHolder, position: Int) {
        var findBean = mList.get(position)
        GlideUtils.displayRound(mCtx, findBean?.bgPicture as String, holder.ivPhoto)
        TextUtils.setText(holder.tvCategory, findBean?.name ?: "--")
    }

    class FoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPhoto: ImageView
        var tvCategory: TextView

        init {
            ivPhoto = itemView.findViewById(R.id.item_iv_photo)
            tvCategory = itemView.findViewById(R.id.item_tv_category)
        }
    }
}