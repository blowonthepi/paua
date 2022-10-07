package kiwi.liam.paua.screens.wallet

import kiwi.liam.paua.dependencies.managers.TransitManager
import kiwi.liam.paua.dependencies.managers.TransitState
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class WalletViewModel : BaseViewModel() {
    private val transitManager: TransitManager by inject()
    private val transitManagerState: TransitState by inject()
    val transactions = transitManagerState.transactions

    fun topUp() {
        // Fake a $1 top-up for this Proof of concept
        transitManager.topUp(100)
    }

    fun getAccountBalance(): String {
        val dollars: Double = transitManagerState.balanceCents / 100.0

        return "\$${String.format("%.2f", dollars)}"
    }
}