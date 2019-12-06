package com.qinglianyun.eyepetizerkotlinstudy.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.*
import android.util.Log
import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.eyepetizerkotlinstudy.model.HomeModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by tang_xqing on 2019/12/6.
 *
 * ItemKeyedDataSource：往上拉根据最后一条数据的key；往下拉根据最顶上的一条数据的key
 *
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    // 下一页请求的Url
    private var mNextUrl: String? = null

    /**
     * 获取
     */
    fun getPagedListBuilder(): LiveData<PagedList<HomeBean.IssueListBean.ItemListBean>> {
        var dataFactory =
            object : DataSource.Factory<String, HomeBean.IssueListBean.ItemListBean>() {
                override fun create(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
                    return getItemKeyedDataSource()
                }
            }

        var config = PagedList.Config.Builder()
            .setPageSize(10) // 每个显示数据个数
            .setEnablePlaceholders(false) // 是否显示占位。显示占位，需要固定每页的个数
            .setInitialLoadSizeHint(5)  //初始化加载个数
//            .setPrefetchDistance()
            .build()

        return LivePagedListBuilder(dataFactory, config).build()
    }

    fun getItemKeyedDataSource(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
        val dataSourceFacoty =
            object : ItemKeyedDataSource<String, HomeBean.IssueListBean.ItemListBean>() {

                /**
                 * 第一次加载RecycerView时自动调用
                 */
                override fun loadInitial(
                    params: LoadInitialParams<String>,
                    callback: LoadInitialCallback<HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadInitial() ${params.requestedInitialKey}")
                    requestFirst(callback)
                }

                /*往下滑动加载的数据 根据最底部的数据的key 加载数据*/
                override fun loadAfter(
                    params: LoadParams<String>,
                    callback: LoadCallback<HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadAfter() ${params.key}")
                }

                /*往上滑的时候加载的数据 根据最顶部的数据的key加载数据*/
                override fun loadBefore(
                    params: LoadParams<String>,
                    callback: LoadCallback<HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadBefore() ${params.key}")
                    // 请求下一页数据
                    params.key
                    requestMore(callback)
                }

                //这里返回的key就是上边方法里LoadParams的key
                override fun getKey(item: HomeBean.IssueListBean.ItemListBean): String {
                    printLog("getKey() ${item.data?.title}")
                    return item.data?.playUrl as String
                }
            }

        return dataSourceFacoty
    }

    /**
     * 每页个数num
     */
    fun getPageKeyedDataSource(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
        val tempSource =
            object : PageKeyedDataSource<String, HomeBean.IssueListBean.ItemListBean>() {
                override fun loadInitial(
                    params: LoadInitialParams<String>,
                    callback: LoadInitialCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {

                }

                override fun loadAfter(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {
                }

                override fun loadBefore(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {
                }
            }

        return tempSource
    }

    /**
     * 一般通过pageLength,Page
     */
    fun getPositionalDataSource(): PositionalDataSource<HomeBean.IssueListBean.ItemListBean> {
        var tem = object : PositionalDataSource<HomeBean.IssueListBean.ItemListBean>() {

            override fun loadRange(
                params: LoadRangeParams,
                callback: LoadRangeCallback<HomeBean.IssueListBean.ItemListBean>
            ) {
                //  params.loadSize--每页加载的个数  params.startPosition--开始位置

            }

            /**
             * 初始化加载
             */
            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<HomeBean.IssueListBean.ItemListBean>
            ) {
            }
        }

        return tem
    }

    /**
     * Retrofit请求
     */
    fun requestFirst(callback: ItemKeyedDataSource.LoadInitialCallback<HomeBean.IssueListBean.ItemListBean>) {
        mModel.getHomeFirstData(1, object : ICallback<HomeBean> {
            override fun onSuccess(result: HomeBean) {
                result?.let {
                    mNextUrl = it.nextPageUrl
                    var temp: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
                    it.issueList!!
                        .flatMap { it.itemList!! }
                        .filter { it.type.equals("video") }
                        .forEach { temp.add(it) }
                    callback.onResult(temp)
                }
            }

            override fun onError(error: ErrorMessage) {

            }
        })
    }

    /**
     * Retrofit请求
     */
    fun requestMore(callback: ItemKeyedDataSource.LoadCallback<HomeBean.IssueListBean.ItemListBean>) {
        mNextUrl?.let {
            mModel.getHomeMoreData(it, object : ICallback<HomeBean> {
                override fun onError(error: ErrorMessage) {

                }

                override fun onSuccess(result: HomeBean) {
                    result?.let {
                        mNextUrl = it.nextPageUrl
                        var temp: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()
                        it.issueList!!
                            .flatMap { it.itemList!! }
                            .filter { it.type.equals("video") }
                            .forEach { temp.add(it) }
                        callback.onResult(temp)
                    }
                }
            })
        }
    }


    private fun printLog(content: String) {
        Log.d("HomeViewModel", content)
    }
}