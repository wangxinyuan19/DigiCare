package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.screen.summary.ui_component.DatePicker
import comp5216.sydney.edu.au.digicare.screen.summary.ui_component.GenerateDialog
import comp5216.sydney.edu.au.digicare.screen.summary.SummaryViewModel
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

//@Preview(showBackground = true)
@Composable
fun Summary(navController: NavController) {
    val viewModel: SummaryViewModel = viewModel()
    Scaffold(
        containerColor = ColorBackground,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxHeight(0.1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {navController.navigate("main_screen")},
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Back",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddings)
                .padding(bottom = 10.dp)
        ) {
            Box(){
                Image(
                    painter = painterResource(id = R.drawable.top_background),
                    contentDescription = "top_background",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Column (
                    modifier = Modifier
                        .align(Alignment.Center)
                ){
                    Text(
                        text = "Summary",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Choose a period below to generate summary",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            DatePicker()
            Spacer(modifier = Modifier.fillMaxHeight(0.3f))
            Button(
                onClick = {viewModel.onGenerateClick()},
                shape = CircleShape,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = "Generate",
                    style = MaterialTheme.typography.titleLarge,
                    color = ColorTextSecondary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            if(viewModel.showDialog){
                GenerateDialog(
                    onDismiss = {viewModel.onCancelClick()},
                    onConfirm = {viewModel.onSaveClick()}
                    )
            }

        }
    }
}

