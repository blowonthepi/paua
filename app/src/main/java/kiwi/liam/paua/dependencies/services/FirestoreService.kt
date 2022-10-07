package kiwi.liam.paua.dependencies.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kiwi.liam.paua.dependencies.managers.AuthManagerState
import kiwi.liam.paua.dependencies.managers.TopologyManager
import kiwi.liam.paua.dependencies.models.Card
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.models.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

interface FirestoreService {

    /**
     * Get users transaction history
     */
    suspend fun getTransactionHistory(): Flow<List<Transaction>>
    suspend fun getUserBalanceCents(): Int
    suspend fun updateUserBalance(valueCents: Int)
    suspend fun getSavedCard(): Flow<Card>
    suspend fun updateSavedCard(card: Card)

    suspend fun createUserDocument(uid: String)

    suspend fun addTrip(trip: Trip)

    suspend fun disputeTransaction(transaction: Transaction)
}

class AppFirestoreService(
    private val topologyManager: TopologyManager,
    private val authManagerState: AuthManagerState,
) : FirestoreService {
    private val firestore = Firebase.firestore

    private val userId: String
        get() = authManagerState.user.value?.uid ?: ""

    @Suppress("UNCHECKED_CAST")
    override suspend fun getTransactionHistory(): Flow<List<Transaction>> = channelFlow {
        val routes = topologyManager.routes

        firestore
            .collection("users")
            .document(userId)
            .collection("trips")
            .snapshots().collectLatest { snapshot ->
                val list = mutableListOf<Transaction>()
                for (document in snapshot) {
                    val routeId = document.data["route_id"] as String
                    list.add(
                        Transaction(
                            id = document.id,
                            routeId = routeId,
                            routeName = routes.firstOrNull { it.routeShortName == routeId }?.routeLongName ?: "",
                            type = TransitType.Train,
                            disputed = document.data["disputed"] as Boolean? ?: false,
                            stops = document.data["stops"] as List<String>
                        )
                    )
                }
                send(list)
            }
    }

    override suspend fun getUserBalanceCents(): Int {
        val document = firestore
            .collection("users")
            .document(userId)
            .get()
            .await()
        return document.getLong("balance")?.toInt() ?: 0
    }

    override suspend fun updateUserBalance(valueCents: Int) {
        firestore
            .collection("users")
            .document(userId)
            .update("balance", valueCents)
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun getSavedCard(): Flow<Card> = channelFlow {
        firestore
            .collection("users")
            .document(userId)
            .snapshots().collectLatest { snapshot ->
                val cardInfo = snapshot.get("card") as Map<String, String>?
                send(
                    Card(
                        number = cardInfo?.get("number") ?: "",
                        cvv = cardInfo?.get("cvv") ?: "",
                    )
                )
            }
    }

    override suspend fun updateSavedCard(card: Card) {
        try {
            firestore
                .collection("users")
                .document(userId)
                .update("card", card.toMap())
            Log.e("UpdateSavedCardResult", true.toString())
        } catch (e: FirebaseFirestoreException) {
            Log.e("UpdateSavedCardResult", e.toString())
        }
    }

    override suspend fun createUserDocument(uid: String) {
        firestore
            .collection("users")
            .document(userId)
            .set(
                hashMapOf(
                    "balance" to 0,
                    "card" to hashMapOf(
                        "number" to "",
                        "cvv" to ""
                    )
                )
            )
    }

    override suspend fun addTrip(trip: Trip) {
        firestore
            .collection("users")
            .document(userId)
            .collection("trips")
            .add(
                hashMapOf(
                    "route_id" to trip.routeId,
                    "stops" to trip.stops,
                )
            )
    }

    override suspend fun disputeTransaction(transaction: Transaction) {
        firestore
            .collection("users")
            .document(userId)
            .collection("trips")
            .document(transaction.id)
            .update("disputed", true)
    }
}

class MockFirestoreService : FirestoreService {
    var didGetTransactionHistory = false
    var didGetBalanceCents = false
    var didUpdateBalance = false
    var didGetSavedCard = false
    var didUpdateSavedCard = false
    var didCreateUserDocument = false
    var didAddTrip = false
    var didDisputeTransaction = false

    override suspend fun getTransactionHistory(): Flow<List<Transaction>> = flow {
        didGetTransactionHistory = true
        emit(emptyList())
    }

    override suspend fun getUserBalanceCents(): Int {
        didGetBalanceCents = true
        return 0
    }

    override suspend fun updateUserBalance(valueCents: Int) {
        didUpdateBalance = true
    }

    override suspend fun getSavedCard(): Flow<Card> = flow {
        didGetSavedCard = true
        emit(
            Card(
                number = "",
                cvv = "",
            )
        )
    }

    override suspend fun updateSavedCard(card: Card) {
        didUpdateSavedCard = true
    }

    override suspend fun createUserDocument(uid: String) {
        didCreateUserDocument = true
    }

    override suspend fun addTrip(trip: Trip) {
        didAddTrip = true
    }

    override suspend fun disputeTransaction(transaction: Transaction) {
        didDisputeTransaction = true
    }
}