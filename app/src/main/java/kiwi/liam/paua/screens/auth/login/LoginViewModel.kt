package kiwi.liam.paua.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.managers.AuthManager
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class LoginViewModel : BaseViewModel() {
    private val authManager: AuthManager by inject()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun signIn() {
        email.ifEmpty { return }
        password.ifEmpty { return }

        authManager.signIn(email, password)
    }
}