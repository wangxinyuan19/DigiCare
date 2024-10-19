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
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun fetchRecordsForCurrentUser(){
        launchCatching {
            val fetchedRecords = storageService.fetchRecords(accountService.currentUserId)
            records.clear()
            records.addAll(fetchedRecords)
        }
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
