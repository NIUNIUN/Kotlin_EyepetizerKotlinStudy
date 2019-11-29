package com.qinglianyun.eyepetizerkotlinstudy.model

import com.qinglianyun.base.net.RetrofitManager
import com.qinglianyun.base.view.IBaseModel
import com.qinglianyun.eyepetizerkotlinstudy.net.ApiService

/**
 * Created by tang_xqing on 2019/11/26.
 */
open class BaseModel: IBaseModel {
    protected val mApiString: ApiService by lazy {
        RetrofitManager.getInstance(ApiService.BASE_URL)
            .create(ApiService::class.java)
    }
}