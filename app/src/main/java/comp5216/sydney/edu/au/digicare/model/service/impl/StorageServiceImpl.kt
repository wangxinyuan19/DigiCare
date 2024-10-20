package comp5216.sydney.edu.au.digicare.model.service.impl

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.model.service.SecurityUtil
import comp5216.sydney.edu.au.digicare.util.convertDateToLong
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.io.BufferedReader
import javax.inject.Inject
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
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
//        val securityUtil = SecurityUtil()
//
//        val secretKey = retrieveKey()
//
//        // Encrypt the text with the key and IV
//        val (encryptedText, ivString) = securityUtil.encryptData(text, secretKey)

        // Create and save the VoiceRecord
        val recordToSave = VoiceRecord(
            id = "",
            text = text,
            userId = userId,
            date = date,
//            iv = ivString
        )
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

    // Retrieve the key
//    private fun retrieveKey(): SecretKey {
//        return SecurityUtil().stringToKey("6v3T8rIry3ORr1UNe5dRbB9Sg2n5FQJXZ2FkY3IoeR0=")  // Convert it back to SecretKey
//    }
}
