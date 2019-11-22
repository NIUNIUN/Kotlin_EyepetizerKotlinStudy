package com.qinglianyun.base.net

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by tang_xqing on 2019/11/12.
 */
interface ApiService {

    companion object {
        val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    }

    // 首页
    @GET("v2/feed?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun requestData(@Query("num") num: Int)

//    @GET("v2/feed")
//    fun requestMoreData(@Query("data") data: String, @Query("num") num: Int)

    // 发现视频信息
    @GET("v2/categories?uuid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun requestFindData()

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