package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comp5216.sydney.edu.au.digicare.model.service.impl.TextRecord
import comp5216.sydney.edu.au.digicare.model.service.impl.getTextRecords
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    //State to hold the list of textRecord objects which will be fetched from firestore
    var records by mutableStateOf <List<TextRecord>>(emptyList())
        private set

    //Method to fetch record from firestore using getTextRecords

    fun fetchRecords(userId: String) {
        viewModelScope.launch {
            records = getTextRecords(userId)  // Fetch record and update the state
        }
    }

    fun onRecordClick(){
        showDialog = true
    }

    fun onCancelClick(){
        showDialog = false
    }

    fun onDeleteClick(){
        showDialog = false

        // delete the selected record
    }
}