package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

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