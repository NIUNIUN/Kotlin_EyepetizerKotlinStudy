package com.qinglianyun.eyepetizerkotlinstudy.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewGridDivier
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.CategoryAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.CategoryPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ICategoryView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.CategoryBean

/**
 * Created by tang_xqing on 2019/11/22.
 */
class CategoryFragment : BaseFragment<ICategoryView, CategoryPresenter>(), ICategoryView {
    private lateinit var mRlList: RecyclerView
    private lateinit var mAdapter: CategoryAdapter
    private var mDataList: MutableList<CategoryBean> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initPresenter() {
        mPresenter = CategoryPresenter(this)
    }

    override fun initViews() {
        mRlList = mActivity.findViewById(R.id.rl_found_list)
        RecyclerViewUtils.initGridLayoutManager(mRlList, mActivity, 2)
        mRlList.addItemDecoration(RecyclerViewGridDivier())
        mAdapter = CategoryAdapter(mActivity, mDataList, mRlList)
        mRlList.adapter = mAdapter
    }

    override fun initListeners() {
        mAdapter.setRvClickListener(object : BaseClickAdapter.OnRvClickListener<CategoryBean> {
            override fun onItemClick(view: View, data: CategoryBean, position: Int) {
                Toast.makeText(mActivity, "点击 position = " + position, Toast.LENGTH_SHORT).show()
                CategoryDetailActivity.startAction(mActivity,data)
            }
        })
    }

    override fun initData() {
        mPresenter?.requestData()
    }

    override fun getDataSuccess(result: MutableList<CategoryBean>) {
        mAdapter.setData(result)
    }

    override fun getDataFail(code: Int, msg: String) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }
}