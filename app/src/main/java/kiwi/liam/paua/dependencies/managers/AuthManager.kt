package kiwi.liam.paua.dependencies.managers

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kiwi.liam.paua.dependencies.models.User
import kotlinx.coroutines.flow.MutableStateFlow

class AuthManagerState {
    var user: MutableStateFlow<User?> = MutableStateFlow(null)
}

interface AuthManager {

    fun listenToAuthStatus()

    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String)

    fun signOut()
}

class AppAuthManager(private val state: AuthManagerState) : AuthManager {
    private val auth = Firebase.auth

    override fun listenToAuthStatus() {
        auth.addAuthStateListener {
            auth.currentUser?.let {
                state.user.value = User(
                    uid = it.uid,
                    name = it.displayName ?: "",
                )
            }
        }
    }

    override fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                state.user.value = User(
                    uid = it.user?.uid ?: "",
                    name = it.user?.displayName ?: "",
                )
            }
    }

    override fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                state.user.value = User(
                    uid = it.user?.uid ?: "",
                    name = it.user?.displayName ?: "",
                )
            }
    }

    override fun signOut() {
        auth.signOut()
        state.user.value = null
    }
}

class MockAuthManager : AuthManager {
    var didListenToAuthStatus = false
    var didSignIn = false
    var didSignUp = false
    var didSignOut = false

    override fun listenToAuthStatus() {
        didListenToAuthStatus = true
    }

    override fun signIn(email: String, password: String) {
        didSignIn = true
    }

    override fun signUp(email: String, password: String) {
        didSignUp = true
    }

    override fun signOut() {
        didSignOut = true
    }
}