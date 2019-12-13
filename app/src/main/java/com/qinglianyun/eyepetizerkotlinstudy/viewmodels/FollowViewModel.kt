package com.qinglianyun.eyepetizerkotlinstudy.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.*
import com.qinglianyun.base.bean.ErrorMessage
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.eyepetizerkotlinstudy.model.FollowModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean

/** “关注”页 使用Paging库拉取更多数据
 * Created by tang_xqing on 2019/12/11.
 */
class FollowViewModel(application: Application) : AndroidViewModel(application) {

    private val mModel: FollowModel by lazy {
        FollowModel()
    }

    fun getLivePageList(): LiveData<PagedList<HomeBean.IssueListBean.ItemListBean>> {
        var builder = LivePagedListBuilder(getFactory(), getPageListConfig())
            .setInitialLoadKey("初始值")   // 初始化Key
        return builder.build()
    }

    /**
     * 设置分页配置
     */
    fun getPageListConfig(): PagedList.Config {
        var builder = PagedList.Config.Builder()
        builder.setEnablePlaceholders(false)  // 占位符
            .setPageSize(10)  // 每页加载的个数
            .setInitialLoadSizeHint(10)  // 初始化加载的个数
            .setPrefetchDistance(4)  //距离底部多少条数据，加载下一页数据

        return builder.build()
    }

    /**
     * 分页工厂
     */
    fun getFactory(): DataSource.Factory<String, HomeBean.IssueListBean.ItemListBean> {
        var factory = object : DataSource.Factory<String, HomeBean.IssueListBean.ItemListBean>() {
            override fun create(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
                return getDataSource()
            }
        }
        return factory
    }

    /**
     * 分页数据源管理
     */
    fun getDataSource(): DataSource<String, HomeBean.IssueListBean.ItemListBean> {
        var dataSource =
            object : PageKeyedDataSource<String, HomeBean.IssueListBean.ItemListBean>() {

                /**
                 * 初始化请求
                 */
                override fun loadInitial(
                    params: LoadInitialParams<String>,
                    callback: LoadInitialCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadInitial() placeholdersEnabled = ${params.placeholdersEnabled}, requestedLoadSize = ${params.requestedLoadSize}")
                    loadCallFirst(callback)
                }

                /**
                 * 往上滑，加载后一页数据。方向：下->上
                 */
                override fun loadAfter(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadAfter() ${params.key}")
                    loadCallMore(params.key, callback)
                }

                /**
                 * 往下拉，加载前一页数据。方向：上->下
                 */
                override fun loadBefore(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
                ) {
                    printLog("loadBefore() ${params.key}")
//                    loadCallMore(params.key,callback)
                }
            }
        return dataSource
    }


    fun loadCallFirst(callback: PageKeyedDataSource.LoadInitialCallback<String, HomeBean.IssueListBean.ItemListBean>) {
        mModel.getFollowData(object : ICallback<HomeBean.IssueListBean> {
            override fun onSuccess(result: HomeBean.IssueListBean) {
                printLog("loadCallFirst() --> onSuccess() ")
                result.itemList?.let {
                    callback.onResult(it, null, result.nextPageUrl)
                }
            }

            override fun onError(error: ErrorMessage) {

            }
        })
    }

    fun loadCallMore(
        url: String,
        callback: PageKeyedDataSource.LoadCallback<String, HomeBean.IssueListBean.ItemListBean>
    ) {
        mModel.getFollowDataMore(url,
            object : ICallback<HomeBean.IssueListBean> {
                override fun onSuccess(result: HomeBean.IssueListBean) {
                    printLog("loadCallMore() --> onSuccess() ")
                    result.itemList?.let {
                        callback.onResult(it, result.nextPageUrl)
                    }
                }

                override fun onError(error: ErrorMessage) {

                }
            })
    }

    fun printLog(content: String) {
        println("分页请求 $content")
    }
}