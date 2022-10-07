package kiwi.liam.paua.dependencies.services

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kiwi.liam.paua.dependencies.managers.AuthManagerState
import kiwi.liam.paua.dependencies.managers.TopologyManager
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.models.TransitType
import kotlinx.coroutines.tasks.await

interface FirestoreService {

    suspend fun getTransactionHistory(): List<Transaction>
    suspend fun getUserBalanceCents(): Int
    suspend fun updateUserBalance(valueCents: Int)
}

class AppFirestoreService(
    private val topologyManager: TopologyManager,
    private val authManagerState: AuthManagerState,
) : FirestoreService {
    private val firestore = Firebase.firestore

    private val userId: String
        get() = authManagerState.user.value?.uid ?: ""

    @Suppress("UNCHECKED_CAST")
    override suspend fun getTransactionHistory(): List<Transaction> {
        val routes = topologyManager.routes

        val list = mutableListOf<Transaction>()
        val snapshot: QuerySnapshot = firestore
            .collection("users")
            .document(userId)
            .collection("trips")
            .get()
            .await()

        for (document in snapshot) {
            val routeId = document.data["route_id"] as String
            list.add(
                Transaction(
                    routeId = routeId,
                    routeName = routes.firstOrNull { it.routeShortName == routeId }?.routeLongName ?: "",
                    type = TransitType.Train,
                    stops = document.data["stops"] as List<String>
                )
            )
        }
        return list
    }

    override suspend fun getUserBalanceCents(): Int {
        val document = firestore
            .collection("users")
            .document(userId)
            .get()
            .await()
        return (document.get("balance") as Long).toInt()
    }

    override suspend fun updateUserBalance(valueCents: Int) {
        firestore
            .collection("users")
            .document(userId)
            .update("balance", valueCents)
    }
}

class MockFirestoreService : FirestoreService {
    var didGetTransactionHistory = false
    var didGetBalanceCents = false
    var didUpdateBalance = false

    override suspend fun getTransactionHistory(): List<Transaction> {
        didGetTransactionHistory = true
        return emptyList()
    }

    override suspend fun getUserBalanceCents(): Int {
        didGetBalanceCents = true
        return 0
    }

    override suspend fun updateUserBalance(valueCents: Int) {
        didUpdateBalance = true
    }
}