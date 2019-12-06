package com.qinglianyun.eyepetizerkotlinstudy.model

import com.qinglianyun.base.comm.CommError
import com.qinglianyun.base.net.*
import com.qinglianyun.eyepetizerkotlinstudy.bean.TabInfoBean
import com.qinglianyun.eyepetizerkotlinstudy.net.ApiService
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

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

    fun getCategoryDetailList(id: Long, callListener: ICallback<HomeBean.IssueListBean>) {
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

class DownloadModel : BaseModel() {
    fun downloadFile(url: String, file: File, downloadListener: DownloadListener) {
        mApiString.downloadFile(url).enqueue(BaseDownloadCall(file, downloadListener))
    }

    fun downloadFile1(url: String, file: File, downloadListener: DownloadListener) {
        downloadListener.onStart()
        var retrofitManager = RetrofitManager.getInstance(ApiService.BASE_URL)
        retrofitManager
            .setListener(object : RetrofitManager.ProgressListener {
                override fun onProgress(progress: Long, totalLen: Long, finish: Boolean) {
                    if (!finish) {
                        downloadListener.onProgress(progress, totalLen)
                    }
                }
            })
        retrofitManager.create(ApiService::class.java)
            .downloadFile(url).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    downloadListener.onError(
                        CommError.DOWNLOAD_ERR_CODE,
                        CommError.DOWNLOAD_ERR_MSG
                    )
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    var inputStream = response.body()?.byteStream()
                    inputStream?.let {
                        var stream = BufferedOutputStream(FileOutputStream(file))
                        var b = ByteArray(1024)
                        var len = it.read(b)
                        while (len != -1) {
                            stream.write(b, 0, len)
                            stream.flush()
                        }
                        stream.close()
                        it.close()
                        downloadListener.onFinish(file.absolutePath)
                    }
                }
            })
    }

}