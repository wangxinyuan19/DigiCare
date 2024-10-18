package comp5216.sydney.edu.au.digicare.model

import com.google.firebase.firestore.DocumentId

data class VoiceRecord(
    @DocumentId val id: String = "",
    val text: String = "",
    val userId: String = "",
    val date: Long = 0L
)

