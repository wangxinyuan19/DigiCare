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

class HistoryViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    var voiceHistory = mutableStateListOf<Map<String, Any>>() // Used to store history records

    private val db = FirebaseFirestore.getInstance() // Initialize Firestore instance

    var currentId: String by mutableStateOf("")

    fun onCardClick(id: String) {
        currentId = id
        showDialog = true
    }

    fun onRecordClick(){
        showDialog = true
    }

    fun onCancelClick(){
        showDialog = false
    }

    fun onDeleteClick(recordId: String) {
        showDialog = false

        //Delete the selected record
        db.collection("voiceHistory").document(recordId)
            .delete()
            .addOnSuccessListener {
                //After successfully deleting the record, update the local state
                voiceHistory.removeAll { it["id"] == recordId }
            }
            .addOnFailureListener {
                //Error handling
            }
    }

    // Fetch Firestore history records
    fun fetchVoiceHistory() {
        viewModelScope.launch {
            db.collection("voiceHistory")
                .get()
                .addOnSuccessListener { documents ->
                    //Clear the previous history records
                    voiceHistory.clear()

                    for (document in documents) {
                        val data = document.data
                        data["id"] = document.id //Save the document ID for subsequent deletion
                        voiceHistory.add(data) //Add the record to the list
                    }
                    Log.d("VoiceHistory",voiceHistory.size.toString())
                }
                .addOnFailureListener {
                    // Error handling
                }
        }
    }
}
