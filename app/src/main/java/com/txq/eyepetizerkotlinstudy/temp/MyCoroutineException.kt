package com.txq.eyepetizerkotlinstudy.temp

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/** 全局捕获异常
 * 两种方式：1、协程局部捕获异常。作为参数，传入协程launch()方法中。
 *         2、全局捕获异常。
 *
 * launch、actor：发生异常自动向上传递。
 * async、producr：发送异常依赖调用者处理。  async：只要没调用await就不会出现崩溃。 解决方法：在挂起点使用try{ }catch()
 *
 * Created by tang_xqing on 2020/1/17.
 */

class MyCoroutineException : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("全局捕获异常 ${exception.message}")
        exception.printStackTrace()
    }
}