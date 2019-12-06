package com.qinglianyun.eyepetizerkotlinstudy.net

import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by tang_xqing on 2019/11/12.
 */
interface ApiService {

    companion object {
        val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    }

    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Call<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Call<HomeBean>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Call<MutableList<FindBean>>

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList(): Call<TabInfoBean>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Call<HotBean>

    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query: String): Call<HomeBean.IssueListBean>

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWord(): Call<ArrayList<String>>


    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Call<HomeBean.IssueListBean>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getCategoryDataMore(@Url url: String): Call<HomeBean.IssueListBean>

    /**
     * 下载文件。
     * 一定添加@Streaming,防止内存溢出。因为Retrofit会把response的内容缓存到内存，添加Streaming注解会立即缓存到磁盘
     */
    @Streaming
    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>

    // 首页
    @GET("v2/feed?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(@Query("num") num: Int): Call<HomeBean>

    @GET("v2/feed")
    fun getHomeMoreData(@Query("data") data: String, @Query("num") num: Int): Call<HomeBean>

    // 发现视频信息
    @GET("v2/categories?uuid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData()

    @GET("v4/categories/videoList/?")
    fun requestFindeDetail()

    // 热门排行strategy = weekly,monthly ,historical
    @GET("v3/rankList?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun requestRanklist(@Query("num") num: Int, @Query("strategy") strategy: String)

    // 视频
    @GET("v3/videos")
    fun requestViedeos()

    // 热门搜索关键词
    @GET("v3/queries/hot?uuid=26868b32e808498db32fd51fb422d00175e179df")
    fun requestHotQueries()

    // 搜索
    @GET("v1/search?uuid=26868b32e808498db32fd51fb422d00175e179df")
    fun requestSerach(@Query("num") num: Int, @Query("start") start: Int, @Query("query") query: String)

    // 关注
    @GET("v4/tabs/follow?uuid=26868b32e808498db32fd51fb422d00175e179df")
    fun requestFollow()

    // 作者信息
    @GET("v4/pgcs/detail/tab?id=571")
    fun requestPqcsDetail()


}