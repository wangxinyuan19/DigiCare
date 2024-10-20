package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.model.service.SecurityUtil
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
//
//    // Initialize SecurityUtil
//    private val securityUtil = SecurityUtil()

//    // Hardcoded AES Key (Replace with your actual base64-encoded key)
//    private val hardcodedKeyString = "6v3T8rIry3ORr1UNe5dRbB9Sg2n5FQJXZ2FkY3IoeR0="

//    // Convert the hardcoded key string to SecretKey
//    private val secretKey: SecretKey = securityUtil.stringToKey(hardcodedKeyString)

    fun fetchRecordsForCurrentUser(){
        launchCatching {
            val fetchedRecords = storageService.fetchRecords(accountService.currentUserId)
            //val decryptedRecords = decryptRecords(fetchedRecords)
            records.clear()
            records.addAll(fetchedRecords)
        }
    }

    // Decrypt records using the AES key and IV
//    private fun decryptRecords(fetchedRecords: List<VoiceRecord>): List<VoiceRecord> {
//        // Retrieve the AES key and IV from the saved file
//
//        return fetchedRecords.map { record ->
//            // Decrypt the record's text using the secret key and its specific IV
//            val decryptedText = securityUtil.decryptData(
//                encryptedData = record.text,  // Encrypted text from record
//                secretKey = secretKey,        // Retrieved AES key
//                ivString = record.iv          // IV stored in the record
//            )
//            // Create a new VoiceRecord with the decrypted text
//            record.copy(text = decryptedText)
//        }
//    }

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
