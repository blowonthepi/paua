package kiwi.liam.paua.screens.auth.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kiwi.liam.paua.R
import kiwi.liam.paua.dependencies.managers.AuthManager
import kiwi.liam.paua.tools.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class LoginViewModel : BaseViewModel() {
    private val authManager: AuthManager by inject()
    private val context: Context by inject()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMsg by mutableStateOf<String?>(null)

    fun signIn() {
        email.ifEmpty { return }
        password.ifEmpty { return }

        viewModelScope.launch {
            if (!authManager.signIn(email, password)) {
                errorMsg = context.getString(R.string.screen_login_error)
            }
        }
    }
}