package kiwi.liam.paua.screens.wallet

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.rounded.DirectionsBoat
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsRailway
import androidx.compose.material.icons.rounded.DirectionsSubway
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kiwi.liam.paua.dependencies.managers.TransitManager
import kiwi.liam.paua.dependencies.managers.TransitState
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.tools.BaseViewModel
import kiwi.liam.paua.ui.theme.icons
import org.koin.core.component.inject

class WalletViewModel : BaseViewModel() {
    private val transitManager: TransitManager by inject()
    private val transitManagerState: TransitState by inject()
    val transactions = transitManagerState.transactions

    init {
        transitManager.fetchTransactions()
    }

    fun getAccountBalance(): String {
        val dollars: Double = transitManagerState.balanceCents / 100.0

        return "\$${String.format("%.2f", dollars)}"
    }

    @Composable
    fun getTransitIcon(type: TransitType): ImageVector {
        return when (type) {
            TransitType.Bus -> MaterialTheme.icons.DirectionsBus
            TransitType.Train -> MaterialTheme.icons.DirectionsRailway
            TransitType.Ferry -> MaterialTheme.icons.DirectionsBoat
            TransitType.CableCar -> MaterialTheme.icons.DirectionsSubway
        }
    }
}