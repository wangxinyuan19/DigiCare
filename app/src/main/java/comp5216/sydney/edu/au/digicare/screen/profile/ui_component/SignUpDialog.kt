package comp5216.sydney.edu.au.digicare.screen.profile.ui_component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import comp5216.sydney.edu.au.digicare.screen.profile.ProfileViewModel

@Composable
fun SignUpDialog(
    onDismissRequest: () -> Unit,
    onSignUpClick: (String, String, String) -> Unit,
    viewModel : ProfileViewModel = hiltViewModel()
) {
    // State for storing email and password inputs
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var message by remember {mutableStateOf("")}

    // Dialog for email and password input
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Enter Email and Password")
        },
        text = {
            Column {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password1,
                    onValueChange = { password1 = it },
                    label = { Text(text = "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    //visualTransformation = PasswordVisualTransformation() // For password input
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password2,
                    onValueChange = { password2 = it },
                    label = { Text(text = "Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    //visualTransformation = PasswordVisualTransformation() // For password input
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display error message if validation fails
                if (viewModel.errorMessage.isNotEmpty()) {
                    Text(
                        text = viewModel.errorMessage,
                        color = MaterialTheme.colorScheme.error, // Use error color
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSignUpClick(email, password1, password2)
                }
            ) {
                Text(text = "Sign Up")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
                viewModel.errorMessage = ""
            }) {
                Text(text = "Cancel")
            }
        }
    )
}


