package comp5216.sydney.edu.au.digicare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip


import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient1
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient2
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient3
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary

import androidx.compose.ui.res.painterResource
import comp5216.sydney.edu.au.digicare.R

@Preview
@Composable
fun VoiceRecord(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.5f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(32.dp)

            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "Start Recording Your Symptom",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = ColorTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Click the button below",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = ColorTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    // Handle button click here
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mic), // Replace with your image resource name
                    contentDescription = "Button Image", // For accessibility
                    modifier = Modifier.size(80.dp) // Set the image size
                )
            }
        }

    }

}
