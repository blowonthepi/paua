package kiwi.liam.paua.dependencies.managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.models.User

class AuthManagerState {
    var user: User? by mutableStateOf(null)
}

interface AuthManager

class AppAuthManager(val state: AuthManagerState) : AuthManager

class MockAuthManager : AuthManager