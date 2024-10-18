package comp5216.sydney.edu.au.digicare.screen.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import comp5216.sydney.edu.au.digicare.model.service.GeminiIntegrationService
import android.content.Context
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import comp5216.sydney.edu.au.digicare.util.combineRecords
import comp5216.sydney.edu.au.digicare.util.convertDateToLong
import comp5216.sydney.edu.au.digicare.util.convertLongToDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : DigiCareViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    val _analysisResult = MutableStateFlow("")
    val analysisResult: StateFlow<String> = _analysisResult
    var startDate by mutableStateOf("Start Date")
    var endDate by mutableStateOf("End Date")


    fun onGenerateClick(context: Context){
        showDialog = true
        launchCatching {
            val records =
                storageService.fetchRecords(startDate,
                    endDate, accountService.currentUserId)
            val combinedRecords = combineRecords(records)
            GeminiIntegrationService().sendToGemini(combinedRecords, context, object :
                GeminiIntegrationService.GeminiCallback {
                override fun onSuccess(result: String) {
                    _analysisResult.value = result
                }

                override fun onFailure(error: String) {
                    _analysisResult.value = error
                }
            })
        }
    }

    fun onCancelClick(){
        showDialog = false
    }

    fun onSaveClick(){
        showDialog = false
        // save generated report to database
        //saveSummaryToDatabase(summaryText)  // Simulated save function
    }

    fun updateStartDate(date: String) {
        startDate = date
    }

    fun updateEndDate(date: String) {
        endDate = date
    }
}