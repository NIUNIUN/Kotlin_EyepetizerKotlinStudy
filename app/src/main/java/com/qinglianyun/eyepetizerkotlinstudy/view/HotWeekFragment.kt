package com.qinglianyun.eyepetizerkotlinstudy.view

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.RankAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.RankPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IRankView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.coverToVideoBean
import kotlinx.android.synthetic.main.fragment_hot_rank.*


/**
 * Created by tang_xqing on 2019/11/22.
 */
class HotWeekFragment : BaseFragment<IRankView, RankPresenter>(), IRankView {

    private lateinit var mRvData: RecyclerView

    private lateinit var mAdapter: RankAdapter

    private var mList: MutableList<HotBean.ItemListBean.DataBean> = mutableListOf()

    private var mAPiUri: String? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_rank
    }

    override fun initPresenter() {
        mPresenter = RankPresenter(this)
    }

    override fun initViews() {
        arguments?.let {
            mAPiUri = it.getString(EXTRA_DATA)
        }

//        mRvData = mActivity.findViewById(R.id.rv_rank_data)
//        RecyclerViewUtils.initVerticalLayoutManager(mRvData, mActivity)
//        mAdapter = RankAdapter(mActivity, mList, mRvData)
//        mRvData.adapter = mAdapter

//        mRvData = mActivity.findViewById(R.id.rv_rank_data)
        RecyclerViewUtils.initVerticalLayoutManager(rv_rank_data, mActivity)
        mAdapter = RankAdapter(mActivity, mList, rv_rank_data)
        rv_rank_data.adapter = mAdapter
    }

    override fun initListeners() {
        mAdapter.setRvClickListener(object :
            BaseClickAdapter.OnRvClickListener<HotBean.ItemListBean.DataBean> {
            override fun onItemClick(
                view: View,
                data: HotBean.ItemListBean.DataBean,
                position: Int
            ) {
                jumpToActivty(view, data)
            }
        })
    }

    override fun initData() {
        mAPiUri?.let {
            mPresenter?.requestRankList(it)
        }
    }

    override fun requestRankListSuc(result: HotBean) {
        var temp: MutableList<HotBean.ItemListBean.DataBean> = mutableListOf()
        result.run {
            itemList?.forEach {
                it.data?.let {
                    temp.add(it)
                }
            }
        }
        mAdapter.setDataList(temp)
    }

    override fun requestRankListFail(code: Int, msg: String) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show()
    }


    private fun jumpToActivty(view: View, data: HotBean.ItemListBean.DataBean) {
        VideoDetailActivity.startAction(mActivity, data.coverToVideoBean(), view)
    }

    companion object {
        const val EXTRA_DATA = "extra_api_string"
        fun getInstance(apiUri: String): HotWeekFragment {
            var hotWeekFragment = HotWeekFragment()
            var bundle: Bundle = Bundle()
            bundle.putString(EXTRA_DATA, apiUri)
            hotWeekFragment.arguments = bundle
            return hotWeekFragment
        }
    }
}