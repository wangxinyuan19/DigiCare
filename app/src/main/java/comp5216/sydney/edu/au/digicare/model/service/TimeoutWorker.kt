package comp5216.sydney.edu.au.digicare.worker

import android.content.Context
import android.os.Process
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimeoutWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(TAG, "Timeout reached. Terminating process.")
        Process.killProcess(Process.myPid())
        System.exit(1)
        return Result.success()
    }

    companion object {
        private const val TAG = "TimeoutWorker"
    }
}
