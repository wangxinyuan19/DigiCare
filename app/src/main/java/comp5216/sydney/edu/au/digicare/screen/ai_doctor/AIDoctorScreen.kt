package comp5216.sydney.edu.au.digicare.screen.ai_doctor

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary
import comp5216.sydney.edu.au.digicare.model.service.SpeechRecognitionService
import comp5216.sydney.edu.au.digicare.model.service.GeminiIntegrationService
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AI_Doctor(navController: NavController) {
    val viewModel: GeminiIntegrationViewModel = viewModel()
    val context = LocalContext.current
    val analysisResult by viewModel.analysisResult.collectAsState()
    val coroutineScope = rememberCoroutineScope()

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
                .padding(paddings)
                .padding(bottom = 10.dp)
        ) {
            Box() {
                Image(
                    painter = painterResource(id = R.drawable.top_background),
                    contentDescription = "top_background",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "AI Doctor",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "For emergency, please dial 000 or see doctor",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            ChatBox(resultText = analysisResult)
            Button(
                onClick = {
                    coroutineScope.launch {
                        SpeechRecognitionService.startRecording(context) { recognizedText ->
                            if (recognizedText.isNotBlank()) {
                                viewModel.sendToGemini(recognizedText + " Please give me a brief analysis of my condition, including possible symptoms and corresponding suggestions. Don't just tell me to go to the hospital to see a doctor", context)
                            }
                        }
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
        }
    }
}

@Composable
fun ChatBox(resultText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ai_doctor), // Replace with your icon resource
            contentDescription = "AI Robot",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
            ) {
                Text(
                    text = "AI Doctor",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = resultText.ifEmpty { "Hello, how can I help you today?\n\nPlease speak to describe your symptoms." },
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

class GeminiIntegrationViewModel : ViewModel() {
    private val geminiService = GeminiIntegrationService()
    private val _analysisResult = MutableStateFlow("")
    val analysisResult: StateFlow<String> = _analysisResult

    fun sendToGemini(inputText: String, context: Context) {
        geminiService.sendToGemini(inputText, context, object : GeminiIntegrationService.GeminiCallback {
            override fun onSuccess(result: String) {
                _analysisResult.value = result
            }

            override fun onFailure(error: String) {
                _analysisResult.value = error
            }
        })
    }
}
