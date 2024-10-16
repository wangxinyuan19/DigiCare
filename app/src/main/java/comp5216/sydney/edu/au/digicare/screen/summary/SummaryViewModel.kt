package comp5216.sydney.edu.au.digicare.screen.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import comp5216.sydney.edu.au.digicare.model.service.GeminiIntegrationService
import android.content.Context

class SummaryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    var summaryText by mutableStateOf("Generated summary...")  // Hold the summary text
    var startDate by mutableStateOf("Start Date")
    var endDate by mutableStateOf("End Date")


    fun onGenerateClick(context: Context){
        showDialog = true
        viewModelScope.launch {
            delay(4000) // Simulate a 4-second delay for fetching data
            // Call the service function to get the summary from the database
            val fetchedRecord = fetchSummaryFromService(startDate, endDate) // Simulated service call
            val geminiService = GeminiIntegrationService()
            geminiService.sendToGemini(fetchedRecord, context, object : GeminiIntegrationService.GeminiCallback {
                override fun onSuccess(result: String) {
                    val fetchedSummary = result
                    summaryText = fetchedSummary // Update the summary text
                }
                override fun onFailure(error: String) {
                    println("Error fetching record: $error")
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

    // Simulate the service call to fetch the summary
    private fun fetchSummaryFromService(startDate: String, endDate: String): String {
        // Assume this gets the data from the database based on the date range
        return "Summary from $startDate to $endDate:\n\nRecord 1: I got a flu\nRecord 2: I got a cancer\n..."
    }

    // Simulate saving the summary to the database
    // Save the text to pdf
    private fun saveSummaryToDatabase(summary: String) {
        // Implementation to save summary to the database
        // Save the summary data to the "summaries" collection in Firestore
//        db.collection("summaries")
//            .add(summaryData)
//            .addOnSuccessListener { documentReference ->
//                // Handle success (e.g., show a confirmation message)
//                println("Summary saved with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                // Handle failure (e.g., show an error message)
//                println("Error saving summary: $e")
//            }
    }
}