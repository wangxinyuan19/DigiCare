package comp5216.sydney.edu.au.digicare.model.service.impl

import android.util.Base64
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.model.service.SecurityUtil
import comp5216.sydney.edu.au.digicare.util.convertDateToLong
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import java.io.File
import javax.crypto.SecretKey

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun fetchRecords(userId: String) : List<VoiceRecord> {
        return try {
            val snapshot = Firebase
                .firestore
                .collection(RECORD_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, userId)
                .get()
                .await()

            snapshot.documents.mapNotNull {
                    document -> document.toObject(VoiceRecord::class.java)
            }

        } catch (e: Exception){
            println("Error getting records: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createRecord(text:String, date: Long) {
        val userId = auth.currentUserId

        // Initialize SecurityUtil and generate or retrieve the AES key
        val securityUtil = SecurityUtil()
        // Generate a new AES key (or retrieve from saved file)
        val (secretKey, ivString) = if (keyExists()) {
            retrieveKeyAndIvFromFile() // Retrieve the key and IV from the file
        } else {
            val newKey = securityUtil.generateKey() // Generate a new AES key
            val iv = ByteArray(16) // Create a new IV
            saveKeyAndIvToFile(
                securityUtil.keyToString(newKey),
                Base64.encodeToString(iv, Base64.DEFAULT)
            ) // Save both key and IV
            Pair(newKey, Base64.encodeToString(iv, Base64.DEFAULT)) // Return both key and IV as a pair
        }

        // Encrypt the data (record content) using the secret key and the generated IV
        val (encryptedText, newIvString) = securityUtil.encryptData(text, secretKey)

        val recordToSave = VoiceRecord("", text = encryptedText, userId, date)
        Firebase.firestore
            .collection(RECORD_COLLECTION)
            .add(recordToSave).await()
    }

    override suspend fun fetchRecords(startDate: String, endDate: String, userId: String): List<VoiceRecord> {

        val startDateLong = convertDateToLong(startDate)
        val endDateLong = convertDateToLong(endDate)

        return try {
            val snapshot = Firebase
                .firestore
                .collection(RECORD_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, userId)
                .whereGreaterThanOrEqualTo(DATE_FIELD, startDateLong)
                .whereLessThanOrEqualTo(DATE_FIELD, endDateLong)
                .get()
                .await()

            snapshot.documents.mapNotNull {
                document -> document.toObject(VoiceRecord::class.java)
            }

        } catch (e: Exception){
            println("Error getting records: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteRecord(recordId: String) {
        Firebase.firestore
            .collection(RECORD_COLLECTION)
            .document(recordId).delete().await()
    }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val RECORD_COLLECTION = "records"
        private const val DATE_FIELD = "date"
    }

    // Check if the key file exists
    private fun keyExists(): Boolean {
        val file = File("app/src/main/java/comp5216/sydney/edu/au/digicare/model/service/keySave.txt")
        return file.exists()
    }

    // Save the key and IV to the file as a JSON string
    private fun saveKeyAndIvToFile(keyString: String, ivString: String) {
        val json = JSONObject()
        json.put("key", keyString)
        json.put("iv", ivString)

        val file = File("app/src/main/java/comp5216/sydney/edu/au/digicare/model/service/keySave.txt")
        file.writeText(json.toString())
    }

    // Retrieve the key and IV from the JSON file
    private fun retrieveKeyAndIvFromFile(): Pair<SecretKey, String> {
        val file = File("app/src/main/java/comp5216/sydney/edu/au/digicare/model/service/keySave.txt")
        val jsonString = file.readText()
        val json = JSONObject(jsonString)

        val keyString = json.getString("key")
        val ivString = json.getString("iv")

        val secretKey = SecurityUtil().stringToKey(keyString)
        return Pair(secretKey, ivString)
    }
}

