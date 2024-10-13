package comp5216.sydney.edu.au.digicare.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var showSignInDialog by mutableStateOf(false)
    var showSignUpDialog by mutableStateOf(false)

    fun onSignInClick(){
        showSignInDialog = true
    }

    fun onSignUpClick(){
        showSignUpDialog = true
    }

    fun onCancelClick(){
        showSignInDialog = false
        showSignUpDialog = false
    }

    fun onOkClick(email:String, password:String){
        //Handle sign in with email and password

        showSignInDialog = false

    }

    fun onConfirmClick(email:String, password:String){
        //Handle sign up with email and password


        showSignUpDialog = false

    }
}