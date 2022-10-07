package kiwi.liam.paua.dependencies.managers

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kiwi.liam.paua.dependencies.models.User
import kiwi.liam.paua.dependencies.services.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthManagerState {
    var user: MutableStateFlow<User?> = MutableStateFlow(null)
}

interface AuthManager {

    fun listenToAuthStatus()

    suspend fun signIn(email: String, password: String): Boolean
    fun signUp(email: String, password: String)

    fun signOut()
}

class AppAuthManager(
    private val state: AuthManagerState,
    private val firestoreService: FirestoreService,
) : AuthManager {
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

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            state.user.value = User(
                uid = result.user?.uid ?: "",
                name = result.user?.displayName ?: "",
            )

            true
        } catch (e: FirebaseFirestoreException) {
            false
        }
    }

    override fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                state.user.value = User(
                    uid = it.user?.uid ?: "",
                    name = it.user?.displayName ?: "",
                )
                it.user?.uid?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        firestoreService.createUserDocument(it)
                    }
                }
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

    override suspend fun signIn(email: String, password: String): Boolean {
        didSignIn = true
        return true
    }

    override fun signUp(email: String, password: String) {
        didSignUp = true
    }

    override fun signOut() {
        didSignOut = true
    }
}