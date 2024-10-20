package comp5216.sydney.edu.au.digicare.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.screen.profile.ui_component.SignInDialog
import comp5216.sydney.edu.au.digicare.screen.profile.ui_component.SignUpDialog
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextPrimary

//@Preview(showBackground = true)
@Composable
fun ProfileScreen(navController: NavController, viewModel : ProfileViewModel = hiltViewModel()){

    var isAnonymous = viewModel.isAnonymous
    var userEmail = viewModel.userEmail

    Scaffold(
        containerColor = ColorBackground,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxHeight(0.1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate("main_screen") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Back",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddings)
        ) {
            ConstraintLayout(){
                val (topImg, profile, header) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.top_background),
                    contentDescription = "top_background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(topImg) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.img_profile),
                    contentDescription = "profile_icon",
                    modifier = Modifier
                        .clip(CircleShape)
                        .constrainAs(profile) {
                            top.linkTo(topImg.bottom)
                            bottom.linkTo(topImg.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(header) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = if (isAnonymous) {
                    "Anonymous"
                } else {
                    "Registered"
                },
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = ColorTextPrimary,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(20.dp))
            if(!isAnonymous){
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = userEmail)
                    }

                }
            }

            Spacer(modifier = Modifier.padding(60.dp))

            if(isAnonymous){
                Button(
                    onClick = {viewModel.onSignInClick()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBBDEFB),
                        contentColor = Color.Black),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),

                    ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                    )
                }

                if(viewModel.showSignInDialog){
                    SignInDialog(
                        onDismissRequest = {viewModel.onCancelClick()},
                        onSignInClick = viewModel::onSignInClick
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {viewModel.onSignUpClick()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBBDEFB),
                        contentColor = Color.Black),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            if(viewModel.showSignUpDialog){
                SignUpDialog(
                    onDismissRequest = {viewModel.onCancelClick()},
                    onSignUpClick = viewModel::onSignUpClick
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            if(!isAnonymous){
                Button(
                    onClick = { viewModel.onSignOutClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBBDEFB),
                        contentColor = Color.Black),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),

                    ) {
                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

        }
    }
}

