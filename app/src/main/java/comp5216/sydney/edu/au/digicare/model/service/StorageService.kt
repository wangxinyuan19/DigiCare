package comp5216.sydney.edu.au.digicare.model.service

import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import kotlinx.coroutines.flow.Flow


interface StorageService {
    suspend fun createRecord(text:String, date: Long)
    suspend fun fetchRecords(startDate: String, endDate: String, userId: String): List<VoiceRecord>
    suspend fun fetchRecords(userId: String): List<VoiceRecord>
    suspend fun deleteRecord(recordId: String)
}