package comp5216.sydney.edu.au.digicare.screen.home.ui_component

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.SpeechRecognitionService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceRecordViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    @ApplicationContext val context: Context
) : DigiCareViewModel() {

    val isRecording = mutableStateOf(false)
    val recognizedText = mutableStateOf("")

    // Function to request microphone permission and start recording
    fun requestMicrophonePermission(onPermissionResult: (Boolean) -> Unit) {
        val permissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        onPermissionResult(permissionGranted)
    }

    // Function to start recording voice
    fun startRecording() {
        viewModelScope.launch {
            isRecording.value = true
            SpeechRecognitionService.startRecording(context) { result ->
                recognizedText.value = result
                isRecording.value = false
                saveRecordToFirestore(result)
            }
        }
    }

    // Function to save recognized text to Firestore
    fun saveRecordToFirestore(recognizedText: String) {
        launchCatching {
            storageService.createRecord(recognizedText, System.currentTimeMillis())
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Release SpeechRecognitionService resources
        SpeechRecognitionService.releaseResources()
    }
}
