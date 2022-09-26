package kiwi.liam.paua.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.liam.paua.routers.TabScreen
import kiwi.liam.paua.ui.theme.icons

@Composable
fun PauaBottomNavigation(
    selected: TabScreen,
    onClick: (TabScreen) -> Unit,
) {
    CustomBottomNavigation {
        PauaBottomItem(
            screen = TabScreen.Wallet, selected = selected, icon = MaterialTheme.icons.Wallet, onClick = onClick,
        )
        PauaBottomItem(
            screen = TabScreen.Account, selected = selected, icon = MaterialTheme.icons.AccountCircle, onClick = onClick,
        )
    }
}

/**
 * Customized version of the provided [BottomNavigation].
 * This is so it extends into the system gesture/navigation area and looks more continuous.
 */
@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    bottom = WindowInsets.mandatorySystemGestures
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .height(56.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            content = content,
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