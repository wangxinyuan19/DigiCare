package comp5216.sydney.edu.au.digicare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient10
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient11
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient12
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary


@Preview
@Composable
fun Features(
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to ColorGradient10,
                            0.5f to ColorGradient11,
                            1f to ColorGradient12
                        )
                    )
            ) {
                Text(
                    text = "History",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                    )
            }
            Spacer(modifier = Modifier.height(20.dp)) // Space between buttons
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to ColorGradient10,
                            0.5f to ColorGradient11,
                            1f to ColorGradient12
                        )
                    )
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.width(30.dp)) // Space between columns

        // Second Column
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to ColorGradient10,
                            0.5f to ColorGradient11,
                            1f to ColorGradient12
                        )
                    )
            ) {
                Text(
                    text = "Summary",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(20.dp)) // Space between buttons
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        brush = Brush.linearGradient(
                            0f to ColorGradient10,
                            0.5f to ColorGradient11,
                            1f to ColorGradient12
                        )
                    )
            ) {
                Text(
                    text = "AI Doctor",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }




    }

}
