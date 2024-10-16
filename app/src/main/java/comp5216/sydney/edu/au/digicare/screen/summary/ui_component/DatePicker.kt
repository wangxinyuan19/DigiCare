package comp5216.sydney.edu.au.digicare.screen.summary.ui_component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel
import comp5216.sydney.edu.au.digicare.screen.summary.SummaryViewModel

@Composable
fun DatePicker(viewModel: SummaryViewModel = viewModel()) {
    val context = LocalContext.current

    // Get the start and end date from the view model
    var startDate by remember { mutableStateOf(viewModel.startDate) }
    var endDate by remember { mutableStateOf(viewModel.endDate) }

    // Function to show the DatePickerDialog
    val showDatePicker = { onDateSelected: (Int, Int, Int) -> Unit ->
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                onDateSelected(year, month + 1, dayOfMonth) // month is zero-indexed, so we add 1
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Start Date Button
        Button(onClick = {
            showDatePicker { year, month, day ->
                startDate = "$day/$month/$year"
                viewModel.updateStartDate(startDate) // Update in the ViewModel
            }
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB))
        ) {
            Text(
                text = startDate,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))

        // End Date Button
        Button(onClick = {
            showDatePicker { year, month, day ->
                endDate = "$day/$month/$year"
                viewModel.updateEndDate(endDate) // Update in the ViewModel
            }
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB))
        ) {
            Text(
                text = endDate,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
