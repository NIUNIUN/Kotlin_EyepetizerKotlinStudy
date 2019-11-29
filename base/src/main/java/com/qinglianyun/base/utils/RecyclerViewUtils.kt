package com.qinglianyun.base.utils

import android.content.Context
import android.graphics.Rect
import android.inputmethodservice.Keyboard
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.flexbox.*

/**
 * Created by tang_xqing on 2019/11/22.
 */
class RecyclerViewUtils {

    companion object {

        fun initVerticalLayoutManager(view: RecyclerView, ctx: Context) {
            var manager = LinearLayoutManager(ctx)
            manager.orientation = LinearLayoutManager.VERTICAL
            view.layoutManager = manager
        }

        fun initHorizontalLayoutManager(view: RecyclerView, ctx: Context) {
            var manager = LinearLayoutManager(ctx)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            view.layoutManager = manager
        }

        fun initGridLayoutManager(view: RecyclerView, ctx: Context, spannCount: Int) {
            var manager = GridLayoutManager(ctx, spannCount)
            view.layoutManager = manager
        }

        fun initFlexboxLayoutManager(view: RecyclerView, ctx: Context) {
            var manager = FlexboxLayoutManager(ctx)
            manager.flexWrap = FlexWrap.WRAP      //按正常方向换行
            manager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
            manager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
            manager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式
            view.layoutManager = manager
        }
    }
}


class RecyclerViewGridDivier : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect?,
        view: View?,
        parent: RecyclerView?,
        state: RecyclerView.State?
    ) {
        if (parent?.layoutManager is GridLayoutManager) {
            // 网络布局
            var layoutManager = parent?.layoutManager as GridLayoutManager
            var spanCount = layoutManager.spanCount
            var position = parent.getChildAdapterPosition(view)
            var childCount = parent.childCount
            var offset = 5
            if (position < spanCount) {
                outRect?.set(offset, offset, offset, offset / 2)
            } else {
                outRect?.set(offset, 0, offset, offset / 2)
            }

        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }

}