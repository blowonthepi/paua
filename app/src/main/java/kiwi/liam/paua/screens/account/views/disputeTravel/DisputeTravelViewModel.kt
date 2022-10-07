package kiwi.liam.paua.screens.account.views.disputeTravel

import androidx.lifecycle.viewModelScope
import kiwi.liam.paua.dependencies.managers.TransitState
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.dependencies.services.FirestoreService
import kiwi.liam.paua.tools.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class DisputeTravelViewModel : BaseViewModel() {
    private val transitManagerState: TransitState by inject()
    private val firestoreService: FirestoreService by inject()
    val transactions = transitManagerState.transactions

    fun dispute(transaction: Transaction) {
        viewModelScope.launch {
            firestoreService.disputeTransaction(transaction)
        }
    }
}