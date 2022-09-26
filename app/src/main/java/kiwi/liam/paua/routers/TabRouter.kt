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
import kiwi.liam.paua.ui.components.TopBarRouterDelegate

enum class TabScreen {
    Wallet,
    Account
}

class TabRouter : TopBarRouterDelegate {
    var currentTab by mutableStateOf(TabScreen.Wallet)

    override fun showWalletScreen() {
        currentTab = TabScreen.Wallet
    }

    override fun showAccountScreen() {
        currentTab = TabScreen.Account
    }

    @Composable
    fun bottomBar() {
        PauaBottomNavigation(
            selected = currentTab,
            onClick = {
                currentTab = it
            },
        )
    }
}


@Composable
fun TabRouterView(router: TabRouter) {

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { router.bottomBar() },
    ) { padding ->
        Box(modifier = Modifier.padding(bottom = padding.calculateBottomPadding())) {

            Crossfade(targetState = router.currentTab) {
                when (it) {
                    TabScreen.Wallet -> WalletScreen()
                    TabScreen.Account -> Text("Account")
                }
            }
        }
    }
}

