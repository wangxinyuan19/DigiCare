package comp5216.sydney.edu.au.digicare.util



import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LocationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun doWork(): Result {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        return withContext(Dispatchers.IO) {
            try {
                if (hasLocationPermission()) {
                    val location = getLastLocation()
                    if (location != null) {
                        // Use the location (send it to your server, store locally, etc.)
                        Log.d("LocationWorker", "Location: ${location.latitude}, ${location.longitude}")
                    } else {
                        Log.d("LocationWorker", "Location not available")
                    }
                } else {
                    Log.d("LocationWorker", "Location permission not granted")
                    return@withContext Result.failure()
                }
                Result.success()
            } catch (e: SecurityException) {
                Log.e("LocationWorker", "Location permission denied", e)
                Result.failure()
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private suspend fun getLastLocation(): Location? {
        return try {
            fusedLocationClient.lastLocation.await()
        } catch (e: SecurityException) {
            Log.e("LocationWorker", "SecurityException when accessing location", e)
            null
        }
    }
}
