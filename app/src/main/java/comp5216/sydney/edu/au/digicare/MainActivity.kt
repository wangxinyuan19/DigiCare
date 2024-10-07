package comp5216.sydney.edu.au.digicare

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import comp5216.sydney.edu.au.digicare.ui.HomeScreen
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
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
            HomeScreen()
        }

    }
}

@Composable
fun SplashScreen(navController: NavController){
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate("main_screen")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo")
    }
}