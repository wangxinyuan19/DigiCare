package comp5216.sydney.edu.au.digicare.worker

import android.content.Context
import androidx.work.WorkerParameters
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object BackgroundManager {

    private const val TAG = "BackgroundManager"

    fun scheduleTimeoutTask(context: Context) {
        val timeoutWorkRequest = OneTimeWorkRequest.Builder(TimeoutWorker::class.java)
            .setInitialDelay(10, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(timeoutWorkRequest)
        Log.d(TAG, "Timeout task scheduled.")
    }

    fun cancelTimeoutTask(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
        Log.d(TAG, "Timeout task cancelled.")
    }
}
