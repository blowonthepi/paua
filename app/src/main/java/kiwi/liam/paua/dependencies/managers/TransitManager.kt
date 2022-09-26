package kiwi.liam.paua.dependencies.managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.models.TransitType

class TransitState {
    var balanceCents by mutableStateOf(0)
    var transactions = mutableListOf<Transaction>()
}

interface TransitManager {
    fun fetchTransactions()

    fun topUp(valueCents: Int)
    fun chargeAccount(valueCents: Int)
}

class AppTransitManager(private val state: TransitState) : TransitManager {
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

    override fun topUp(valueCents: Int) {
        // TODO
    }

    override fun chargeAccount(valueCents: Int) {
        // TODO
    }
}