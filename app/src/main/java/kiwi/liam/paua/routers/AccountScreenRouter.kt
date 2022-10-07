package kiwi.liam.paua.routers

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kiwi.liam.paua.dependencies.models.Card
import kiwi.liam.paua.dependencies.services.FirestoreService
import kiwi.liam.paua.screens.account.AccountNavigationDelegate
import kiwi.liam.paua.screens.account.AccountScreen
import kiwi.liam.paua.screens.account.views.DisputeTravel
import kiwi.liam.paua.screens.account.views.ManageCard
import kiwi.liam.paua.screens.account.views.findBusStops.FindBusStops
import kiwi.liam.paua.screens.account.views.findBusStops.FindBusStopsViewModel
import kiwi.liam.paua.ui.components.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class Destination(val destination: String) {
    companion object {
        val Account = Destination("account")
        val ManageSavedCards = Destination("manageSavedCards")
        val FindBusStops = Destination("findBusStops")
        val DisputeTravel = Destination("disputeTravel")
    }
}

class AccountScreenRouter : KoinComponent {
    private val firestoreService: FirestoreService by inject()
    private var card by mutableStateOf<Card?>(null)

    init {
        CoroutineScope(Dispatchers.Main).launch {
            firestoreService.getSavedCard().collectLatest {
                card = it
            }
        }
    }

    private fun updateCard(card: Card) {
        CoroutineScope(Dispatchers.IO).launch {
            firestoreService.updateSavedCard(card)
        }
    }

    @Composable
    fun manageCardView() = ManageCard(
        card = card,
        updateCard = { updateCard(it) },
    )
}

@Composable
fun AccountScreenRouterView(router: AccountScreenRouter) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val backAction: (() -> Unit)? = if (navBackStackEntry?.destination?.route != "account") {
        { navController.popBackStack() }
    } else {
        null
    }

    val navigateTo: (String) -> Unit = {
        navController.navigate(it) {
            popUpTo(Destination.Account.destination)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                backAction = backAction
            )
        }
    ) {
        NavHost(navController = navController, startDestination = "account") {
            composable(Destination.Account.destination) {
                AccountScreen(
                    navigation = object : AccountNavigationDelegate {
                        override fun toManagedSavedCards() {
                            navigateTo(Destination.ManageSavedCards.destination)
                        }

                        override fun toFindBusStops() {
                            navigateTo(Destination.FindBusStops.destination)
                        }

                        override fun toDisputeTravel() {
                            navigateTo(Destination.DisputeTravel.destination)
                        }
                    }
                )
            }
            composable(Destination.ManageSavedCards.destination) { router.manageCardView() }
            composable(Destination.FindBusStops.destination) { FindBusStops(FindBusStopsViewModel()) }
            composable(Destination.DisputeTravel.destination) { DisputeTravel() }
        }
    }
}