package comp5216.sydney.edu.au.digicare.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.screen.home.components.Features
import comp5216.sydney.edu.au.digicare.screen.home.components.TopBar
import comp5216.sydney.edu.au.digicare.screen.home.components.VoiceRecord
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground

@Preview
@Composable
fun HomeScreen() {
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
            Features()
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}