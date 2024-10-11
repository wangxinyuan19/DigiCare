package comp5216.sydney.edu.au.digicare.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import comp5216.sydney.edu.au.digicare.screen.home.ui_component.Features
import comp5216.sydney.edu.au.digicare.screen.home.ui_component.TopBar
import comp5216.sydney.edu.au.digicare.screen.home.ui_component.VoiceRecord
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground

//@Preview
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            TopBar()
            Spacer(
                modifier = Modifier.height(70.dp)
            )
            VoiceRecord()
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            Features(navController)
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}