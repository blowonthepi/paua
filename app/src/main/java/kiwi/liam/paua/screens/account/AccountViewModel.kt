package kiwi.liam.paua.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.managers.AuthManager
import kiwi.liam.paua.dependencies.managers.AuthManagerState
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class AccountViewModel : BaseViewModel() {
    private val authManagerState: AuthManagerState by inject()
    private val authManager: AuthManager by inject()

    var isShowingConfirmAlert by mutableStateOf(false)

    fun getEmail(): String {
        return authManagerState.user.value?.email ?: ""
    }

    fun signOut() {
        authManager.signOut()
    }
}