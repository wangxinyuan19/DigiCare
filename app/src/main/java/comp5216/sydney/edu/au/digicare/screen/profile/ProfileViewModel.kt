package comp5216.sydney.edu.au.digicare.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.impl.AccountServiceImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel (
) : ViewModel() {

    // Initialize AccountService inside the ViewModel
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance() // Firebase instance
    var showSignInDialog by mutableStateOf(false)
    var showSignUpDialog by mutableStateOf(false)
    var signInError by mutableStateOf<String?>(null) // For error handling
    var signUpError by mutableStateOf<String?>(null)

    fun onSignInClick() {
        showSignInDialog = true
    }

    fun onSignUpClick() {
        showSignUpDialog = true
    }

    fun onCancelClick() {
        showSignInDialog = false
        showSignUpDialog = false
    }

    // Sign-in function using FirebaseAuth
    fun onOkClick(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> ->
                if (task.isSuccessful) {
                    // Sign-in successful
                    showSignInDialog = false // Close the dialog
                    signInError = null // Clear error message
                } else {
                    // Sign-in failed, show error message
                    signInError = task.exception?.message ?: "Failed to sign in"
                }
            }
    }

    fun onConfirmClick(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> ->
                if (task.isSuccessful) {
                    // Sign-up successful
                    showSignUpDialog = false // Close the dialog
                    signUpError = null // Clear error message
                } else {
                    // Sign-up failed, show error message
                    signUpError = task.exception?.message ?: "Failed to sign up"
                }
            }
    }

}
