package comp5216.sydney.edu.au.digicare

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import comp5216.sydney.edu.au.digicare.screen.ai_doctor.AI_Doctor
import comp5216.sydney.edu.au.digicare.screen.history.History
import comp5216.sydney.edu.au.digicare.screen.history.Summary
import comp5216.sydney.edu.au.digicare.screen.home.HomeScreen
import comp5216.sydney.edu.au.digicare.screen.profile.ProfileScreen
import comp5216.sydney.edu.au.digicare.screen.splash.SplashScreen
import comp5216.sydney.edu.au.digicare.screen.summary.SummaryViewModel
import comp5216.sydney.edu.au.digicare.util.AppPermission
import comp5216.sydney.edu.au.digicare.util.LocationWorker
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore  // Initialize Firestore
    private lateinit var appPermissions: AppPermission

    private val requestLocationPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                Log.d(TAG, "Fine location permission granted")
            }
            permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                Log.d(TAG, "Coarse location permission granted")
            }
            else -> {
                Log.d(TAG, "Location permission denied")
                Toast.makeText(this, "Location permission is required to use this feature", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth and Firestore
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        appPermissions = AppPermission()

        requestLocationPermissionIfNeeded()
        val locationWorkRequest = PeriodicWorkRequestBuilder<LocationWorker>(6, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(locationWorkRequest)



        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }

    private fun requestLocationPermissionIfNeeded() {
        if (appPermissions.isLocationOk(this)) {
            // Permission is already granted, proceed with location-based tasks
            Log.d(TAG, "Location permission already granted")
        } else {
            requestLocationPermissionsLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }



    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun signInAnonymously() {
        // [START signin_anonymously]
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)

                }
            }
        // [END signin_anonymously]
    }

    companion object {
        private const val TAG = "AnonymousAuth"
    }

}

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen" ){
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }
        composable("main_screen"){
            HomeScreen(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
        composable("ai_doctor"){
            AI_Doctor(navController)
        }
        composable("summary"){
            Summary(navController)
        }
        composable("history"){
            History(navController)
        }
    }
}