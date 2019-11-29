package com.qinglianyun.eyepetizerkotlinstudy.model

import com.qinglianyun.base.net.BaseCallback
import com.qinglianyun.base.net.ICallback
import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import retrofit2.CallAdapter

/**
 * Created by tang_xqing on 2019/11/26.
 */
class HomeModel : BaseModel() {

    fun getHomeFirstData(num: Int, callListener: ICallback<HomeBean>) {
        mApiString.getFirstHomeData(num)
            .enqueue(BaseCallback(callListener))
    }

    fun getHomeMoreData(data: String, callListener: ICallback<HomeBean>) {
        mApiString
            .getMoreHomeData(data)
            .enqueue(BaseCallback(callListener))
    }
}

class FoundModel : BaseModel() {
    fun getCategory(callListener: ICallback<MutableList<FindBean>>) {
        mApiString.getCategory()
            .enqueue(BaseCallback(callListener))
    }

    fun getCategoryDetailList(id: Long, callListener: ICallback<HomeBean>) {
        mApiString.getCategoryDetailList(id)
            .enqueue(BaseCallback(callListener))

    }

    fun getCategoryDataMore(url: String, callListener: ICallback<HomeBean.IssueListBean>) {
        mApiString.getCategoryDataMore(url)
            .enqueue(BaseCallback(callListener))
    }
}

class RankModel : BaseModel() {

    fun getRankList(callListener: ICallback<TabInfoBean>) {
        mApiString.getRankList().enqueue(BaseCallback(callListener))
    }

    fun requestRankList(apiUri: String, callListener: ICallback<HotBean>) {
        mApiString.getIssueData(apiUri)
            .enqueue(BaseCallback(callListener))
    }

}

class SearchModel : BaseModel() {

    fun getSearchDataByKey(query: String, callListener: ICallback<HomeBean.IssueListBean>) {
        mApiString.getSearchData(query).enqueue(BaseCallback(callListener))
    }

    fun getHotWord(callListener: ICallback<ArrayList<String>>) {
        mApiString.getHotWord()
            .enqueue(BaseCallback(callListener))
    }
}