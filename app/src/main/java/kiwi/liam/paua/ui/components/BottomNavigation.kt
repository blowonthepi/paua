package kiwi.liam.paua.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kiwi.liam.paua.routers.TabScreen
import kiwi.liam.paua.ui.theme.icons

@Composable
fun PauaBottomNavigation(
    selected: TabScreen,
    onClick: (TabScreen) -> Unit,
) {
    BottomNavigation {
        PauaBottomItem(
            screen = TabScreen.Wallet,
            selected = selected,
            icon = MaterialTheme.icons.Wallet,
            onClick = onClick
        )
        PauaBottomItem(
            screen = TabScreen.History,
            selected = selected,
            icon = MaterialTheme.icons.History,
            onClick = onClick
        )
        PauaBottomItem(
            screen = TabScreen.Account,
            selected = selected,
            icon = MaterialTheme.icons.AccountCircle,
            onClick = onClick
        )
    }
}

@Composable
private fun RowScope.PauaBottomItem(
    screen: TabScreen,
    selected: TabScreen,
    icon: ImageVector,
    onClick: (TabScreen) -> Unit,
) {
    BottomNavigationItem(
        selected = screen == selected,
        onClick = { onClick(screen) },
        icon = {
            Icon(
                icon,
                contentDescription = screen.name,
            )
        },
        alwaysShowLabel = true,
        label = { Text(screen.name) },
    )
}