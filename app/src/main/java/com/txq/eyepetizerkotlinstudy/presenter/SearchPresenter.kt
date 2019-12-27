package com.txq.eyepetizerkotlinstudy.presenter

import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.base.view.BasePresenter
import com.txq.eyepetizerkotlinstudy.model.SearchModel
import com.txq.eyepetizerkotlinstudy.view.i.ISearchView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/28.
 */
class SearchPresenter(view: ISearchView) : BasePresenter<ISearchView>(view) {
    private val mModel: SearchModel by lazy {
        SearchModel()
    }

    private var mNextUrl: String? = null

    fun getSearchDataByKey(query: String) {
        mModel.getSearchDataByKey(query, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                mNextUrl = result.nextPageUrl
                baseView?.getSearchByKeySuc(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getSearchByKeyFail(error.code, error.message)
            }
        })
    }

    fun getSearchMoreData() {
        mNextUrl?.let {
            mModel.getMoreSearchData(it, object : ICallback<HomeBean.IssueListBean> {
                override fun onSuccess(result: HomeBean.IssueListBean) {
                    mNextUrl = result.nextPageUrl
                    baseView?.getSearchByKeySuc(result)
                }

                override fun onError(error: ErrorMessage) {
                    baseView?.getSearchByKeyFail(error.code, error.message)
                }
            })
        }
    }

    fun getHotWord() {
        mModel.getHotWord(object : ICallback<ArrayList<String>> {
            override fun onSuccess(result: ArrayList<String>) {
                baseView?.getHotWorldSuccess(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getHotWorldFail(error.code, error.message)
            }
        })
    }
}