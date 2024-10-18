package comp5216.sydney.edu.au.digicare.util

import comp5216.sydney.edu.au.digicare.model.VoiceRecord

fun combineRecords(records: List<VoiceRecord>): String {
    return "Summarise the following text with date\n" + records.joinToString(", ") { record ->
        val dateString = convertLongToDateTime(record.date)
        "$dateString: ${record.text}"
    }
}
