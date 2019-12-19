package com.qinglianyun.eyepetizerkotlinstudy

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

/**
 * Created by tang_xqing on 2019/12/19.
 */
class ObserverClass(lifecycle:Lifecycle) :LifecycleObserver{
    private val mLifecycle:Lifecycle by lazy{
        lifecycle
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        println("应用 event = ON_CREATE , state =${mLifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgroup(){
        println("应用 后台 event = ON_STOP , state =${mLifecycle.currentState}")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForgroup(){

        println("应用 前台 event = ON_START , state =${mLifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        println("应用 event = ON_RESUME , state =${mLifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory(){
        /**
         * 为什么ON_DESTROY 事件没有执行呢？什么时候执行？
         */
        println("应用 event = ON_DESTROY , state =${mLifecycle.currentState}")
    }
}