package com.qinglianyun.base.net

import com.qinglianyun.base.comm.Constans
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * Created by tang_xqing on 2019/11/12.
 */
class RetrofitManager(val baseUrl: String) {
    private var retrofit: Retrofit

    init {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
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
}