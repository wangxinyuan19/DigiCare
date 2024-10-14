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
    val db = FirebaseFirestore.getInstance()


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

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { offset ->

                                if (!isRecording) {
                                    if (ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.RECORD_AUDIO
                                        ) == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        coroutineScope.launch {
                                            isRecording = true
                                            Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
                                            SpeechRecognitionService.startRecording(context) { result ->
                                                recognizedText = result
                                                isRecording = false
                                                // Store the result into Firestore
                                                val historyRecord = hashMapOf(
                                                    "text" to recognizedText,
                                                    "timestamp" to System.currentTimeMillis()
                                                )

                                                db.collection("voiceHistory")
                                                    .add(historyRecord)
                                                    .addOnSuccessListener {
                                                        Toast.makeText(context, "Record saved successfully", Toast.LENGTH_SHORT).show()
                                                    }
                                                    .addOnFailureListener { e ->
                                                        Log.d("addOnFailureListener e :" , "Error saving record: $e")
                                                        println("Error saving record: $e")
                                                        Toast.makeText(context, "Error saving record: $e", Toast.LENGTH_SHORT).show()

                                                    }
                                                Toast.makeText(context, "Recognized: $recognizedText", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        launcher.launch(Manifest.permission.RECORD_AUDIO)
                                    }
                                }
                                tryAwaitRelease()

                                if (isRecording) {
                                    isRecording = false
                                    Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mic),
                    contentDescription = "Mic Button",
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

