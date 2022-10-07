package kiwi.liam.paua.screens.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.managers.AuthManager
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class SignupViewModel : BaseViewModel() {
    private val authManager: AuthManager by inject()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun signUp() {
        email.ifEmpty { return }
        password.ifEmpty { return }

        authManager.signUp(email, password)
    }
}