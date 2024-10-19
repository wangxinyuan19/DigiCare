package comp5216.sydney.edu.au.digicare.screen.history.ui_component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import comp5216.sydney.edu.au.digicare.screen.history.HistoryViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import comp5216.sydney.edu.au.digicare.model.VoiceRecord
import comp5216.sydney.edu.au.digicare.util.convertLongToDateTime
import comp5216.sydney.edu.au.digicare.util.displayFirst50Characters


@Composable
fun CardList(viewModel: HistoryViewModel) {
    
    val records = viewModel.records

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between cards
    ) {
        items(records) { record ->
            Card(
                modifier = Modifier
                    .fillMaxWidth() // Make the card take up the full width of the parent
                    .height(150.dp), // Set the height of the card
                colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)), // Light blue background
                elevation = CardDefaults.cardElevation(4.dp) // Elevation for shadow effect
            ) {
                // Content inside the card
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp) // Padding inside the card
                        .clickable { viewModel.onRecordClick(record) }
                ) {
                    Column {
                        Text(
                            text = convertLongToDateTime(record.date),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                        Text(
                            text = displayFirst50Characters(record.text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }

                }
            }
        }
    }
}



