package com.txq.eyepetizerkotlinstudy.temp

import android.widget.TextView

/**
 * Created by tang_xqing on 2019/12/2.
 */

class TempCall {

    fun call() {
        var temp = TempClass()
        temp.setOnListener { num, msg ->
            println("$num $msg")
        }

        temp.setOnListener(
            { num, msg ->
                // Kotlin中的Lambda表达式的返回值，根据操作代码自动推导出来
                println("$num $msg")
            })

        // 调用扩展函数
        var tv:TextView ?=null
        tv?.isBlod()
    }
}

class TempClass {

    lateinit var mListener: (Int, String) -> Unit

    //    fun setOnListener(listener: (num: Int, msg: String) -> Unit) {
    fun setOnListener(listener: (Int, String) -> Unit) {        // 可写参数名，也可不写参数名
        mListener = listener
        mListener.invoke(3, "234")
    }

    interface OnListener {
        var onClick: (num: Int, msg: String) -> Unit
    }

    /**
     * 高阶函数：一个函数作为另一个函数的参数。
     * 自定义高阶函数：1、定义函数变量。 2、创建调用方法
     */
    interface OnRcListener {
        /**
         * 等价于line9,line11~14
         */
        fun onClick(code: Int, msg: String)
    }
}

/**
 * 扩展函数
 */
fun TextView.isBlod():Boolean{
     paint.isFakeBoldText = true
    return paint.isFakeBoldText
}

fun TextView.isBlodStyle() = this.apply {
    paint.isFakeBoldText = false
}

/**
 * 设计模式：分为创建型模式、行为型模式
 * 1、工厂模式（创建型）
 *    简单工厂、抽象工厂
 *
 * 2、策略模式（行为型）
 *
 * 3、模块模式
 */