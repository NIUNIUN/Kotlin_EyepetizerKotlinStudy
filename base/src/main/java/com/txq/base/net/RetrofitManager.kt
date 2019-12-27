package com.txq.base.net

import com.txq.base.comm.Constans
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * Created by tang_xqing on 2019/11/12.
 */
class RetrofitManager(val baseUrl: String) : DownloadFileListener {
    private var retrofit: Retrofit
    var mListener: ProgressListener? = null

    init {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(DownloadInterceptor(this))
            .addInterceptor(AddParameterInterceptor())
            .connectTimeout(Constans.HTTP_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(Constans.HTTP_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(Constans.HTTP_TIMEOUT, TimeUnit.MINUTES)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    companion object {
        @Volatile
        var manager: RetrofitManager? = null

        fun getInstance(baseUrl: String): RetrofitManager {
            if (null == manager) {
                synchronized(RetrofitManager::class.java) {
                    if (null == manager) {
                        manager = RetrofitManager(baseUrl)
                    }
                }
            }
            return manager!!
        }
    }

    fun <T> create(server: Class<T>): T {
        if (null == server) {
            throw RuntimeException("ApiServer is NULL")
        }

        return retrofit.create(server)
    }

    fun setListener(listener: ProgressListener) {
        mListener = listener
    }

    override fun onProgress(progress: Long, totalLen: Long, finish: Boolean) {
        mListener?.let {
            it.onProgress(progress, totalLen, finish)
        }
    }

    interface ProgressListener {
        fun onProgress(progress: Long, totalLen: Long, finish: Boolean)
    }
}

class AddParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 添加Url固定参数
        var request = chain.request()
        var url = request.url().newBuilder()
            .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
            .addQueryParameter("deviceModel", "OPPO%20A83").build()
        var build = request.newBuilder().url(url).build()
        return chain.proceed(build)
    }
}

class AddHeaderInterceptor : Interceptor {
    private val token: String by lazy {
        ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        // 添加头
        var request = chain.request()
        var method = request.newBuilder().addHeader("", "")
            .method(request.method(), request.body()).build()
        return chain.proceed(method)
    }
}