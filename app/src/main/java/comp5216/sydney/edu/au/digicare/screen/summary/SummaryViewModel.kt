package comp5216.sydney.edu.au.digicare.screen.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SummaryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    fun onGenerateClick(){
        showDialog = true
    }

    fun onCancelClick(){
        showDialog = false
    }

    fun onSaveClick(){
        showDialog = false

        // save generated report to database
    }
}