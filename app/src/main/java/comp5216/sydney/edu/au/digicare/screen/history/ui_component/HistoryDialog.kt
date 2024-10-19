package comp5216.sydney.edu.au.digicare.screen.history.ui_component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.util.convertDateToLong
import comp5216.sydney.edu.au.digicare.util.convertLongToDateTime

@Composable
fun HistoryDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    record: VoiceRecord
) {

    // State to control the visibility of the dialog
    val scrollState = rememberScrollState()
    val date = convertLongToDateTime(record.date)
    val text = record.text

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = "Record",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        dismissButton = {
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    )
}

