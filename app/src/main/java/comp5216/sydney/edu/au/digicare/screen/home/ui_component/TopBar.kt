package comp5216.sydney.edu.au.digicare.screen.home.ui_component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient1
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient2
import comp5216.sydney.edu.au.digicare.ui.theme.ColorGradient3
import comp5216.sydney.edu.au.digicare.ui.theme.ColorSurface
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextPrimary
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import comp5216.sydney.edu.au.digicare.util.LocationService.LocationService
import kotlinx.coroutines.launch



@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Preview
@Composable
fun TopBar(
    context: Context=LocalContext.current,
    modifier:Modifier=Modifier
) {
    var city by remember { mutableStateOf("Loading...") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val locationService=LocationService(context)
        coroutineScope.launch {
            locationService.getCurrentLocation{currentCity ->
                city = currentCity
            }
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LocationInfo(
            modifier = Modifier.padding(top = 10.dp),
            location = city
        )
        ProfileButton()
    }
}



@Composable
private fun ProfileButton(
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 1.5.dp,
                color = ColorSurface,
                shape = CircleShape
            )

    ) {
        Image(
            painter = painterResource(id = R.drawable.img_profile),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
private fun LocationInfo(
    modifier: Modifier = Modifier,
    location: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_location_pin),
                contentDescription = null,
                modifier = Modifier.height(18.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = location,
                style = MaterialTheme.typography.titleLarge,
                color = ColorTextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        CommentBar()
    }
}

@Composable
private fun CommentBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.25f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                vertical = 2.dp,
                horizontal = 10.dp
            )
    ) {
        Text(
            text = "You are not alone",
            style = MaterialTheme.typography.labelSmall,
            color = ColorTextSecondary.copy(alpha = 0.7f)
        )
    }
}
