package comp5216.sydney.edu.au.digicare.screen.history.ui_component

import HistoryViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardList(viewModel: HistoryViewModel) {
    // Record the number of currently displayed entries, with a default display of 4.
    var itemCount by remember { mutableStateOf(4) }
    val listState = rememberLazyListState() // Used to track the scroll state of the list.

    // Load more data when scrolling to the bottom of the list.
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collect { visibleItems ->
                if (visibleItems >= itemCount && itemCount < viewModel.voiceHistory.size) {
                    itemCount += 4 // Load 3 entries each time.
                }
            }
    }

    LazyColumn(
        state = listState, // Bind the list state
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        // Display only the current number of items
        itemsIndexed(viewModel.voiceHistory.take(itemCount)) { index, record ->
            val text = record["text"] as? String ?: ""
            val timestamp = record["timestamp"] as? Long ?: 0L

            // Convert the timestamp to LocalDateTime and format it
            val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
            val formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        viewModel.onRecordClick()
                        viewModel.onCardClick(record["id"] as String)  // Assume the id is of String type
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "Timestamp: $formattedDateTime",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

    }
}
