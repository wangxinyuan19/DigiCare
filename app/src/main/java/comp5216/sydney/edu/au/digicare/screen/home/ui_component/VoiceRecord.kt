package comp5216.sydney.edu.au.digicare.screen.home.ui_component

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient1
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient2
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient3
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary
import comp5216.sydney.edu.au.digicare.model.service.SpeechRecognitionService
import kotlinx.coroutines.launch

@Composable
fun VoiceRecord() {
    var isRecording by remember { mutableStateOf(false) }
    var recognizedText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                coroutineScope.launch {
                    SpeechRecognitionService.startRecording(context) { result ->
                        recognizedText = result
                        isRecording = false
                    }
                }
            } else {
                Toast.makeText(context, "Microphone permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

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
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = ColorTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Click the button below",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = ColorTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {

                    //extract function to viewmodel
                    if (!isRecording) {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            coroutineScope.launch {
                                SpeechRecognitionService.startRecording(context) { result ->
                                    recognizedText = result
                                    isRecording = false
                                    Toast.makeText(context, "Recognized: $recognizedText", Toast.LENGTH_SHORT).show() // Debug message
                                }
                            }
                        } else {
                            launcher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                        isRecording = true
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mic),
                    contentDescription = "Button Image",
                    modifier = Modifier.size(80.dp)
                )
            }

            if (recognizedText.isNotEmpty()) {
                Text(
                    text = "Recognized Text: $recognizedText",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
