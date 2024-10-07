package comp5216.sydney.edu.au.digicare.ui

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
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground
import comp5216.sydney.edu.au.digicare.ui.components.ActionBar
import comp5216.sydney.edu.au.digicare.ui.components.Features
import comp5216.sydney.edu.au.digicare.ui.components.VoiceRecord

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
                .verticalScroll(rememberScrollState())
                .padding(paddings)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            ActionBar()
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