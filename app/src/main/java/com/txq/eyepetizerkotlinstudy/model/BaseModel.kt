package com.txq.eyepetizerkotlinstudy.model

import com.txq.base.net.RetrofitManager
import com.txq.base.view.IBaseModel
import com.txq.eyepetizerkotlinstudy.net.ApiService

/**
 * Created by tang_xqing on 2019/11/26.
 */
open class BaseModel: IBaseModel {
    protected val mApiString: ApiService by lazy {
        RetrofitManager.getInstance(ApiService.BASE_URL)
            .create(ApiService::class.java)
    }
}