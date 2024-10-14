package comp5216.sydney.edu.au.digicare.model.service.impl
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class TextRecord(
    val userId:String,
    val text:String,
    val date:Long
)

//will use this to store it
suspend fun storeTextRecord(userId: String, generatedText: String) {
    val db=FirebaseFirestore.getInstance()

    //we store the text record with storing id, the text and the timestamp
    val record=TextRecord(
        userId=userId,
        text=generatedText,
        date=System.currentTimeMillis()
    )

    //to store record in firestore
    db.collection("records").add(record).await()
    println("Text record stored successfully.")
}

suspend fun getTextRecords(userId: String): List<TextRecord> {
    val db=FirebaseFirestore.getInstance()

    //query firestore for records using user id
    val result=db.collection("records")
        .whereEqualTo("userId", userId)
        .get()
        .await()

    //to convert  firestore docs into TextRecord object
    val textRecords = result.documents.mapNotNull { document ->
        document.toObject(TextRecord::class.java)
    }

    return textRecords
}