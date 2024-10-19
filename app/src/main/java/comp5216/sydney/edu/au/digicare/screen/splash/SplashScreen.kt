package comp5216.sydney.edu.au.digicare.screen.splash


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import comp5216.sydney.edu.au.digicare.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()){
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        viewModel.onAppStart()
        delay(2000L)
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