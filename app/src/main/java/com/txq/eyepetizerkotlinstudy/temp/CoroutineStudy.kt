package com.txq.eyepetizerkotlinstudy.temp

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.*

/** 协程练习：
 * 使用协程实现一个网络请求：
 *    等待时显示 Loading；
 *    请求成功或者出错让 Loading 消失；
 *    请求失败需要提示用户请求失败了;
 *    让你的协程写法上看上去像单线程。
 *
 * Created by tang_xqing on 2020/1/6.
 */
class CoroutineStudy {
    /**
     * 使用分析： CoroutineScope.launch{}
     * 启动协程需要哪些参数？  上下文，启动模式，协程体（方法体）
     *
     * 启动模式：CoroutineStart.DEFAULT-立即执行（饿汉式）
     *
     *         LAZY-有需要的时候执行。（也就是需要结果的时候）
     *              调用.start()-主动触发  .join()-隐式触发。和Thread类似，但不相同。
     *
     *         ATOMIC-立即执行，开始运行前无法取消。
     *                涉及到cancel()才有意义，调用的时机不同，执行结果也不相同。和线程的interrupt类似。
     *
     *         UNDISPATCHED-立即执行在当前线程执行，直到遇到挂起点。（没遇到挂起点前，代码顺序执行；遇到挂起点，后面代码在新线程中执行）
     *
     * 调度：协程会被挂起，挂起后恢复，恢复的时候怎么执行，在哪个线程执行就是调度。
     *
     * 拦截器：ContinuationIntercepter
     *
     * 调度器：CoroutineDispatcher (上下文的子类)
     *      目的：切换线程。
     *
     * suspendCoroutine: 获取当前协程的Continuation实例。
     *
     * suspend main：更底层的API
     *
     *
     * 错误处理：
     *
     */

    /**
     * 实现分析：
     *    1、显示Loading
     *    2、网络请求。实现方式：
     *              a: async()函数指定线程，在里面直接写网络请求代码。
     *              b: withContext() 里面写网络请求代码。
     *              C: async()里面包裹 withContext()函数，在withContext()里写网络请求。
     *
     *    3、拿到函数请求结果判断，关闭Loading。  （因为await()是阻塞操作，后面的代码必须得到拿到结果后才能执行 ）
     */
    fun reqestNet() {
        println("1- 线程名称 ${Thread.currentThread().name}")

//        GlobalScope.launch {
//            println("2- 线程名称 ${Thread.currentThread().name}")
//            Thread.sleep(3000)
//            println("3- 线程名称 ${Thread.currentThread().name}")
//        }


        /**
         * UNDISPATCHED :协程直接在当前线程下执行，直到遇到第一个挂起点。
         *    也就是说每遇到挂起点之前，按照代码顺序执行；遇到挂起点，后面的代码都在新线程中执行。
         */
//        GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
//            println("2- 线程名称 ${Thread.currentThread().name}")
////            Thread.sleep(3000)
//            delay(3000)
//            println("3- 线程名称 ${Thread.currentThread().name}")
//        }

        println("4- 线程名称 ${Thread.currentThread().name}")

//        CoroutineScope(Dispatchers.Main).launch {
//            showLoaging()
//            // 方案A：
//            var result = async(Dispatchers.IO) {
//                Thread.sleep(2000)
//                false
//            }
//
////            result.cancel("取消请求",null) // 取消协程
//
//            result.invokeOnCompletion {
//                // 协程关闭时
//            }
//            if (!result.await()) {
//                ToastUtils.showSafeShortInfo("请求失败")
//            }
//
//            // 方案B：
////            if (!getImage()) {
////                ToastUtils.showSafeShortInfo("请求失败")
////            }
//
//            // 方案C：
////            var result1 = async {
////                getImage()
////            }
////            if (!result1.await()) {
////                ToastUtils.showSafeShortInfo("请求失败")
////            }
//
//            closeLoaging()
//        }

//        GlobalScope.launch {
//            launch(MyCoroutineException()) {
                                // 协程内部捕获，没有出现崩溃
//                throw IndexOutOfBoundsException("launch()：空指针异常")
//            }

//            var asyy = GlobalScope.async(MyCoroutineException()) {
//                 async：只有调用await()才会抛异常
//                throw NullPointerException("async()：空指针异常")
//            }
//            try {
//                asyy.await()
//            } catch (e: java.lang.NullPointerException) {
//                e.printStackTrace()
//            }
//        }

        /**
         * 自定义拦截器、调度器
         */
//        GlobalScope.launch(MyContinuationInterceptor()) {
//
//        }

        CoroutineScope(Dispatchers.Main).launch {
            var userName = getUser()
        }
//
//        var coroutineDispatcher = newSingleThreadContext("")
//        coroutineDispatcher.close()
//        coroutineDispatcher.executor
//
//
//        requestNonCancellable()

//        requestSupervisorJob()
//        requestSupervisorScope()

//        requestDebug()

//        var manager = WorkManager()
//        manager.doWork1()
//        manager.doWork2()
//        manager.cancelAll()
//        manager.doWork1()
    }

