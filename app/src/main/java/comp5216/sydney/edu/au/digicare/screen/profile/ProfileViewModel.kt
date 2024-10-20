package comp5216.sydney.edu.au.digicare.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import comp5216.sydney.edu.au.digicare.model.User
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.impl.AccountServiceImpl
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService
) : DigiCareViewModel() {

    val currentUserFlow: Flow<User?> = accountService.currentUser
    var showSignInDialog by mutableStateOf(false)
    var showSignUpDialog by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var isAnonymous by mutableStateOf(false)
    var userEmail by mutableStateOf("")

    init {
        launchCatching {
            currentUserFlow.collect{
                user -> updateUserInfo()
            }
        }
    }

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
    fun onSignInClick(email: String, password: String) {
        launchCatching {
            accountService.signIn(email, password)
            showSignInDialog = false // Close the dialog
        }
    }

    fun onSignUpClick(email: String, password1: String, password2 : String) {
        launchCatching {
            if (!email.isValidEmail()) {
                errorMessage = "Invalid email format"
            } else if (!password1.isValidPassword()) {
                errorMessage = "Invalid password format"
            } else if (password1 != password2) {
                errorMessage = "password do not match"
            } else {
                accountService.linkAccount(email, password1)
                updateUserInfo()
                showSignUpDialog = false // Close the dialog
            }
        }

    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }

    }

    fun updateUserInfo() {
        isAnonymous = accountService.isAnonymous
        userEmail = accountService.currentUserEmail
    }


}
