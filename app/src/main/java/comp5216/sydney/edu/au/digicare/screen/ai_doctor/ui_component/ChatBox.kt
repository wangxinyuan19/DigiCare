package comp5216.sydney.edu.au.digicare.screen.ai_doctor.ui_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import comp5216.sydney.edu.au.digicare.R

@Composable
fun ChatBox() {

    // State to control the visibility of the dialog
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icon on the left
        Image(
            painter = painterResource(id = R.drawable.ai_doctor), // Replace with your icon resource
            contentDescription = "AI Robot",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp)) // Add spacing between icon and chat dialog

        // Chat Dialog on the right
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "AI Doctor",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hello, how can I help you today?\n\nPlease speak to describe your symptoms.",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewIconWithChatDialog() {
    ChatBox()
}