    /**
     * 不可取消任务
     * NonCancellable：不可取消。发生异常也会执行。
     */
//    private fun requestNonCancellable() {
//        runBlocking {
//            var job = GlobalScope.launch(MyCoroutineException()) {
//                println("异常： 开始")
//                launch {
//                    try {
//                        delay(5000)
//                    } finally {
//                        /**
//                         *  NonCancellable：不能取消。
//                         *  嵌套协程中，一个协程发生异常，另一个协程使用NonCancellable，那么也会继续执行，直到任务完成。
//                         *     如果不使用NonCancellable，那么其余为执行的协程都会被终止。
//                         *
//                         *   父协程F，子协程A+子协程B。A发送异常E时，F会终止B，直到B彻底被终止，F才会抛出E。
//                         */
//                        withContext(Dispatchers.IO) {
//                            println("异常： first -I ")
////                            throw  IndexOutOfBoundsException("数组下标越界")
//                            delay(600)
//                            println("异常： first -II ")
//                        }
//                    }
//                }
//
//                launch {
//                    delay(100)
//                    println("异常： second ")
//                    throw  IndexOutOfBoundsException("数组下标越界")
//                }
//            }
//            job.join()
//        }
//    }

    /**
     * 单向取消任务
     * SupervisorJob :单向取消。不会影响其他子任务被取消。
     */
//    private fun requestSupervisorJob() {
//        runBlocking {
//            val supervisor = SupervisorJob()
//
//            val firstChild =
//                CoroutineScope(Dispatchers.Main).launch(CoroutineExceptionHandler { _, _ -> }) {
//                    println("First child is failing")
//                    throw AssertionError("First child is cancelled")
//                }
//
//            // launch the second child
//            val secondChild = launch {
//                //                    firstChild.join()
//
//                // Cancellation of the first child is not propagated to the second child
//                println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
//
//                try {
//                    delay(Long.MAX_VALUE)
//                } finally {
//                    // But cancellation of the supervisor is propagated
//                    println("Second child is cancelled because supervisor is cancelled")
//                }
//            }
//            // wait until the first child fails & completes
//            firstChild.join()
//
//            println("child is  Cancelling supervisor")
//
//            supervisor.cancel()
//
//            secondChild.join()
//        }
//    }

//    private fun requestSupervisorScope() {
//        runBlocking {
//            supervisorScope {
//                var child = launch(MyCoroutineException()) {
//                    println("child -1")
//                    throw IndexOutOfBoundsException()
//                }
//                println("child -2")
//            }
//            println("child -3")
//        }
//    }

//    private fun requestDebug() {
//        // 调用模式 作用是啥？  在debug模式下，设置成off，也会打印。
//        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "off" else "off")
//
//        runBlocking {
//            launch {
//                log("Data loading started")
//                val task1 = async { "hello -1 " }
//                val task2 = async { "hello -2 " }
//                val result = task1.await() + task2.await()
//                log("data loading completed $result")
//            }
//        }
//    }

    fun log(msg: String) {
        Log.d(TAG, msg)
    }

    val TAG = "测试Debug模式"

    private fun showLoaging() {
        println("加载中")
    }

    private fun closeLoaging() {
        println("加载结束")
    }

    private suspend fun getImage() =
        withContext(Dispatchers.IO) {
            // 网络请求，耗时
            Thread.sleep(2000)
            false
        }
}

/**
 * 自定义拦截器
 */
//class MyContinuationInterceptor : ContinuationInterceptor {
//    override val key: CoroutineContext.Key<*> = ContinuationInterceptor
//
//    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
//        println("MyContinuationInterceptor: call with interceptContinuation()")
//        return MyContinuation(continuation)
//    }
//}
//
//class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
//    override val context: CoroutineContext = continuation.context
//
//    override fun resumeWith(result: Result<T>) {
//        println("MyContinuation: call with resumeWith()")
//        continuation.resumeWith(result)
//    }
//}

/**
 * suspendCoroutione：获取当前协程的实例
 */
inline suspend fun getUser() = suspendCoroutine<String> { continuation ->
    continuation.resume("")
    continuation.resumeWithException(Throwable("请求失败"))
}

//class WorkManager {
//    val job = SupervisorJob()
//    val scope = CoroutineScope(Dispatchers.IO + job)
//    fun doWork1() = scope.launch { println("输出1") }
//    fun doWork2() = scope.launch{ println("输出2") }
//
////    fun cancelAll() = scope.coroutineContext.cancel()  // 调用后，再次调用job输出，则不会输出。
//    fun cancelAll() = scope.coroutineContext.cancelChildren()  // 全部输出，不会影响后续Job调用，job正常运行。
//}