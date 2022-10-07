package kiwi.liam.paua.dependencies.managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.services.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransitState {
    var balanceCents by mutableStateOf(0)
    var transactions = mutableStateListOf<Transaction>()
}

interface TransitManager {
    fun fetchBalance()
    fun fetchTransactions()

    fun topUp(valueCents: Int)
    fun chargeAccount(valueCents: Int)
}

class AppTransitManager(
    private val state: TransitState,
    private val firestoreService: FirestoreService,
) : TransitManager {
    override fun fetchBalance() {
        CoroutineScope(Dispatchers.IO).launch {
            state.balanceCents = firestoreService.getUserBalanceCents()
        }
    }

    override fun fetchTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            state.transactions.addAll(firestoreService.getTransactionHistory())
        }
    }

    override fun topUp(valueCents: Int) {
        state.balanceCents += valueCents
        updateBalance(state.balanceCents)
    }

    override fun chargeAccount(valueCents: Int) {
        state.balanceCents -= valueCents
        updateBalance(state.balanceCents)
    }

    private fun updateBalance(valueCents: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            firestoreService.updateUserBalance(valueCents)
        }
    }
}

class MockTransitManager(private val state: TransitState) : TransitManager {

    override fun fetchBalance() {
        state.balanceCents = 0
    }

    override fun fetchTransactions() {
        val transactionList = listOf(
            Transaction(
                type = TransitType.Train,
                routeId = "HVL",
                routeName = "Hutt Valley Line",
                stops = listOf(
                    "Upper Hutt",
                    "Wellington Station",
                ),
            ),
            Transaction(
                type = TransitType.CableCar,
                routeId = "CCL",
                routeName = "Cable Car",
                stops = listOf(
                    "Lambton Quay Terminal",
                    "Salamanca",
                ),
            ),
            Transaction(
                type = TransitType.Bus,
                routeId = "22",
                routeName = "Wellington Station",
                stops = listOf(
                    "Wgtn Uni - Stop A",
                    "Wellington Station - Stop D",
                    "Lambton Quay - Willis Street",
                ),
            ),
            Transaction(
                type = TransitType.Ferry,
                routeId = "WHF",
                routeName = "Days Bay Ferry",
                stops = listOf(
                    "Queens Wharf",
                    "Days Bay Wharf",
                ),
            ),
        )
        state.transactions.addAll(transactionList)
    }

    override fun topUp(valueCents: Int) {
        state.balanceCents += valueCents
    }

    override fun chargeAccount(valueCents: Int) {
        state.balanceCents -= valueCents
    }
}