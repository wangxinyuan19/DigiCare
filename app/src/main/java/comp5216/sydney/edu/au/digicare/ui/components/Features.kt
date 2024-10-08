package comp5216.sydney.edu.au.digicare.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.R
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
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                    )
                Button(
                    onClick = {
                        // Handle button click here
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,  // Transparent button background
                    )

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chart), // Replace with your image resource name
                        contentDescription = "Chart image", // For accessibility
                        modifier = Modifier
                            .size(80.dp) // Set the image size
                            .background(color = Color.Transparent)
                    )
                }
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
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        // Handle button click here
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,  // Transparent button background
                    )

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person), // Replace with your image resource name
                        contentDescription = "Chart image", // For accessibility
                        modifier = Modifier
                            .size(80.dp) // Set the image size
                            .background(color = Color.Transparent)
                    )
                }
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
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        // Handle button click here
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,  // Transparent button background
                    )

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.summary), // Replace with your image resource name
                        contentDescription = "Chart image", // For accessibility
                        modifier = Modifier
                            .size(80.dp) // Set the image size
                            .background(color = Color.Transparent)
                    )
                }
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
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        // Handle button click here
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,  // Transparent button background
                    )

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ai_doctor), // Replace with your image resource name
                        contentDescription = "Chart image", // For accessibility
                        modifier = Modifier
                            .size(80.dp) // Set the image size
                            .background(color = Color.Transparent)
                    )
                }
            }
        }




    }

}
