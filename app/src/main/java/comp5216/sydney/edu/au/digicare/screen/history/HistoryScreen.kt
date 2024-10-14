package comp5216.sydney.edu.au.digicare.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import comp5216.sydney.edu.au.digicare.R
import comp5216.sydney.edu.au.digicare.screen.history.ui_component.CardList
import comp5216.sydney.edu.au.digicare.screen.history.ui_component.HistoryDialog
import comp5216.sydney.edu.au.digicare.ui.theme.ColorBackground
import comp5216.sydney.edu.au.digicare.ui.theme.ColorTextSecondary

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun History(navController: NavController, userId:String) {
fun History(navController: NavController) {


    //val sampleItems = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    val viewModel: HistoryViewModel = viewModel()

    //using fetchRecords() to load records from the Firestore, we pass userId for it
//    viewModel.fetchRecords(userId)
    viewModel.fetchRecords();
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
                        .clickable { navController.navigate("main_screen") },
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
                        text = "History",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Click each card below to view details",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = ColorTextSecondary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

            }
            CardList(items = viewModel.records.map { it.text }, viewModel)
            if(viewModel.showDialog){
                HistoryDialog(onDismiss = {viewModel.onCancelClick()},
                    onDelete = {viewModel.onDeleteClick()})
            }
        }
    }
}