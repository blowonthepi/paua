package kiwi.liam.paua.dependencies.managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.models.TransitType

class TransitManagerState {
    var balanceCents by mutableStateOf(0)
    var transactions = mutableListOf<Transaction>()
}

interface TransitManager {
    fun fetchTransactions()
}

class AppTransitManager(val state: TransitManagerState) : TransitManager {
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
}

class MockTransitManager : TransitManager {
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
    }
}