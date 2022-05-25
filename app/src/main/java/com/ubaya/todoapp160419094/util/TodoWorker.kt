package com.ubaya.todoapp160419094.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(val context: Context, val workerParams:WorkerParameters)
    :Worker(context, workerParams) {
    override fun doWork(): Result {
        NotificationHelper(context)
            .createNotification(
                inputData.getString("title").toString(),
                inputData.getString("message").toString())
        return Result.success()
    }
}