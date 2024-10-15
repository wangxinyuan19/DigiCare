package comp5216.sydney.edu.au.digicare.model.service

import android.content.Context
import android.widget.Toast
import com.microsoft.cognitiveservices.speech.*
import comp5216.sydney.edu.au.digicare.model.service.impl.storeTextRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SpeechRecognitionService {

    private const val speechKey = "cc62d7b5048645fe9f7c4b9caa41367f"
    private const val speechRegion = "australiaeast"

    suspend fun startRecording(context: Context, onResult: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion)

                val recognizer = SpeechRecognizer(speechConfig)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
                }

                val result = recognizer.recognizeOnceAsync().get()

                withContext(Dispatchers.Main) {
                    when (result.reason) {
                        ResultReason.RecognizedSpeech -> {
                            onResult(result.text)
                            Toast.makeText(context, "Recognition success", Toast.LENGTH_SHORT).show()
                            storeTextRecord(generatedText = result.text)
                        }
                        ResultReason.NoMatch -> {
                            onResult("No speech recognized")
                            Toast.makeText(context, "No match found", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Speech recognition failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Recognition failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
