package com.qinglianyun.eyepetizerkotlinstudy.presenter

import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.base.view.BasePresenter
import com.qinglianyun.eyepetizerkotlinstudy.model.SearchModel
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ISearchView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/11/28.
 */
class SearchPresenter(view: ISearchView) : BasePresenter<ISearchView>(view) {
    private val mModel: SearchModel by lazy {
        SearchModel()
    }

    fun getSearchDataByKey(query: String) {
        mModel.getSearchDataByKey(query, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                baseView?.getSearchByKeySuc(result)
            }

            override fun onError(error: ErrorMessage) {
                baseView?.getSearchByKeyFail(error.code, error.message)
            }
        })
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

    fun requestHotWorld(){

    }
}