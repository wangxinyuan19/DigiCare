package comp5216.sydney.edu.au.digicare.util.LocationService

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*

class LocationService(private val context: Context) {

    private val fusedLocationProviderClient:FusedLocationProviderClient=
        LocationServices.getFusedLocationProviderClient(context)

    // Fetching the current location of the user
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onLocationReceived: (String)->Unit) {
        try {
            val locationResult:Task<Location> = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location: Location=task.result
                    val cityName=getCityNameFromCoordinates(location.latitude, location.longitude)
                    onLocationReceived(cityName)
                } else {
                    // We will default location sydney if fail to fetch
                    onLocationReceived("Sydney")
                }
            }
        } catch (e:SecurityException) {
            Log.e("LocationService","Failed to get location: ${e.message}")
        }
    }

    // we get city name from latitude and longitude using Geocoder
    private fun getCityNameFromCoordinates(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses:List<Address>? =geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!=null && addresses.isNotEmpty()) {
                addresses[0].locality?:"Unknown City"
            } else {"Unknown City"}
        } catch (e:IOException) {
            Log.e("LocationService", "Geocoder failed", e)
            "Unknown City"
        }
    }
}
