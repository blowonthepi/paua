package kiwi.liam.paua.routers

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kiwi.liam.paua.screens.wallet.WalletScreen
import kiwi.liam.paua.ui.components.PauaBottomNavigation
import kiwi.liam.paua.ui.components.TopBar
import org.koin.androidx.compose.get

enum class TabScreen {
    Wallet,
    History,
    Account
}

class TabRouter {
    var currentTab by mutableStateOf(TabScreen.Wallet)
}

@Composable
fun TabRouterView() {
    val router: TabRouter = get()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            PauaBottomNavigation(
                selected = router.currentTab,
                onClick = {
                    router.currentTab = it
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            Crossfade(targetState = router.currentTab) { tab ->
                when (tab) {
                    TabScreen.Wallet -> WalletScreen()
                    TabScreen.History -> Text(text = "History")
                    TabScreen.Account -> Text(text = "Account")
                }
            }
        }
    }
}

