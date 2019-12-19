package com.qinglianyun.eyepetizerkotlinstudy.utils

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by tang_xqing on 2019/12/19.
 */
class WorkManageUtil(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        println("使用WorkManager库 请求后台任务")
        return Result.success()
    }
}

class WorkA(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
     println()
        println("WorkA执行任务")
        return Result.success()
    }
}

class WorkB(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        println()
        println("WorkB执行任务")
        return Result.success()
    }
}

class WorkC(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        println("WorkC执行任务")
        return Result.success()
    }
}

class WorkD(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        // 取出传入的参数
        var string = inputData.getString("key")

        println("WorkD执行任务 获取输入数据 $string")
        var data = Data.Builder().putString("output","输出").build()
        return Result.success(data)
    }
}