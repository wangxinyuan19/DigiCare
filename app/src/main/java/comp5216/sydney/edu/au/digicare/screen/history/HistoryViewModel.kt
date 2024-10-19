package comp5216.sydney.edu.au.digicare.screen.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.model.service.SecurityUtil
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.crypto.SecretKey
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
): DigiCareViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    var clickedRecord by mutableStateOf(VoiceRecord())
        private set

    var records = mutableStateListOf<VoiceRecord>()
        private set

    // Initialize SecurityUtil
    private val securityUtil = SecurityUtil()

    fun fetchRecordsForCurrentUser(){
        launchCatching {
            val fetchedRecords = storageService.fetchRecords(accountService.currentUserId)
            val decryptedRecords = decryptRecords(fetchedRecords)
            records.clear()
            records.addAll(decryptedRecords)
        }
    }

    // Decrypt records using the AES key and IV
    private fun decryptRecords(fetchedRecords: List<VoiceRecord>): List<VoiceRecord> {
        // Retrieve the AES key and IV from the saved file
        val (secretKey, ivString) = retrieveKeyAndIvFromFile()

        return fetchedRecords.map { record ->
            val decryptedText = securityUtil.decryptData(
                record.text,
                secretKey,
                ivString  // Use the IV retrieved from the file
            )
            record.copy(text = decryptedText) // Return a new record with decrypted text
        }
    }

    // Retrieve AES key and IV from the saved JSON file
    private fun retrieveKeyAndIvFromFile(): Pair<SecretKey, String> {
        val file = File("app/src/main/java/comp5216/sydney/edu/au/digicare/model/service/keySave.txt")
        val jsonString = file.readText()
        val json = org.json.JSONObject(jsonString)

        // Extract the key and IV from the JSON object
        val keyString = json.getString("key")
        val ivString = json.getString("iv")

        // Convert the key string back to a SecretKey object
        val secretKey = securityUtil.stringToKey(keyString)

        return Pair(secretKey, ivString) // Return both key and IV as a pair
    }

    fun onRecordClick(record: VoiceRecord){
        clickedRecord = record
        showDialog = true
    }

    fun onCancelClick(){
        showDialog = false
    }

    fun onDeleteClick(record: VoiceRecord) {
        launchCatching {
            storageService.deleteRecord(record.id)
        }
        fetchRecordsForCurrentUser()
        showDialog = false

    }

}
