package com.txq.eyepetizerkotlinstudy.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.txq.base.bean.ErrorMessage
import com.txq.base.net.ICallback
import com.txq.eyepetizerkotlinstudy.model.CategoryModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/17.
 */
class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val mModel: CategoryModel by lazy {
        CategoryModel()
    }

    var mCategoryId: Long = -1L

    fun getCategoryLiveData(): LiveData<PagedList<HomeBean.IssueListBean.ItemListBean>> {
        val factory = getDataFactory()
        val config = PagedList.Config.Builder()
            .setPageSize(10)    // 必须设置
            .setEnablePlaceholders(false)
            .build()
        return LivePagedListBuilder<String, HomeBean.IssueListBean.ItemListBean>(
            factory,
            config
        ).setInitialLoadKey("initkey").build()
    }

    fun getDataFactory(): DataSource.Factory<String, HomeBean.IssueListBean.ItemListBean> {
        return object : DataSource.Factory<String, HomeBean.IssueListBean.ItemListBean>() {
            override fun create(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
                return getPageKeyDataSource()
            }
        }
    }

    fun getPageKeyDataSource(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
        return object : PageKeyedDataSource<String, HomeBean.IssueListBean.ItemListBean>() {
            override fun loadInitial(
                params: LoadInitialParams<String>,
                callback: LoadInitialCallback<String, HomeBean.IssueListBean.ItemListBean>
            ) {
                getCategoryList(callback)
            }

            override fun loadAfter(
                params: LoadParams<String>,
                callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
            ) {
                getCategoryMore(params.key, callback)
            }

            override fun loadBefore(
                params: LoadParams<String>,
                callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
            ) {
            }
        }
    }

    private fun getCategoryList(callback: PageKeyedDataSource.LoadInitialCallback<String, HomeBean.IssueListBean.ItemListBean>) {
        mModel.getCategoryDetailList(mCategoryId, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                result.itemList?.let {
                    callback.onResult(it, null, result.nextPageUrl)
                }
            }

            override fun onError(error: ErrorMessage) {
            }
        })
    }

    private fun getCategoryMore(
        key: String,
        callback: PageKeyedDataSource.LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
    ) {
        mModel.getCategoryDataMore(key, object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                result.itemList?.let {
                    callback.onResult(it, result.nextPageUrl)
                }
            }

            override fun onError(error: ErrorMessage) {

            }
        })
    }
}