package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardList(items: List<String>) {
    // LazyColumn to display a scrollable list of items
    LazyColumn(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),

        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between cards
    ) {
        // Iterate over each item and display it as a card
        items(items) { item ->
            CardItem(item)
        }
    }
}

@Composable
fun CardItem(text: String) {
    // Card that displays each item
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
        ) {
            // Display the item text
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardList() {
    val sampleItems = listOf("Wed 9th Oct: \n Today I feel sick in the stomach", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    CardList(items = sampleItems)

}