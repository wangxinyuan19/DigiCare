package comp5216.sydney.edu.au.digicare.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertDateToLong(dateString: String): Long {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date: Date = dateFormat.parse(dateString) ?: return 0L
        date.time // Return the timestamp in milliseconds
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
        0L
    }
}

fun convertLongToDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(date)
}


