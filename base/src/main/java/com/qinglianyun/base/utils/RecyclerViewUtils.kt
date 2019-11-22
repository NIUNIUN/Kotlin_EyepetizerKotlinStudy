package com.qinglianyun.base.utils

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by tang_xqing on 2019/11/22.
 */
class RecyclerViewUtils {

    companion object {

        fun initVerticalLayoutManager(view: RecyclerView, ctx: Context) {
            var manager: LinearLayoutManager = LinearLayoutManager(ctx)
            manager.orientation = LinearLayoutManager.VERTICAL
            view.layoutManager = manager
        }

        fun initHorizontalLayoutManager(view: RecyclerView, ctx: Context) {
            var manager: LinearLayoutManager = LinearLayoutManager(ctx)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            view.layoutManager = manager
        }

        fun initGridLayoutManager(view: RecyclerView, ctx: Context, spannCount: Int) {
            var manager: GridLayoutManager = GridLayoutManager(ctx, spannCount)
            view.layoutManager = manager
        }
    }
}