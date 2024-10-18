package comp5216.sydney.edu.au.digicare.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.util.convertDateToLong
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

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
        val recordToSave = VoiceRecord("", text, userId, date)
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
}

