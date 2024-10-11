package comp5216.sydney.edu.au.digicare

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import comp5216.sydney.edu.au.digicare.screen.ai_doctor.AI_Doctor
import comp5216.sydney.edu.au.digicare.screen.history.History
import comp5216.sydney.edu.au.digicare.screen.history.Summary
import comp5216.sydney.edu.au.digicare.screen.home.HomeScreen
import comp5216.sydney.edu.au.digicare.screen.profile.ProfileScreen
import comp5216.sydney.edu.au.digicare.screen.splash.SplashScreen
import comp5216.sydney.edu.au.digicare.screen.summary.SummaryViewModel


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            Navigation()
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


