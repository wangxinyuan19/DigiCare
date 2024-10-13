package comp5216.sydney.edu.au.digicare.screen.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SummaryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    fun onGenerateClick(){
        //Pass the start and end date to service function, so that

        //that function can get all the records within this period 24/10/2024 to 23/10/2024

        //service function will pass back the results (a list of records within this period)


        //generated_summary = gemini_api_generate(listOf(Records))



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