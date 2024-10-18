package comp5216.sydney.edu.au.digicare.screen.splash

import comp5216.sydney.edu.au.digicare.MAIN_SCREEN
import comp5216.sydney.edu.au.digicare.SPLASH_SCREEN
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : DigiCareViewModel(){

    fun onAppStart(){
        createAnonymousAccount()
    }

    private fun createAnonymousAccount(){
        launchCatching {
            accountService.createAnonymousAccount()
        }
    }
}